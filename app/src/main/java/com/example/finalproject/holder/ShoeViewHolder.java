package com.example.finalproject.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.Interface.ItemClickListener;
import com.example.finalproject.R;


public class ShoeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView shoe_name;
    public ImageView shoe_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ShoeViewHolder(View itemView) {
        super(itemView);

        shoe_name = itemView.findViewById(R.id.shoe_name);
        shoe_image = itemView.findViewById(R.id.shoe_image);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
