package com.example.finalproject.page;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.finalproject.Common.Common;
import com.example.finalproject.Database.Database;
import com.example.finalproject.Interface.ItemClickListener;
import com.example.finalproject.Model.Order;
import com.example.finalproject.Model.Product;
import com.example.finalproject.R;
import com.example.finalproject.holder.ShoeViewHolder;
import com.example.finalproject.page.scanner.CodeScannerActivity;
import com.example.finalproject.page.scanner.ScanResultDialog;
import com.example.finalproject.util.PreferenceUtil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference productList;

    int page = 0;
    FirebaseRecyclerAdapter<Product, ShoeViewHolder> adapter;
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
        toolbar.setTitle("Daftar Produk");
        setSupportActionBar(toolbar);


        recyclerView = (RecyclerView)findViewById(R.id.recycler_shoe);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadListProduct(productList);
    }

    private void iniEnv(){
        database = FirebaseDatabase.getInstance();

        productList = database.getReference("Product");

    }

    private void loadListProduct(Query query) {
        adapter = new FirebaseRecyclerAdapter<Product, ShoeViewHolder>(Product.class,
                R.layout.shoe_item,
                ShoeViewHolder.class,
                query) {
            @Override
            protected void populateViewHolder(ShoeViewHolder viewHolder, final Product model, int position) {
                viewHolder.shoe_name.setText(model.getName());

                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.shoe_image);

                final Product local = model;
                if(page == Common.PAGE_CREATE_QR){
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongCLick) {
                            Intent intent = new Intent(ProductList.this, CreateQR.class);
                            intent.putExtra("productId", adapter.getRef(position).getKey());
                            startActivity(intent);
                        }
                    });
                }else if(page == Common.PAGE_UPDATE_PRODUCT){
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongCLick) {
                            Intent intent = new Intent(ProductList.this, ProductDetail.class);
                            intent.putExtra("productId", adapter.getRef(position).getKey());
                            intent.putExtra("product", (Parcelable) model);
                            startActivity(intent);
                        }
                    });
                }else if(page == Common.PAGE_SHOP){
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, final int position, boolean isLongCLick) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ScanResultDialog dialog = new ScanResultDialog(ProductList.this, model, adapter.getRef(position).getKey());
                                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialogInterface) {
                                            Intent intent = getIntent();
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        }
                                    });
                                    dialog.show();
                                }
                            });

                        }
                    });
                }


            }
        };

        recyclerView.setAdapter(adapter);
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
                loadListProduct(productList.orderByChild("Name").startAt(query).endAt(query+"\uf8ff"));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadListProduct(productList.orderByChild("Name").startAt(newText).endAt(newText+"\uf8ff"));
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scanBtn:
                Intent intent = new Intent(this, SignIn.class);
                PreferenceUtil.clearAll();
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
