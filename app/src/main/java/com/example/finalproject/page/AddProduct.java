package com.example.finalproject.page;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.finalproject.Model.Product;
import com.momsfarm.finalproject.R;
import com.example.finalproject.base.BaseActivity;
import com.example.finalproject.util.PreferenceUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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

public class AddProduct extends BaseActivity {
    @BindView(R.id.image_card)
    CardView imageHolder;
    @BindView(R.id.minus_stock)
    RelativeLayout minusStock;
    @BindView(R.id.plus_stock)
    RelativeLayout plusStock;
    @BindView(R.id.product_stock_edt)
    EditText stockEdt;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.button_choose_image)
    RelativeLayout mButtonChooseImage;
    @BindView(R.id.image_view)
    ImageView mImageView;
    @BindView(R.id.nameProduct)
    MaterialEditText  Name;
    @BindView(R.id.Price)
    MaterialEditText Price;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri mImageUri;

    private StorageTask mUploadTask;

    DatabaseReference table_product;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);

        initEnv();
        initView();

    }

    private void initView(){
        setTitle(toolbar, "Tambah Produk");

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

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
                if(Name.getText().toString().isEmpty()||Price.getText().toString().isEmpty()||stockEdt.getText().toString().isEmpty()){
                    Toast.makeText(AddProduct.this, "Mohon isi semua data untuk menambahkan produk", Toast.LENGTH_SHORT).show();

                    return;
                }
                table_product.orderByChild("name").equalTo(Name.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            Toast.makeText(AddProduct.this, "Produk sudah terdaftar", Toast.LENGTH_SHORT).show();
                            disProgress();
                        }else{
                            addProduct();
                        }
                        table_product.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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


    private void initEnv(){
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        table_product = database.getReference("Product");

    }

    private void addProduct(){
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                            Product products = new Product( "PROD-"+String.valueOf(System.currentTimeMillis()), Name.getText().toString(), taskSnapshot.getDownloadUrl().toString(), Price.getText().toString(), stockEdt.getText().toString(), "ACTIVE", PreferenceUtil.getUser().getPhone());
                            table_product.child(products.getProductId()).setValue(products);
                            disProgress();
                            Toast.makeText(AddProduct.this, "Berhasil Menambahkan Produk", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            AddProduct.this.finish();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(AddProduct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                                    mProgressBar.setProgress((int) progress);

                        }
                    });
        } else {
            Product products = new Product( "PROD-"+String.valueOf(System.currentTimeMillis()), Name.getText().toString(), " ", Price.getText().toString(), stockEdt.getText().toString(), "ACTIVE", PreferenceUtil.getUser().getPhone());
            table_product.child(products.getProductId()).setValue(products);
            disProgress();
            Toast.makeText(AddProduct.this, "Berhasil Menambahkan Produk", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            AddProduct.this.finish();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            imageHolder.setVisibility(View.VISIBLE);
            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
