/*
 * MIT License
 *
 * Copyright (c) 2018 Yuriy Budiyev [yuriy.budiyev@yandex.ru]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.example.finalproject.page.scanner;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.finalproject.Common.Common;
import com.example.finalproject.Database.Database;
import com.example.finalproject.Model.Order;
import com.example.finalproject.Model.Product;
import com.example.finalproject.R;

import com.example.finalproject.page.ProductList;
import com.example.finalproject.util.PreferenceUtil;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScanResultDialog extends AppCompatDialog {
    int orderQuantityCart = 0;
    public ScanResultDialog(final Context context, final Product result) {
        super(context, resolveDialogTheme(context));
        PreferenceUtil.setContext(context);
        setTitle("Tambah Produk");
        setContentView(R.layout.dialog_scan_result);


        List<Order> orders = PreferenceUtil.getOrders();

        for(Order order : orders){
            if(order.getProductId().equals(result.getProductId())){
                orderQuantityCart = Integer.parseInt(order.getQuantity());
            }
        }

        Button cancelBtn = findViewById(R.id.cancel_button);
        Button addBtn = findViewById(R.id.add_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        TextView name = findViewById(R.id.product_name);
        TextView price = findViewById(R.id.product_price);
        final TextView stock = findViewById(R.id.product_stock);
        final EditText stockEdt = findViewById(R.id.product_stock_edt);
        RelativeLayout minusStock = findViewById(R.id.minus_stock);
        RelativeLayout plusStock = findViewById(R.id.plus_stock);


        plusStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((Integer.parseInt(stockEdt.getText().toString())+ orderQuantityCart)<Integer.parseInt(result.getStock())){
                    int stockValue = Integer.parseInt(stockEdt.getText().toString())+1;
                    stockEdt.setText(String.valueOf(stockValue));
                }
            }
        });

        minusStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(stockEdt.getText().toString())>0){
                    int stockValue = Integer.parseInt(stockEdt.getText().toString())-1;
                    stockEdt.setText(String.valueOf(stockValue));
                }
            }
        });


        name.setText(result.getName());
        price.setText(result.getPrice());
        stock.setText(String.valueOf(Integer.parseInt(result.getStock())-orderQuantityCart));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Order> orders = PreferenceUtil.getOrders();
                boolean found = false;
                for(int i=0;i<orders.size();i++){
                    if(orders.get(i).getProductId().equals(result.getProductId())){
                        found = true;

                        orders.get(i).setQuantity(String.valueOf(
                                Integer.parseInt(orders.get(i).getQuantity())
                                + Integer.parseInt(stockEdt.getText().toString())
                        ));
                    }
                }
                Date date = new Date();
                if(!found){
                    orders.add(new Order(
                            result.getProductId(),
                            result.getName(),
                            stockEdt.getText().toString(),
                            result.getPrice(),
                            PreferenceUtil.getUser().getPhone(),
                            date.getTime()
                    ));
                }
                PreferenceUtil.setOrders(orders);
                dismiss();
            }
        });
    }



    private static int resolveDialogTheme(Context context) {
        TypedValue outValue = new TypedValue();
        return outValue.resourceId;
    }
}
