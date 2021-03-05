package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.finalproject.Interface.ItemClickListener;
import com.example.finalproject.Model.Product;
import com.example.finalproject.holder.ShoeViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ShoeList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference shoeList;

    String categoryId= "";

    FirebaseRecyclerAdapter<Product, ShoeViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoe_list);

        // Firebase
        database = FirebaseDatabase.getInstance();
        shoeList = database.getReference("Product");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_shoe);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Get intent

            categoryId = getIntent().getStringExtra("CategoryId");

            loadListShoe(categoryId);


    }

    private void loadListShoe(final String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Product, ShoeViewHolder>(Product.class,
                R.layout.shoe_item,
                ShoeViewHolder.class,
                shoeList.orderByChild("MerchantId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(ShoeViewHolder viewHolder, Product model, int position) {
                viewHolder.shoe_name.setText(model.getName());

                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.shoe_image);

                final Product local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongCLick) {
//                        Intent intent = new Intent(ShoeList.this, ShoeDetail.class);
//                        intent.putExtra("ShoeId", adapter.getRef(position).getKey());
//                        intent.putExtra("Merchant", categoryId);
//                        startActivity(intent);
                    }
                });

            }
        };

        // Set adapter
        recyclerView.setAdapter(adapter);
    }
}
