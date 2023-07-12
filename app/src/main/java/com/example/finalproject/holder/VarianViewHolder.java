package com.example.finalproject.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Interface.ItemClickListener;
import com.example.finalproject.R;


public class VarianViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView varianName;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public VarianViewHolder(View itemView) {
        super(itemView);

        varianName = itemView.findViewById(R.id.name);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
