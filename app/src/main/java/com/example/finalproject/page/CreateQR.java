package com.example.finalproject.page;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;

import com.momsfarm.finalproject.R;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateQR extends AppCompatActivity {

    @BindView(R.id.idIVQrcode)
    ImageView qrCodeIV;
    @BindView(R.id.save_qr)
    Button save_qr_code;
    @BindView(R.id.parent_img)
    FrameLayout parentImage;
    @BindView(R.id.prod_name)
    TextView prodName;

    final private int PERMISSION = 1;


    private static final int PERMISSION_REQUEST_CODE = 1001;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String productId;
    String productName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qr);
        ButterKnife.bind(this);
        initData();
        initView();

    }

    private void initData(){
        productId = getIntent().getStringExtra("productId");
        productName = getIntent().getStringExtra("productName");
    }

    private void initView(){
//        String output = str.substring(0, 1).toUpperCase() + str.substring(1);

        prodName.setText(productName.substring(0, 1).toUpperCase() + productName.substring(1));

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
                checkPermission();
            }
        });
    }


    private void saveImageToGallery(ImageView iv){
//        BitmapDrawable draw = (BitmapDrawable) iv.getDrawable();
//        Bitmap bitmap = draw.getBitmap();
        parentImage.setDrawingCacheEnabled(true);

        parentImage.buildDrawingCache();

        Bitmap bitmap = parentImage.getDrawingCache();
        FileOutputStream outStream = null;
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/Mom's Farm");
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
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(outFile));
        sendBroadcast(intent);
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
        }else{
            saveImageToGallery(qrCodeIV);
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
