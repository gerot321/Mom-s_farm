package com.example.finalproject.page;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.finalproject.Common.Common;
import com.example.finalproject.Model.User;
import com.example.finalproject.R;
import com.example.finalproject.base.BaseActivity;
import com.example.finalproject.util.PreferenceUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ChangePassword extends BaseActivity {
    Toolbar toolbar;
    EditText etPhone;
    EditText etPassword;
    Button btnSignIn;

    DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        PreferenceUtil.setContext(this);

        initEnv();
        initView();


    }

    private void initView(){
        toolbar =  findViewById(R.id.toolbar);
        etPhone =  findViewById(R.id.etPhone);
        etPassword =  findViewById(R.id.etPassword);
        btnSignIn =  findViewById(R.id.btnSignIn);

        setTitle(toolbar, "Masuk");
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(ChangePassword.this);
                mDialog.setMessage("loading...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Check if user exist in the database
                        if(dataSnapshot.child(etPhone.getText().toString()).exists()) {

                            mDialog.dismiss();

                            table_user.child(etPhone.getText().toString()).child("password").setValue(etPassword.getText().toString());
                            Toast.makeText(ChangePassword.this, "Berhasil merubah password", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            mDialog.dismiss();
                            Toast.makeText(ChangePassword.this, "User tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void initEnv(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");
    }

}
