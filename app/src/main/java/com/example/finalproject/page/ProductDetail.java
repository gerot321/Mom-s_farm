package com.example.finalproject.page;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.finalproject.Model.Product;
import com.example.finalproject.R;
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
    @BindView(R.id.product_stock)
    EditText productStock;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    @BindView(R.id.button_choose_image)
    RelativeLayout mButtonChooseImage;

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
        if(product.getImage()!=null){
            imageCard.setVisibility(View.VISIBLE);
            Picasso.with(getBaseContext()).load(product.getImage()).into(imageProduct);
        }
        productStock.setText(product.getStock());
        productName.setText(product.getName());
        productPrice.setText(product.getPrice());


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productTable.child(productId).child("Stock").setValue(productStock.getText().toString());
                productTable.child(productId).child("Name").setValue(productName.getText().toString());
                productTable.child(productId).child("Price").setValue(productPrice.getText().toString());

                if (mImageUri != null) {
                    StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(mImageUri));

                    mUploadTask = fileReference.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
//
                                    productTable.child(productId).child("Image").setValue(taskSnapshot.getDownloadUrl().toString());

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
                }
                disProgress();

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
            Picasso.with(this).load(mImageUri).into(imageProduct);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
