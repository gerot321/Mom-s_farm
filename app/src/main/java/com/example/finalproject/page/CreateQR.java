package com.example.finalproject.page;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;

import com.example.finalproject.R;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class CreateQR extends AppCompatActivity {

    ImageView qrCodeIV;
    Button save_qr_code;

    final private int PERMISSION = 1;


    private static final int PERMISSION_REQUEST_CODE = 1001;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String productId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qr);
        initData();
        initView();

    }

    private void initData(){
        productId = getIntent().getStringExtra("productId");
    }

    private void initView(){
        qrCodeIV =  findViewById(R.id.idIVQrcode);
        save_qr_code =  findViewById(R.id.save_qr);

        checkPermission();
        if(productId!=null){
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int dimen = width < height ? width : height;
            dimen = dimen * 3 / 4;
            qrgEncoder = new QRGEncoder(productId, null, QRGContents.Type.TEXT, dimen);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                qrCodeIV.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Log.e("Tag", e.toString());
            }
        }
        save_qr_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageToGallery(qrCodeIV);
            }
        });
    }


    private void saveImageToGallery(ImageView iv){
        BitmapDrawable draw = (BitmapDrawable) iv.getDrawable();
        Bitmap bitmap = draw.getBitmap();

        FileOutputStream outStream = null;
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/save_image");
        dir.mkdirs();
        String fileName = String.format("%d.jpg", System.currentTimeMillis());
        File outFile = new File(dir, fileName);
        try {
            outStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
            Toast.makeText(this,"Berhasil menyimpak QR Code ke gallery",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkPermission() {

        int permissionReadStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWriteStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> deniedPermissions = new ArrayList<>();


        if(permissionReadStorage!=PackageManager.PERMISSION_GRANTED){
            deniedPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }else if(permissionWriteStorage!=PackageManager.PERMISSION_GRANTED){
            deniedPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!deniedPermissions.isEmpty()) {
            String[] array = new String[deniedPermissions.size()];
            ActivityCompat.requestPermissions(this, deniedPermissions.toArray(array), PERMISSION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermission();
                }else{
                    Toast.makeText(this,"Mohon untuk mengijinkan untuk mengakses penyimpanan",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
