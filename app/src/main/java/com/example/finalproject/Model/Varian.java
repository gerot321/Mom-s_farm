package com.example.finalproject.Model;


import android.os.Parcel;
import android.os.Parcelable;

public class Varian implements Parcelable {
    private int id;
    private String type;
    private int basePrice;
    private int unit;
    private String name;

    public Varian() {
    }

    public Varian(int id, String type, int basePrice, int unit, String name) {
        this.id = id;
        this.type = type;
        this.basePrice = basePrice;
        this.unit = unit;
        this.name = name;
    }

    protected Varian(Parcel in) {
        id = in.readInt();
        type = in.readString();
        basePrice = in.readInt();
        unit = in.readInt();
        name = in.readString();
    }

    public static final Creator<Varian> CREATOR = new Creator<Varian>() {
        @Override
        public Varian createFromParcel(Parcel in) {
            return new Varian(in);
        }

        @Override
        public Varian[] newArray(int size) {
            return new Varian[size];
        }
    };

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(type);
        parcel.writeInt(basePrice);
        parcel.writeInt(unit);
        parcel.writeString(name);
    }
}
