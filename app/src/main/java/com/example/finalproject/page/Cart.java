package com.example.finalproject.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Checkout;
import com.example.finalproject.Common.Common;
import com.example.finalproject.Database.Database;
import com.example.finalproject.Model.Order;
import com.example.finalproject.R;
import com.example.finalproject.adapter.CartAdapter;
import com.example.finalproject.base.BaseActivity;
import com.example.finalproject.page.scanner.CodeScannerActivity;
import com.example.finalproject.util.PreferenceUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Cart extends BaseActivity {
    @BindView(R.id.listCart)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.total)
    TextView txtTotalPrice;
    @BindView(R.id.btnPlaceOrder)
    Button btnPlace;
    @BindView(R.id.btnClearCart)
    Button btnClear;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;
    public static final int REQUEST_CODE = 1;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        new Database(getBaseContext()).clearCart();
        initEnv();
        initView();
    }

    private void initEnv(){
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Order");
//        DatabaseReference cities = requests.child("cities")
//        Query citiesQuery = requests.orderByKey().startAt(input).endAt(input+"\uf8ff");
    }


    private void initView(){
        toolbar.setTitle("Main Menu");
        setSupportActionBar(toolbar);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Order> orders =  PreferenceUtil.getOrders();
                for(Order order : orders){
                    String id = "ORD-"+String.valueOf(System.currentTimeMillis());
                    requests.child(id).setValue(order);
                }
                Toast.makeText(Cart.this,"Order Terbuat",Toast.LENGTH_LONG).show();
                finish();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               PreferenceUtil.clearOrder();
                Toast.makeText(Cart.this, "Cart Cleared!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        loadListProduct();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.scanBtn:
                intent = new Intent(this, CodeScannerActivity.class);
                startActivity(intent);
            case R.id.menuSearch:
                intent = new Intent(this, ProductList.class);
                intent.putExtra("page", Common.PAGE_SHOP);
                startActivityForResult(intent, REQUEST_CODE);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void loadListProduct() {
        cart = PreferenceUtil.getOrders();
        adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);

        //Calculate total price
        int total = 0;

        for (Order order:cart)
            total+=Integer.parseInt(order.getPrice());
        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {
                loadListProduct();
            }
        } catch (Exception ex) {
            Toast.makeText(Cart.this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }
}