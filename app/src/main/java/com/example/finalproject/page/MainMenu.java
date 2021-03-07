package com.example.finalproject.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.Common.Common;
import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.example.finalproject.util.PreferenceUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainMenu extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
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
    TextView txtFullName, editProfile;
    ImageView myImage;

    DatabaseReference table_stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);



        FirebaseDatabase database = FirebaseDatabase.getInstance();

        table_stock = database.getReference("test");


        initView();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    private void initView(){
        toolbar.setTitle("Main Menu");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txtFullName = headerView.findViewById(R.id.user_name);
        txtFullName.setText(PreferenceUtil.getUser().getName());
        editProfile = headerView.findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, EditProfile.class);
                startActivity(intent);
            }
        });
        myImage = headerView.findViewById(R.id.my_image);
        Picasso.with(this).load(PreferenceUtil.getUser().getImage()).into(myImage);


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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.logout:
//                Intent intent = new Intent(this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                PreferenceUtil.clearAll();
//                startActivity(intent);
//                finish();
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_edit_profile) {
//
//        } else
        if (id == R.id.nav_log_out) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PreferenceUtil.clearAll();
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
