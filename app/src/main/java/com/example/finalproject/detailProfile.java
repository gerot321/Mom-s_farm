package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by gerrys on 4/2/2018.
 */

public class detailProfile extends AppCompatActivity {
    FirebaseDatabase databasea;
    DatabaseReference UsersSS;

    EditText hobi, tanggallahir, profesi;
    RadioGroup gp;
    RadioButton rb;
    String IDs;
    TextView et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailpro);

        IDs = getIntent().getStringExtra("phoneId");
        databasea = FirebaseDatabase.getInstance();
        UsersSS = databasea.getReference("User");

        hobi = (MaterialEditText) findViewById(R.id.editHobi);
        tanggallahir = (MaterialEditText) findViewById(R.id.editBD);
        profesi = (MaterialEditText) findViewById(R.id.editProfesi);
        gp = (RadioGroup) findViewById(R.id.radioGroups);
        et = (TextView)findViewById(R.id.textView3);

        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int select = gp.getCheckedRadioButtonId();
                rb = (RadioButton)findViewById(select);
                UsersSS.child(IDs).child("hobi").setValue(hobi.getText().toString());
                UsersSS.child(IDs).child("tanggalLahir").setValue(tanggallahir.getText().toString());
                UsersSS.child(IDs).child("profesi").setValue(profesi.getText().toString());
                UsersSS.child(IDs).child("gender").setValue(rb.getText().toString());
                Intent intent = new Intent(detailProfile.this, UserProfile.class);
                intent.putExtra("phoneId",IDs);
                startActivity(intent);
            }
        });
        /*btnDetail = (Button)findViewById(R.id.okess);
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int select = gp.getCheckedRadioButtonId();
                rb = (RadioButton)findViewById(select);
                Users.child(ID).child("hobi").setValue(hobi.getText().toString());
                Users.child(ID).child("tanggalLahir").setValue(tanggallahir.getText().toString());
                Users.child(ID).child("profesi").setValue(profesi.getText().toString());
                Users.child(ID).child("gender").setValue(rb.getText().toString());
            }
        });*/

    }
}