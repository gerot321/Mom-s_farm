package com.example.finalproject.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.PropertyName;

public class Product implements Parcelable {
    private String name, image, description, price, stock, merchantId,productId;

    public Product(){

    }

    public Product(String Id,String Name, String Image, String Description, String Price, String Stock, String MerchantId) {
        productId=Id;
        name = Name;
        image = Image;
        description = Description;
        price = Price;
        stock = Stock;
        merchantId = MerchantId;
    }

    protected Product(Parcel in) {
        name = in.readString();
        image = in.readString();
        description = in.readString();
        price = in.readString();
        stock = in.readString();
        merchantId = in.readString();
        productId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(stock);
        dest.writeString(merchantId);
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
    @PropertyName("Description")
    public String getDescription() {
        return description;
    }
    @PropertyName("Description")
    public void setDescription(String Description) {
        description = Description;
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
    @PropertyName("MerchantId")
    public String getMerchantId() {
        return merchantId;
    }
    @PropertyName("MerchantId")
    public void setMerchantId(String MerchantId) {
        merchantId = MerchantId;
    }
}

