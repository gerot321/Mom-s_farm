package com.example.finalproject.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.PropertyName;

public class Product implements Parcelable {
    private String name, image, price, stock,productId;

    public Product(){

    }

    public Product(String Id,String Name, String Image, String Price, String Stock) {
        productId=Id;
        name = Name;
        image = Image;
        price = Price;
        stock = Stock;;
    }

    protected Product(Parcel in) {
        name = in.readString();
        image = in.readString();
        price = in.readString();
        stock = in.readString();
        productId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(price);
        dest.writeString(stock);
        dest.writeString(productId);
    }

    @Override
    public int describeContents() {
        return 0;
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

    @PropertyName("Name")
    public String getName() {
        return name;
    }
    @PropertyName("Name")
    public void setName(String Name) {
        name = Name;
    }
    @PropertyName("Image")
    public String getImage() {
        return image;
    }
    @PropertyName("Image")
    public void setImage(String Image) {
        image = Image;
    }
    @PropertyName("Price")
    public String getPrice() {
        return price;
    }
    @PropertyName("Price")
    public void setPrice(String Price) {
        price = Price;
    }
    @PropertyName("Stock")
    public String getStock() {
        return stock;
    }
    @PropertyName("Stock")
    public void setStock(String Stock) {
        stock = Stock;
    }
    @PropertyName("ProductId")
    public String getProductId() {
        return productId;
    }
    @PropertyName("ProductId")
    public void setProductId(String productId) {
        this.productId = productId;
    }
}

