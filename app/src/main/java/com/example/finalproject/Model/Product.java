package com.example.finalproject.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.PropertyName;

public class Product implements Parcelable {
    private String name, image, price, stock,productId,isActive, seller;

    public Product(){

    }

    public Product(String id,String name, String image, String price, String stock, String isActive,String seller) {
        productId=id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.stock = stock;
        this.seller = seller;
        this.isActive = isActive;
    }


    protected Product(Parcel in) {
        name = in.readString();
        image = in.readString();
        price = in.readString();
        stock = in.readString();
        productId = in.readString();
        isActive = in.readString();
        seller = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeString(price);
        parcel.writeString(stock);
        parcel.writeString(productId);
        parcel.writeString(isActive);
        parcel.writeString(seller);
    }

    public String getProductId() {
        return productId;
    }

    public String getSeller() {
        return seller;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getIsActive() {
        return isActive;
    }

    public String getName() {
        return name;
    }

    public String getStock() {
        return stock;
    }

}


