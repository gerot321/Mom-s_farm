package com.example.finalproject.Model;


import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {

    private String quantity;
    private String price;
    private Long date;
    private Varian size;
    private Varian mattboard;
    private Varian linen;
    private Varian glass;
    private String id;
    private Product product;

    public Order() {
    }

    public Order(String id,Product product, String quantity, String price, Long date, Varian size, Varian mattboard, Varian linen, Varian glass, String status) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
        this.mattboard = mattboard;
        this.linen = linen;
        this.glass = glass;
        this.date = date;
        this.id = id;

    }


    protected Order(Parcel in) {
        quantity = in.readString();
        price = in.readString();
        if (in.readByte() == 0) {
            date = null;
        } else {
            date = in.readLong();
        }
        size = in.readParcelable(Varian.class.getClassLoader());
        mattboard = in.readParcelable(Varian.class.getClassLoader());
        linen = in.readParcelable(Varian.class.getClassLoader());
        glass = in.readParcelable(Varian.class.getClassLoader());
        id = in.readString();
        product = in.readParcelable(Product.class.getClassLoader());
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
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

    public String getQuantity() {
        return quantity;
    }


    public void setPrice(String price) {
        this.price = price;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Varian getGlass() {
        return glass;
    }

    public void setGlass(Varian glass) {
        this.glass = glass;
    }

    public Varian getLinen() {
        return linen;
    }

    public void setLinen(Varian linen) {
        this.linen = linen;
    }

    public Varian getSize() {
        return size;
    }

    public void setSize(Varian size) {
        this.size = size;
    }

    public Varian getMattboard() {
        return mattboard;
    }

    public void setMattboard(Varian mattboard) {
        this.mattboard = mattboard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(quantity);
        parcel.writeString(price);
        if (date == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(date);
        }
        parcel.writeParcelable(size, i);
        parcel.writeParcelable(mattboard, i);
        parcel.writeParcelable(linen, i);
        parcel.writeParcelable(glass, i);
        parcel.writeString(id);
        parcel.writeParcelable(product, i);
    }
}
