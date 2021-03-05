package com.example.finalproject.Model;

import com.google.firebase.database.PropertyName;

public class UMKM {
    private String nama, alamat, gambar;

    public UMKM(String alamat, String nama, String gambar) {
        this.alamat=alamat;
        this.nama = nama;
        this.gambar = gambar;
    }
    @PropertyName("nama")
    public String getNama() {
        return nama;
    }
    @PropertyName("nama")
    public void setNama(String nama) {
        this.nama = nama;
    }
    @PropertyName("gambar")
    public String getGambar() {
        return this.gambar;
    }
    @PropertyName("gambar")
    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
    @PropertyName("alamat")
    public String getAlamat() {
        return alamat;
    }
    @PropertyName("alamat")
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

}

