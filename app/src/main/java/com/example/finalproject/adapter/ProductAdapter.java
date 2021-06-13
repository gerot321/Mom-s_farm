package com.example.finalproject.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Common.Common;
import com.example.finalproject.Interface.ItemClickListener;
import com.example.finalproject.Model.Order;
import com.example.finalproject.Model.Product;
import com.momsfarm.finalproject.R;
import com.example.finalproject.page.CreateQR;
import com.example.finalproject.page.ProductDetail;
import com.example.finalproject.page.ProductList;
import com.example.finalproject.page.scanner.ScanResultDialog;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView shoe_name;
    public ImageView shoe_image;
    public CardView parent;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ProductViewHolder(View itemView) {
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

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>{

    private List<Product> listData = new ArrayList<>();
    private ProductList context;
    private int page;
    public ProductAdapter(List<Product> listData, ProductList context, int page) {
        this.listData = listData;
        this.context = context;
        this.page = page;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.shoe_item, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder viewHolder, int position) {
        final Product model = listData.get(position);
        viewHolder.shoe_name.setText(model.getName());
        if(!model.getImage().isEmpty()&&!model.getImage().equals(" ")){
            Picasso.with(context).load(model.getImage()).into(viewHolder.shoe_image);
        }

        final Product local = model;
        if(page == Common.PAGE_CREATE_QR){
            viewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongCLick) {
                    Intent intent = new Intent(context, CreateQR.class);
                    intent.putExtra("productId", model.getProductId());
                    intent.putExtra("productName", model.getName());
                    context.startActivity(intent);
                }
            });
        }else if(page == Common.PAGE_UPDATE_PRODUCT){
            viewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongCLick) {
                    Intent intent = new Intent(context, ProductDetail.class);
                    intent.putExtra("productId", model.getProductId());
                    intent.putExtra("product", (Parcelable) model);
                    context.startActivityForResult(intent, 2);
                }
            });
        }else if(page == Common.PAGE_SHOP){
            viewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, final int position, boolean isLongCLick) {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ScanResultDialog dialog = new ScanResultDialog(context, model);
                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    Intent intent = context.getIntent();
                                    context.setResult(context.RESULT_OK, intent);
                                    context.finish();
                                }
                            });
                            dialog.show();
                        }
                    });

                }
            });
        }else if(page == Common.PAGE_RECAP){
            viewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, final int position, boolean isLongCLick) {

                    Intent intent = context.getIntent();
                    intent.putExtra("product", (Parcelable) model);
                    context.setResult(context.RESULT_OK, intent);
                    context.finish();

                }
            });

        }

    }

    public void addProduct(List<Product> productList){
        listData = productList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
