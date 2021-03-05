package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class ScanActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
    FirebaseDatabase database;
    DatabaseReference prodReq;
    BarcodeReader barcodeReader;
    String userID;
    String activitys;
    String option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        userID = getIntent().getStringExtra("phoneId");
        activitys = getIntent().getStringExtra("activity");
        option  = getIntent().getStringExtra("option");
        database = FirebaseDatabase.getInstance();
        prodReq = database.getReference("productReq");
        // get the barcode reader instance
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
    }

    @Override
    public void onScanned(Barcode barcode) {

        // playing barcode reader beep sound
        barcodeReader.playBeep();
        if(activitys.equals("home")){
            if(option.equals("topup")){
                Intent intent = new Intent(ScanActivity.this, Home.class);
                intent.putExtra("code", barcode.displayValue);
                intent.putExtra("option",option);
                intent.putExtra("phoneId",userID);
                startActivity(intent);
            }else if(option.equals("confirm")){

                prodReq.child(barcode.displayValue.toString()).child("status").setValue("Order telah sampai pada tujuan");
                Intent intent = new Intent(ScanActivity.this, Home.class);
                intent.putExtra("code", barcode.displayValue);
                intent.putExtra("option",option);
                intent.putExtra("phoneId",userID);
                startActivity(intent);
            }else if(option.equals("addProd")){
                Intent intent = new Intent(ScanActivity.this, Home.class);
                intent.putExtra("code", barcode.displayValue);
                intent.putExtra("option",option);
                intent.putExtra("phoneId",userID);
                startActivity(intent);
            }

        }
        else if (activitys.equals("topup")){
            Intent intent = new Intent(ScanActivity.this, TopUpActivity.class);
            intent.putExtra("code", barcode.displayValue);
            intent.putExtra("userID",userID);
            startActivity(intent);
        }

        // ticket details activity by passing barcode

    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String s) {
        Toast.makeText(getApplicationContext(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraPermissionDenied() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
