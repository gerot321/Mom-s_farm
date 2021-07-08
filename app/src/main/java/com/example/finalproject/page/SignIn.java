package com.example.finalproject.page;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.finalproject.Common.Common;
import com.example.finalproject.Model.User;
import com.momsfarm.finalproject.R;
import com.example.finalproject.base.BaseActivity;
import com.example.finalproject.util.PreferenceUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignIn extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    @BindView(R.id.remember_me)
    CheckBox checkBox;
    @BindView(R.id.forgot_password)
    TextView forgotPassword;
    DatabaseReference table_user;
    User persistUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        PreferenceUtil.setContext(this);
        initData();
        initEnv();
        initView();


    }

    private void initData(){
        persistUser = PreferenceUtil.getPersistUser();
        if(persistUser!=null){
            etPhone.setText(persistUser.getPhone());
            etPassword.setText(persistUser.getPassword());
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }
    }

    private void initView(){
        setTitle(toolbar, "Masuk");
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("loading...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Check if user exist in the database
                        if(dataSnapshot.child(etPhone.getText().toString()).exists()) {

                            mDialog.dismiss();

                            // Get user information
                            User user = dataSnapshot.child(etPhone.getText().toString()).getValue(User.class);
                            user.setPhone(etPhone.getText().toString()); //set phone

                            if (user.getPassword().equals(etPassword.getText().toString())) {
                                if(checkBox.isChecked()){
                                    PreferenceUtil.setPersistUser(user);
                                }else{
                                    PreferenceUtil.clearPersist();
                                }
                                Intent intent = new Intent(SignIn.this, MainMenu.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("phoneId",etPhone.getText().toString() );
                                Common.currentUser = user;
                                PreferenceUtil.setUser(user);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(SignIn.this, "Password yang dimasukan tidak sesuai", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "Pengguna tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, ChangePassword.class);
                startActivity(intent);
            }
        });

    }

    private void initEnv(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");
    }

}
