package com.example.finalproject.Model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Invoice implements Parcelable {
    private String price;
    private String shippingPrive;
    private String shippingReceipt;

    private Long date;
    private String imageTransaction;
    private String status;
    private String id;
    private User buyer;

    private List<Order> orders;

    public Invoice() {
    }

    public Invoice(String id, User buyer, List<Order> orders, String imageTransaction, String price, Long date, String status) {
        this.buyer = buyer;
        this.orders = orders;
        this.price = price;
        this.shippingPrive = "9000";
        this.date = date;
        this.status = status;
        this.imageTransaction = imageTransaction;
        this.id = id;
    }


    protected Invoice(Parcel in) {
        price = in.readString();
        shippingPrive = in.readString();
        shippingReceipt = in.readString();
        if (in.readByte() == 0) {
            date = null;
        } else {
            date = in.readLong();
        }
        imageTransaction = in.readString();
        status = in.readString();
        id = in.readString();
        buyer = in.readParcelable(User.class.getClassLoader());
        orders = in.createTypedArrayList(Order.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(price);
        dest.writeString(shippingPrive);
        dest.writeString(shippingReceipt);
        if (date == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(date);
        }
        dest.writeString(imageTransaction);
        dest.writeString(status);
        dest.writeString(id);
        dest.writeParcelable(buyer, flags);
        dest.writeTypedList(orders);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Invoice> CREATOR = new Creator<Invoice>() {
        @Override
        public Invoice createFromParcel(Parcel in) {
            return new Invoice(in);
        }

        @Override
        public Invoice[] newArray(int size) {
            return new Invoice[size];
        }
    };

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
    public String getPrice() {
        return price;
    }


    public void setPrice(String price) {
        this.price = price;
    }


    public String getImageTransaction() {
        return imageTransaction;
    }

    public void setImageTransaction(String imageTransaction) {
        this.imageTransaction = imageTransaction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getShippingPrive() {
        return shippingPrive;
    }

    public void setShippingPrive(String shippingPrive) {
        this.shippingPrive = shippingPrive;
    }

    public String getShippingReceipt() {
        return shippingReceipt;
    }

    public void setShippingReceipt(String shippingReceipt) {
        this.shippingReceipt = shippingReceipt;
    }


}
