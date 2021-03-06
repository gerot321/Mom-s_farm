package com.example.finalproject.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.finalproject.Common.Common;
import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.example.finalproject.util.PreferenceUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainMenu extends AppCompatActivity {
    @BindView(R.id.add_product)
    CardView addProduct;
    @BindView(R.id.shop)
    CardView shop;
    @BindView(R.id.update_product)
    CardView updateProduct;
    @BindView(R.id.create_qr)
    CardView createQR;
    @BindView(R.id.recap)
    CardView recap;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    DatabaseReference table_stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        toolbar.setTitle("Main Menu");
        setSupportActionBar(toolbar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        table_stock = database.getReference("test");

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, AddProduct.class);
                startActivity(intent);
            }
        });

        recap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, RekapOption.class);
                startActivity(intent);
            }
        });

        createQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, ProductList.class);
                intent.putExtra("page",Common.PAGE_CREATE_QR);
                startActivity(intent);
            }
        });


        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, Cart.class);
                intent.putExtra("page",Common.PAGE_SHOP);
                startActivity(intent);
            }
        });


        updateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, ProductList.class);
                intent.putExtra("page",Common.PAGE_UPDATE_PRODUCT);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                PreferenceUtil.clearAll();
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
