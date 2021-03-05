package com.example.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.finalproject.Model.productRequest;
import com.example.finalproject.holder.OrderViewDetailHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatusDetail extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference prodReq;
    String key = " ";
    FirebaseRecyclerAdapter<productRequest, OrderViewDetailHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);


        database = FirebaseDatabase.getInstance();
        prodReq = database.getReference("productReq");

        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        key = getIntent().getStringExtra("key");
        loadOrders(key);
    }

    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<productRequest, OrderViewDetailHolder>(
                productRequest.class,
                R.layout.order_detail_layout,
                OrderViewDetailHolder.class,
                prodReq.orderByChild("requestid").equalTo(key)
        ) {
            @Override
            protected void populateViewHolder(OrderViewDetailHolder viewHolder, productRequest model, int position) {
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtProdName.setText(model.getProductname());
                viewHolder.txtProdReqAddress.setText(model.getAddress());
                viewHolder.txtProdReqPrice.setText(model.getTotalprice());
                viewHolder.txtProdReqQty.setText(model.getQuantity());
                viewHolder.txtProdReqStatus.setText(model.getStatus());

            }
        };

        recyclerView.setAdapter(adapter);
    }


}
