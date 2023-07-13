package com.example.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.finalproject.Interface.ItemClickListener;
import com.example.finalproject.Model.Order;
import com.example.finalproject.R;
import com.example.finalproject.util.PreferenceUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_cart_name, txt_price, varianDetail, quantity;
    public ImageView removeItem;
    public ImageView imageView;

    private ItemClickListener itemClickListener;

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(View itemView) {
        super(itemView);

        txt_cart_name = (TextView)itemView.findViewById(R.id.name);
        txt_price = (TextView)itemView.findViewById(R.id.price);
        removeItem = itemView.findViewById(R.id.removeItem);
        varianDetail = itemView.findViewById(R.id.varianDetail);
        imageView = itemView.findViewById(R.id.image);
        quantity = itemView.findViewById(R.id.qty);
//        txt_shipping_price = (TextView)itemView.findViewById(R.id.cart_shipping_price);

    }

    @Override
    public void onClick(View v) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        holder.txt_price.setText("Total Price : " + listData.get(position).getPrice());
        holder.removeItem.setOnClickListener(view -> {
            listData.remove(position);
            notifyItemRemoved(position);
            PreferenceUtil.removeOrder(position);
        });
        holder.txt_cart_name.setText(listData.get(position).getProduct().getName());
        holder.varianDetail.setText(new StringBuilder().append("Linen ").
                append(listData.get(position).getLinen().getName()).
                append(", Mattboard ").
                append(listData.get(position).getMattboard().getName()).
                append(", Kaca ").
                append(listData.get(position).getGlass().getName()).toString());
        if(listData.get(position).getProduct().getImage() != null && !listData.get(position).getProduct().getImage().isEmpty()){
            Picasso.with(context).load(listData.get(position).getProduct().getImage()).into(holder.imageView);
        }
        holder.quantity.setText("Jumlah: " + listData.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
