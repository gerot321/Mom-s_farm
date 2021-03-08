package com.example.finalproject.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.PropertyName;

public class Product implements Parcelable {
    private String name, image, price, stock,productId,isActive;

    public Product(){

    }

    public Product(String id,String name, String image, String price, String stock, String isActive) {
        productId=id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.stock = stock;
        this.isActive = isActive;
    }


    protected Product(Parcel in) {
        name = in.readString();
        image = in.readString();
        price = in.readString();
        stock = in.readString();
        productId = in.readString();
        isActive = in.readString();
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

    @PropertyName("isActive")
    public String getIsActive() {
        return isActive;
    }
    @PropertyName("isActive")
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

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
    }
}

