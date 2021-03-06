package com.example.finalproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.finalproject.Interface.ItemClickListener;
import com.example.finalproject.Model.Order;
import com.example.finalproject.R;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_name, txt_price,txt_quantity, txt_product_id;

    private ItemClickListener itemClickListener;


    public OrderViewHolder(View itemView) {
        super(itemView);

        txt_name = (TextView)itemView.findViewById(R.id.product_name);
        txt_price = (TextView)itemView.findViewById(R.id.order_total);
        txt_product_id = (TextView)itemView.findViewById(R.id.order_id);
        txt_quantity = (TextView)itemView.findViewById(R.id.order_quantity);


    }

    @Override
    public void onClick(View v) {

    }
}

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder>{

    private List<Order> listData = new ArrayList<>();
    private Context context;

    public OrderAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.order_item_layout, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {

        holder.txt_price.setText("Total Harga : "+listData.get(position).getPrice());
        holder.txt_quantity.setText("Jumlah Produk : "+listData.get(position).getQuantity());
        holder.txt_product_id.setText("Kode Produk : "+listData.get(position).getProductId());
        holder.txt_name.setText("Nama Barang : "+listData.get(position).getProductName());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
