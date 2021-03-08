package com.example.finalproject.Model;

public class User {
    private String name;
    private String password;
    private String phone;
    private String status;
    private String email;
    private String saldo;
    private String address;
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



}
