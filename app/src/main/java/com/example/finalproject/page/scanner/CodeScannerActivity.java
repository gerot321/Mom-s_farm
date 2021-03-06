/*
 * MIT License
 *
 * Copyright (c) 2018 Yuriy Budiyev [yuriy.budiyev@yandex.ru]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.example.finalproject.page.scanner;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.example.finalproject.Common.Common;
import com.example.finalproject.Database.Database;
import com.example.finalproject.Model.Order;
import com.example.finalproject.Model.Product;
import com.example.finalproject.Model.User;
import com.example.finalproject.R;

import com.budiyev.android.codescanner.CodeScanner;
import com.example.finalproject.base.BaseCameraActivity;
import com.example.finalproject.page.MainMenu;
import com.example.finalproject.page.ProductDetail;
import com.example.finalproject.page.ProductList;
import com.example.finalproject.page.SignIn;
import com.example.finalproject.util.PreferenceUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CodeScannerActivity extends BaseCameraActivity {
    private static final int RC_PERMISSION = 10;
    private CodeScanner mCodeScanner;
    private boolean mPermissionGranted;
    @BindView(R.id.scanner)
    CodeScannerView codeScannerView;

    int sourcePage = 0;
    FirebaseDatabase database;
    DatabaseReference productReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_scanner);
        ButterKnife.bind(this);
        sourcePage = getIntent().getIntExtra("page",0);
        iniEnv();
        initView();
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initView(){
        mCodeScanner = new CodeScanner(this, codeScannerView);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showProgress();
                    }
                });
                productReference.child(result.getText()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                disProgress();
                            }
                        });
                        final Product product =dataSnapshot.getValue(Product.class);
                        if(sourcePage == Common.PAGE_SHOP){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ScanResultDialog dialog = new ScanResultDialog(CodeScannerActivity.this, product);
                                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialogInterface) {
                                            mCodeScanner.startPreview();
                                        }
                                    });
                                    dialog.show();
                                }
                            });
                        }else if(sourcePage == Common.PAGE_UPDATE_PRODUCT){
                            Intent intent = new Intent(CodeScannerActivity.this, ProductDetail.class);
                            intent.putExtra("productId", product.getProductId());
                            intent.putExtra("product", (Parcelable) product);
                            startActivity(intent);
                            finish();
                        }else if(sourcePage == Common.PAGE_RECAP){
                            Intent intent = getIntent();
                            intent.putExtra("product", (Parcelable) product);
                            setResult(RESULT_OK, intent);
                            finish();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        mCodeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(Exception error) {
                Toast.makeText(CodeScannerActivity.this, getString(R.string.scanner_error, error), Toast.LENGTH_LONG).show();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = false;
                requestPermissions(new String[] {Manifest.permission.CAMERA}, RC_PERMISSION);
            } else {
                mPermissionGranted = true;
            }
        } else {
            mPermissionGranted = true;
        }
    }

    private void iniEnv(){
        database = FirebaseDatabase.getInstance();

        productReference = database.getReference("Product");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,
            int[] grantResults) {
        if (requestCode == RC_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = true;
                mCodeScanner.startPreview();
            } else {
                mPermissionGranted = false;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPermissionGranted) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}
