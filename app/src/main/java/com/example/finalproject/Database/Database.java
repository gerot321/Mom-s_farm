package com.example.finalproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.finalproject.Model.Order;
import com.example.finalproject.Model.Product;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;


public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "SneakerHaven.db";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCarts() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductName", "ProductId", "Quantity", "Price","Seller"};
        String sqlTable = "OrderList";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);

        final List<Order> result = new ArrayList<>();

//        if (c.moveToFirst()){
//            do {
//                result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
//                        c.getString(c.getColumnIndex("ProductName")),
//                        c.getString(c.getColumnIndex("Quantity")),
//                        c.getString(c.getColumnIndex("Price")),
//                        c.getString(c.getColumnIndex("Seller"))
//                        ));
//            }while (c.moveToNext());
//        }
        return result;
    }

//    public void addToCart(Order order){
//        SQLiteDatabase db = getReadableDatabase();
//        String query = "";
//        if(CheckIsDataAlreadyInDBorNot("OrderDetail","ProductId",order.getProductId())){
//////            Order lastOrder = getOrder(order.getProductId());
////            int totalOrder = Integer.parseInt(order.getQuantity())+Integer.parseInt(lastOrder.getQuantity());
////            ContentValues cv = new ContentValues();
////            cv.put("Quantity",String.valueOf(totalOrder));
////            db.update("OrderDetail", cv, "ProductId = "+order.getProductId(), null);
//        }else{
//            query = String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,Seller) VALUES('%s','%s','%s','%s','%s');",
//                    order.getProductId(),
//                    order.getProductName(),
//                    order.getQuantity(),
//                    order.getPrice(),
//                    order.getSeller());
//            db.execSQL(query);
//        }
//    }

//    public Order getOrder(String mealid) {
//        SQLiteDatabase db = getReadableDatabase();
//
//        String selectQuery = "SELECT  * FROM " + "OrderDetail" + " WHERE "
//                + "ProductId" + " = " + mealid;
//
//
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        if (c != null)
//            c.moveToFirst();
//
////        Order mealunit = new Order(c.getString(c.getColumnIndex("ProductId")),
////                c.getString(c.getColumnIndex("ProductName")),
////                c.getString(c.getColumnIndex("Quantity")),
////                c.getString(c.getColumnIndex("Price")),
////                c.getString(c.getColumnIndex("Seller"))
////        );
//        c.close();
//        return mealunit;
//    }

    public void clearCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");

        db.execSQL(query);

    }

    public boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                               String dbfield, String fieldValue) {
        SQLiteDatabase db = getReadableDatabase();
        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
