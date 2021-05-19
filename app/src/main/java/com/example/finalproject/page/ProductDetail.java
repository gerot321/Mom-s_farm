package com.example.finalproject.page;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.finalproject.Model.Product;
import com.momsfarm.finalproject.R;
import com.example.finalproject.base.BaseActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetail extends BaseActivity {
    @BindView(R.id.image_card)
    CardView imageCard;
    @BindView(R.id.image_product)
    ImageView imageProduct;
    @BindView(R.id.productPrice)
    MaterialEditText productPrice;
    @BindView(R.id.productName)
    MaterialEditText productName;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    @BindView(R.id.delete_btn)
    Button deleteBtn;
    @BindView(R.id.button_choose_image)
    RelativeLayout mButtonChooseImage;
    @BindView(R.id.minus_stock)
    RelativeLayout minusStock;
    @BindView(R.id.plus_stock)
    RelativeLayout plusStock;
    @BindView(R.id.product_stock_edt)
    EditText stockEdt;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    FirebaseDatabase database;
    DatabaseReference productTable;
    Product product;
    String productId;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;

    private Uri mImageUri;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        ButterKnife.bind(this);


        initData();
        initEnv();
        initView();

    }

    public void initData(){
        product =(Product) getIntent().getParcelableExtra("product");
        productId = getIntent().getStringExtra("productId");
    }

    public void initEnv(){
        database = FirebaseDatabase.getInstance();
        productTable = database.getReference("Product");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
    }

    public void initView(){
        setTitle(toolbar, "Ubah Produk");
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productTable.child(productId).child("isActive").setValue("NONACTIVE");
                Toast.makeText(ProductDetail.this, "Berhasil menghapus produk", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
        plusStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int stockValue = Integer.parseInt(stockEdt.getText().toString())+1;
                stockEdt.setText(String.valueOf(stockValue));
            }
        });

        minusStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(stockEdt.getText().toString())>0){
                    int stockValue = Integer.parseInt(stockEdt.getText().toString())-1;
                    stockEdt.setText(String.valueOf(stockValue));
                }
            }
        });
        if(product.getImage()!=null&&!product.getImage().equals(" ")&&!product.getImage().isEmpty()){
            imageCard.setVisibility(View.VISIBLE);
            Picasso.with(getBaseContext()).load(product.getImage()).into(imageProduct);
        }
        stockEdt.setText(product.getStock());
        productName.setText(product.getName());
        productPrice.setText(product.getPrice());

        submitBtn.setText("UBAH PRODUK");
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                productTable.child(productId).child("stock").setValue(stockEdt.getText().toString());
                productTable.child(productId).child("name").setValue(productName.getText().toString());
                productTable.child(productId).child("price").setValue(productPrice.getText().toString());

                if (mImageUri != null) {
                    StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(mImageUri));

                    mUploadTask = fileReference.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
//
                                    productTable.child(productId).child("image").setValue(taskSnapshot.getDownloadUrl().toString());
                                    disProgress();
                                    Toast.makeText(ProductDetail.this, "Berhasil Merubah Produk", Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ProductDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                                    mProgressBar.setProgress((int) progress);

                                }
                            });
                }else{
                    disProgress();
                    Toast.makeText(ProductDetail.this, "Berhasil Merubah Produk", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }

            }
        });

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            imageCard.setVisibility(View.VISIBLE);
            Picasso.with(this).load(mImageUri).into(imageProduct);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
