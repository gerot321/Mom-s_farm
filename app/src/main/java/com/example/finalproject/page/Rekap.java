package com.example.finalproject.page;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.finalproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.ButterKnife;

public class Rekap extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference requests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qr);
        ButterKnife.bind(this);


    }

    private void initEnv(){
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Order");
    }
}
