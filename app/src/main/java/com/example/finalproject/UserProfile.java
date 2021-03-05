package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserProfile extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference Users;

    TextView name,addres, phone, email,gender,profesi,hobi,tanggallahir,saldo;
    ImageView profileImage;
    String ID;
    CardView privateprofile,detailprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        ID = getIntent().getStringExtra("phoneId");
        database = FirebaseDatabase.getInstance();
        Users = database.getReference("User");

        profileImage = (ImageView)findViewById(R.id.imageProfile);

        name=(TextView)findViewById(R.id.UserName);
        addres=(TextView)findViewById(R.id.UserAddress);
        phone=(TextView)findViewById(R.id.UserPhonenumber);
        email=(TextView)findViewById(R.id.UserEmail);

        gender=(TextView)findViewById(R.id.UserGender);
        profesi=(TextView)findViewById(R.id.UserProfesi);
        hobi = (TextView)findViewById(R.id.UserHobi);
        tanggallahir=(TextView)findViewById(R.id.UserTanggal);
        detailprofile=(CardView)findViewById(R.id.detailprofile);
        privateprofile=(CardView)findViewById(R.id.privateprofile);

        Users.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user =dataSnapshot.getValue(User.class);

                name.setText(user.getName());
                addres.setText("Alamat : "+ user.getAddress());
                phone.setText("Nomor Handphone : " +ID);
                email.setText("Email : "+user.getEmail());

                gender.setText("Jenis Kelamin : "+user.getGender());
                tanggallahir.setText("Tanggal  Lahir : "+user.getTanggalLahir());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        detailprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(UserProfile.this, detailProfile.class);
                a.putExtra("phoneId",ID);
                startActivity(a);
            }
        });
        privateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent as = new Intent(UserProfile.this, PrivateProfile.class);
                as.putExtra("phoneId",ID);
                startActivity(as);
            }
        });




    }


}
