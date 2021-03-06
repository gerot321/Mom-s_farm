package com.example.finalproject.page;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.finalproject.Model.User;
import com.example.finalproject.R;
import com.example.finalproject.base.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUp extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private static final String TAG = "PhoneAuth";
    MaterialEditText etPhone, etName, etPassword,etDate,etAddress,etEmail;
    RadioButton radio;
    RadioGroup groups;
    Button btnSignUp;
//    private String phoneVerificationId;
//    private PhoneAuthProvider.ForceResendingToken resendToken;
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
//            verificationCallbacks;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        setTitle(toolbar, "Buat Akun");
        etPhone = (MaterialEditText)findViewById(R.id.etPhone);
        etName = (MaterialEditText)findViewById(R.id.etName);
        etPassword = (MaterialEditText)findViewById(R.id.etPassword);
        etDate= (MaterialEditText)findViewById(R.id.etTanngal);
        etAddress =(MaterialEditText)findViewById(R.id.etAddress);
        radio= (RadioButton)findViewById(R.id.radioButton);
        groups =(RadioGroup)findViewById(R.id.radioGroup);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        etEmail=(MaterialEditText)findViewById(R.id.etEmail);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
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

                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("loading...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(etPhone.getText().toString()).exists()){
                            mDialog.dismiss();

                            Toast.makeText(SignUp.this, "Account already exist!", Toast.LENGTH_SHORT).show();
                        }else {
                            mDialog.dismiss();
                            int select = groups.getCheckedRadioButtonId();
                            radio= (RadioButton)findViewById(select);
//                            sendCode(v);
                            User user = new User(etName.getText().toString(), etPassword.getText().toString(), "Costumer","0",etAddress.getText().toString(),radio.getText().toString(),
                                    etDate.getText().toString()," "," "," ",etEmail.getText().toString(),"unVerified");
                            table_user.child(etPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Account successfully created!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
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
//    public void sendCode(View view) {
//
//        String phoneNumber = etPhone.getText().toString();
//
//        setUpVerificatonCallbacks();
//
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//                verificationCallbacks);
//    }
//    private void setUpVerificatonCallbacks() {
//
//        verificationCallbacks =
//                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//                    @Override
//                    public void onVerificationCompleted(
//                            PhoneAuthCredential credential) {
//
//
//                    }
//
//                    @Override
//                    public void onVerificationFailed(FirebaseException e) {
//
//                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                            // Invalid request
//
//                            Toast.makeText(SignUp.this, "Invalid credential: "
//                                    + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//
//                        } else if (e instanceof FirebaseTooManyRequestsException) {
//                            // SMS quota exceeded
//                            Toast.makeText(SignUp.this, "SMS Quota exceeded.", Toast.LENGTH_SHORT).show();
//                            Log.d(TAG, "SMS Quota exceeded.");
//                        }
//                    }
//
//                    @Override
//                    public void onCodeSent(String verificationId,
//                                           PhoneAuthProvider.ForceResendingToken token) {
//
//                        phoneVerificationId = verificationId;
//                        resendToken = token;
//                        Intent I = new Intent(SignUp.this, SmsVerify.class);
//                        I.putExtra("Phone",etPhone.getText().toString());
//                        I.putExtra("creden",verificationId);
//                        I.putExtra("token",token.toString());
//                        startActivity(I);
//
//                    }
//                };
//    }
}
