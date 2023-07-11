package com.example.finalproject.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String name;
    private String password;
    private String phone;
    private String status;
    private String email;
    private String saldo;
    private String address;
    private String role;
    private String gender;
    private String tanggalLahir;
    private String image;
    public User(){

    }

    public User(String name, String password,String status,String phone,String address,String gender,String tanggalLahir,String hobi, String profesi,String image,String email,String verified) {
        this.name = name;
        this.password = password;
        this.status = status;
        this.phone=phone;
        this.address=address;
        this.gender=gender;
        this.tanggalLahir=tanggalLahir;
        this.image=image;
        this.email=email;
    }

    protected User(Parcel in) {
        name = in.readString();
        password = in.readString();
        phone = in.readString();
        status = in.readString();
        email = in.readString();
        saldo = in.readString();
        address = in.readString();
        role = in.readString();
        gender = in.readString();
        tanggalLahir = in.readString();
        image = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getImage() {
        return image;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public void setImage(String image) {
        this.image = image;
    }


    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getSaldo() {
        return saldo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getStatus() {
        return status;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(password);
        parcel.writeString(phone);
        parcel.writeString(status);
        parcel.writeString(email);
        parcel.writeString(saldo);
        parcel.writeString(address);
        parcel.writeString(role);
        parcel.writeString(gender);
        parcel.writeString(tanggalLahir);
        parcel.writeString(image);
    }
}
