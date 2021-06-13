package com.example.finalproject.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Interface.ItemClickListener;
import com.momsfarm.finalproject.R;


public class ShoeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView shoe_name;
    public ImageView shoe_image;
    public CardView parent;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ShoeViewHolder(View itemView) {
        super(itemView);

        shoe_name = itemView.findViewById(R.id.shoe_name);
        shoe_image = itemView.findViewById(R.id.shoe_image);
        parent = itemView.findViewById(R.id.parentView);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
