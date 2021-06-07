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
import com.momsfarm.finalproject.R;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_name, txt_price,txt_quantity, txt_product_id, txt_product_price, txt_order_date, txt_type, txt_seller_name;

    private ItemClickListener itemClickListener;


    public OrderViewHolder(View itemView) {
        super(itemView);

        txt_name = (TextView)itemView.findViewById(R.id.product_name);
        txt_price = (TextView)itemView.findViewById(R.id.order_total);
        txt_product_id = (TextView)itemView.findViewById(R.id.order_id);
        txt_quantity = (TextView)itemView.findViewById(R.id.order_quantity);
        txt_product_price = (TextView)itemView.findViewById(R.id.product_price);
        txt_order_date = (TextView)itemView.findViewById(R.id.order_date);
        txt_type = (TextView)itemView.findViewById(R.id.order_type);
        txt_seller_name = (TextView)itemView.findViewById(R.id.seller_name);
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
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yy");
        Date date = new Date(listData.get(position).getDate());
        holder.txt_price.setText("Total Harga : "+listData.get(position).getPrice());
        holder.txt_quantity.setText("Jumlah Produk : "+listData.get(position).getQuantity());
        holder.txt_product_id.setText("Kode Produk : "+listData.get(position).getProductId());
        holder.txt_name.setText("Nama Produk : "+listData.get(position).getProductName());
        holder.txt_product_price.setText("Harga Produk : "+Integer.parseInt(listData.get(position).getPrice())/Integer.parseInt(listData.get(position).getQuantity()));
        holder.txt_order_date.setText("Tanggal Order : "+formater.format(date));
        holder.txt_type.setText("Tipe Order : "+listData.get(position).getType());
        holder.txt_seller_name.setText("Penjual : "+listData.get(position).getSeller());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
