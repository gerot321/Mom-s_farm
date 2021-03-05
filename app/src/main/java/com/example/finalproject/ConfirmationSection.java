package com.example.finalproject;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalproject.Model.Confirmation;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ConfirmationSection extends AppCompatActivity  {
    private static final int PICK_IMAGE_REQUEST = 1;
    FirebaseDatabase database;
    DatabaseReference orderList;
    DatabaseReference statuss;
    DatabaseReference confirmations;
    private StorageTask mUploadTask;
    private StorageReference mStorageRef;
    private ImageView mImageView;
    private Uri mImageUri;
    private Button mButtonChooseImage, submit;
    private ProgressBar mProgressBar;
    private EditText name,no;
    Confirmation confirmss;


    DatabaseReference prod;
    DatabaseReference prodReq;
    DatabaseReference ad;

    String ID,keys;
    Spinner oID;
    String prodId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        mImageView = (ImageView) findViewById(R.id.image_view);
        name = (EditText) findViewById(R.id.editName);

        no = (EditText) findViewById(R.id.editNo);
        mButtonChooseImage = (Button) findViewById(R.id.button_choose_image);
        submit = (Button) findViewById(R.id.button_upload);
        database = FirebaseDatabase.getInstance();
        orderList = database.getReference("Requests");
        prodReq = database.getReference("productReq");
        ad = database.getReference("od");
        confirmations = database.getReference("Confirmation");
        prod = database.getReference("Product");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        final ArrayList<String> list = new ArrayList<String>();


        ID= getIntent().getStringExtra("userID");

        orderList.orderByChild("phone").equalTo(ID).addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    confirmss = child.getValue(Confirmation.class);
                    if(confirmss.getStatus().toString().equals("Waiting Payment")){
                        list.add(child.getKey());
                        oID = (Spinner)findViewById(R.id.orderList);

                        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ConfirmationSection.this,android.R.layout.simple_spinner_item, list);
                        oID.setAdapter(dataAdapter);
                        oID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(ConfirmationSection.this, "Selected "+ dataAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //set the view for the Drop down list

        //set the ArrayAdapter to the spinner

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (mImageUri != null) {
                    StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(mImageUri));

                    mUploadTask = fileReference.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressBar.setProgress(0);
                                        }
                                    }, 0);
                                    Toast.makeText(ConfirmationSection.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                    final Intent intent = new Intent(ConfirmationSection.this, Home.class);
                                    intent.putExtra("phoneId",ID);
                                    startActivity(intent);
                                    confirmations.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            // Check if already user phone
                                            if (dataSnapshot.child(oID.getSelectedItem().toString()).exists()) {
                                                // mDialog.dismiss();

                                                //Toast.makeText(addProduct.this, "Account already exist!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                //mDialog.dismiss();

                                                Confirmation products = new Confirmation(oID.getSelectedItem().toString(),no.getText().toString(), name.getText().toString(),"Waiting Admin Confirmation", taskSnapshot.getDownloadUrl().toString());
                                                confirmations.child(oID.getSelectedItem().toString()).setValue(products);


                                               /* for(int a=0;a<=cart.size();a++){
                                                    final int index = a;
                                                    prod.child(cart.get(a).getProductId().toString()).child("MerchantId").addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            productRequest req= new productRequest(dataSnapshot.getValue().toString(),cart.get(index).getProductId(),cart.get(index).getProductName().toString(),cart.get(index).getQuantity().toString()
                                                                    ,cart.get(index).getPrice().toString(),cart.get(index).getAddress()) ;
                                                            prodReq.child(oID.getSelectedItem().toString()).setValue(req);
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });

                                                }*/


                                          //      LocalBroadcastManager.getInstance(ConfirmationSection.this).registerReceiver(mReceiver,new IntentFilter(oID.getSelectedItem().toString()));


                                                /*for(int a=0;a<=cart.size();a++){
                                                    final int index = a;
                                                    prod.child(cart.get(a).getProductId().toString()).child("MerchantId").addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });

                                                }*/

                                                // Toast.makeText(addProduct.this, "Account successfully created!", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });




                                    statuss = database.getReference("Requests").child(oID.getSelectedItem().toString());
                                    statuss.child("status").setValue("Waiting Admin Confirmation");

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ConfirmationSection.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    mProgressBar.setProgress((int) progress);
                                }
                            });
                } else {

                    Toast.makeText(ConfirmationSection.this, "No file selected", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    /*private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
             cart = (List<Order>) intent.getSerializableExtra("cart");
        }
    };*/

}
