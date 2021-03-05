package com.example.finalproject.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.finalproject.Interface.ItemClickListener;
import com.example.finalproject.R;


public class OrderViewDetailHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtOrderId, txtProdName, txtProdReqStatus, txtProdReqQty, txtProdReqPrice, txtProdReqAddress;

    private ItemClickListener itemClickListener;

    public OrderViewDetailHolder(View itemView) {
        super(itemView);

        txtOrderId = (TextView)itemView.findViewById(R.id.Prod_id);
        txtProdName = (TextView)itemView.findViewById(R.id.ProdName);
        txtProdReqStatus = (TextView)itemView.findViewById(R.id.ProdReqStatus);
        txtProdReqQty = (TextView)itemView.findViewById(R.id.ProdReqQty);
        txtProdReqPrice = (TextView)itemView.findViewById(R.id.ProdReqPrice);
        txtProdReqAddress =(TextView)itemView.findViewById(R.id.ProdReqAddress);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
