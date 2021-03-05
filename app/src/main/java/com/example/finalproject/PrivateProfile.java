package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by gerrys on 4/2/2018.
 */

public class PrivateProfile extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference Users;

    EditText phone,address,email;
    Button privates;
    String ID;
    TextView et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privatepro);

        ID = getIntent().getStringExtra("phoneId");
        database = FirebaseDatabase.getInstance();
        Users = database.getReference("User");

        address = (MaterialEditText)findViewById(R.id.editaddress);
        email = (MaterialEditText)findViewById(R.id.editemail);
        et = (TextView)findViewById(R.id.textView4);
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users.child(ID).child("address").setValue(address.getText().toString());
                Users.child(ID).child("email").setValue(email.getText().toString());
                Intent intent = new Intent(PrivateProfile.this, UserProfile.class);
                intent.putExtra("phoneId",ID);
                startActivity(intent);
            }
        });


    }

}
