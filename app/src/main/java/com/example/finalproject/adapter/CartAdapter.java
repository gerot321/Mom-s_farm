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
import com.example.finalproject.page.Cart;
import com.example.finalproject.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;


class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_cart_name, txt_price,txt_shipping_price, txt_total;
    public ImageView img_cart_remove;

    private ItemClickListener itemClickListener;

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        img_cart_remove = itemView.findViewById(R.id.cart_item_delete);
        txt_cart_name = (TextView)itemView.findViewById(R.id.cart_item_name);
        txt_price = (TextView)itemView.findViewById(R.id.cart_item_price);
        txt_shipping_price = (TextView)itemView.findViewById(R.id.cart_stock);
        txt_total = (TextView)itemView.findViewById(R.id.cart_total_price);
    }

    @Override
    public void onClick(View v) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

    private List<Order> listData = new ArrayList<>();
    private Cart cartActivity;

    public CartAdapter(List<Order> listData, Cart cartActivity) {
        this.listData = listData;
        this.cartActivity = cartActivity;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cartActivity.getApplicationContext());
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, final int position) {
//        TextDrawable drawable = TextDrawable.builder()
//                .buildRound(""+listData.get(position).getQuantity(), Color.RED);

//        holder.img_cart_count.setImageDrawable(drawable);

        Order order = listData.get(position);
        holder.img_cart_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAt(holder.getAdapterPosition());
            }
        });
//        int shippingPrice = (Integer.parseInt(listData.get(position).getShippingPrice()));
        holder.txt_price.setText("Harga : "+listData.get(position).getPrice());

        holder.txt_shipping_price.setText("Stok Produk : "+listData.get(position).getQuantity());

        holder.txt_cart_name.setText(listData.get(position).getProductName());
        holder.txt_total.setText("Total Harga: "+String.valueOf(Integer.parseInt(order.getQuantity())*Integer.parseInt(order.getPrice())));
    }

    public void removeAt(int position) {
        listData.remove(position);
        PreferenceUtil.setOrders(listData);
        cartActivity.loadListProduct();
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listData.size());
    }

    public void addData(List<Order> listData){
        this.listData = listData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
