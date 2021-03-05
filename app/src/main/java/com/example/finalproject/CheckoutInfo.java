package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CheckoutInfo extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference Users;

    Button but;
    ImageView profileImage;
    String ID;
    CardView privateprofile,detailprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_info);

        but = (Button)findViewById(R.id.buttonOK);
        ID = getIntent().getStringExtra("userID");
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CheckoutInfo.this, Home.class);
                i.putExtra("phoneId",ID);
                startActivity(i);
            }
        });



    }


}
