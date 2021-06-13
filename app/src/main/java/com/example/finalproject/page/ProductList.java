package com.example.finalproject.page;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Common.Common;
import com.example.finalproject.Database.Database;
import com.example.finalproject.Interface.ItemClickListener;
import com.example.finalproject.Model.Order;
import com.example.finalproject.Model.Product;
import com.momsfarm.finalproject.R;
import com.example.finalproject.adapter.OrderAdapter;
import com.example.finalproject.adapter.ProductAdapter;
import com.example.finalproject.base.BaseActivity;
import com.example.finalproject.holder.ShoeViewHolder;
import com.example.finalproject.page.scanner.CodeScannerActivity;
import com.example.finalproject.page.scanner.ScanResultDialog;
import com.example.finalproject.util.PreferenceUtil;
import com.example.finalproject.util.StringUtil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductList extends BaseActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference productList;
    public static final int REQUEST_CODE = 1;
    public static final int REQUEST_CODE_UPDATE = 2;


    int page = 0;
    ProductAdapter adapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        initData();
        iniEnv();
        initView();

    }

    private void initData(){
        page = getIntent().getIntExtra("page",0);

    }

    private void initView(){
        setTitle(toolbar, "Daftar Produk");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_shoe);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProductAdapter(new ArrayList<Product>(),this,page);
        recyclerView.setAdapter(adapter);
        loadListProduct(productList);
    }

    private void iniEnv(){
        database = FirebaseDatabase.getInstance();

        productList = database.getReference("Product");

    }

    private void loadListProduct(final Query query) {
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                List<Product> products = new ArrayList<>();
                for (DataSnapshot ssn : dataSnapshot.getChildren()) {
                    Product product = ssn.getValue(Product.class);
                    if(product.getIsActive().equals("ACTIVE")){
                        products.add(product);
                    }
                }
                adapter.addProduct(products);
//                adapter = new OrderAdapter(orderList, RekapDetail.this);
                query.removeEventListener(this);
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        }) ;
//        adapter = new FirebaseRecyclerAdapter<Product, ShoeViewHolder>(Product.class,
//                R.layout.shoe_item,
//                ShoeViewHolder.class,
//                query) {
////            @Override
////            public void onBindViewHolder(ShoeViewHolder viewHolder, int position) {
////                super.onBindViewHolder(viewHolder, position);
////                if(!getItem(position).getIsActive().equals("ACTIVE")){
////                    adapter.getRef(position).removeValue();
////                }
////            }
//
//            @Override
//            protected void populateViewHolder(ShoeViewHolder viewHolder, final Product model, int position) {
////                if(!model.getIsActive().equals("ACTIVE")){
////                    viewHolder.parent.setVisibility(View.GONE);
////                    viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
////                }
//
//                viewHolder.shoe_name.setText(model.getName());
//                if(!model.getImage().isEmpty()&&!model.getImage().equals(" ")){
//                    Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.shoe_image);
//                }
//
//                final Product local = model;
//                if(page == Common.PAGE_CREATE_QR){
//                    viewHolder.setItemClickListener(new ItemClickListener() {
//                        @Override
//                        public void onClick(View view, int position, boolean isLongCLick) {
//                            Intent intent = new Intent(ProductList.this, CreateQR.class);
//                            intent.putExtra("productId", adapter.getRef(position).getKey());
//                            intent.putExtra("productName", model.getName());
//                            startActivity(intent);
//                        }
//                    });
//                }else if(page == Common.PAGE_UPDATE_PRODUCT){
//                    viewHolder.setItemClickListener(new ItemClickListener() {
//                        @Override
//                        public void onClick(View view, int position, boolean isLongCLick) {
//                            Intent intent = new Intent(ProductList.this, ProductDetail.class);
//                            intent.putExtra("productId", adapter.getRef(position).getKey());
//                            intent.putExtra("product", (Parcelable) model);
//                            startActivity(intent);
//                        }
//                    });
//                }else if(page == Common.PAGE_SHOP){
//                    viewHolder.setItemClickListener(new ItemClickListener() {
//                        @Override
//                        public void onClick(View view, final int position, boolean isLongCLick) {
//
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ScanResultDialog dialog = new ScanResultDialog(ProductList.this, model);
//                                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                                        @Override
//                                        public void onDismiss(DialogInterface dialogInterface) {
//                                            Intent intent = getIntent();
//                                            setResult(RESULT_OK, intent);
//                                            finish();
//                                        }
//                                    });
//                                    dialog.show();
//                                }
//                            });
//
//                        }
//                    });
//                }else if(page == Common.PAGE_RECAP){
//                    viewHolder.setItemClickListener(new ItemClickListener() {
//                        @Override
//                        public void onClick(View view, final int position, boolean isLongCLick) {
//
//                            Intent intent = getIntent();
//                            intent.putExtra("product", (Parcelable) model);
//                            setResult(RESULT_OK, intent);
//                            finish();
//
//                        }
//                    });
//
//                }
//
//
//            }
//        };

//        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scan, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        MenuItem scan = menu.findItem(R.id.scanBtn);
        if(page == Common.PAGE_SHOP){
            scan.setVisible(false);
        }
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                loadListProduct(productList);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadListProduct(productList.orderByChild("name").startAt(query).endAt(query+"\uf8ff"));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadListProduct(productList.orderByChild("name").startAt(newText).endAt(newText+"\uf8ff"));
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scanBtn:
                Intent  intent = new Intent(this, CodeScannerActivity.class);
                intent.putExtra("page", page);
                if(page == Common.PAGE_RECAP){
                    startActivityForResult(intent, REQUEST_CODE);
                }else{
                    startActivity(intent);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {
                Product product = (Product) data.getParcelableExtra("product");
                Intent intent = getIntent();
                intent.putExtra("product", (Parcelable) product);
                setResult(RESULT_OK, intent);
                finish();
            }else if (requestCode == REQUEST_CODE_UPDATE  && resultCode  == RESULT_OK) {
               loadListProduct(productList);
            }
        } catch (Exception ex) {
            Toast.makeText(ProductList.this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }
}
