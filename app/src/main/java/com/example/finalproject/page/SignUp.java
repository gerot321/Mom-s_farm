package com.example.finalproject.page;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.finalproject.Model.Product;
import com.example.finalproject.Model.User;
import com.momsfarm.finalproject.R;
import com.example.finalproject.base.BaseActivity;
import com.example.finalproject.util.PreferenceUtil;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUp extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_card)
    CardView imageHolder;
    @BindView(R.id.button_choose_image)
    RelativeLayout mButtonChooseImage;
    @BindView(R.id.image_view)
    ImageView mImageView;
    @BindView(R.id.etPhone)
    MaterialEditText etPhone;
    @BindView(R.id.etName)
    MaterialEditText etName;
    @BindView(R.id.etPassword)
    MaterialEditText etPassword;
    @BindView(R.id.etTanngal)
    MaterialEditText etDate;
    @BindView(R.id.etAddress)
    MaterialEditText etAddress;
    @BindView(R.id.radioButton)
    RadioButton radio;
    @BindView(R.id.radioGroup)
    RadioGroup groups;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    private Uri mImageUri;

    private static final int PICK_IMAGE_REQUEST = 1;

    private StorageTask mUploadTask;
    private StorageReference mStorageRef;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    DatabaseReference table_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        initEnv();
        initView();
    }

    private void initEnv(){
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
    }

    private void initView(){
        setTitle(toolbar, "Buat Akun");
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        etDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                showDateDialog();
            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                showProgress();


                if (mImageUri != null) {
                    StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(mImageUri));

                    mUploadTask = fileReference.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
//
                                    table_user.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.child(etPhone.getText().toString()).exists()){
                                                disProgress();

                                                Toast.makeText(SignUp.this, "Account already exist!", Toast.LENGTH_SHORT).show();
                                            }else {
                                                disProgress();
                                                createUser(taskSnapshot.getDownloadUrl().toString());
                                            }
                                            table_user.removeEventListener(this);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    createUser(" ");
                }


            }
        });
    }

    private void createUser (String image){
        int select = groups.getCheckedRadioButtonId();
        radio= (RadioButton)findViewById(select);
        User user = new User(etName.getText().toString(), etPassword.getText().toString(), "Costumer",etPhone.getText().toString(),etAddress.getText().toString(),radio.getText().toString(),
                etDate.getText().toString()," "," ", image," ","unVerified");
        table_user.child(user.getPhone()).setValue(user);
        PreferenceUtil.setUser(user);
        Toast.makeText(SignUp.this, "Berhasil Membuat Akun", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                etDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
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
