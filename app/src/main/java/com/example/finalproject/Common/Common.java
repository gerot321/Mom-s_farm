package com.example.finalproject.Common;

import com.example.finalproject.Model.User;

import java.util.HashMap;

public class Common {
    public static User currentUser;
    public static int PAGE_UPDATE_PRODUCT = 1;
    public static int PAGE_CREATE_QR = 2;
    public static int PAGE_SHOP = 3;
    public static int PAGE_RECAP = 4;
    public static String VARIAN_GLASS = "VARIAN_GLASS";
    public static String VARIAN_MATBOARD = "VARIAN_MATBOARD";
    public static String VARIAN_LINEN = "VARIAN_LINEN";
    public static String VARIAN_SIZE = "VARIAN_SIZE";
    public static String ORDER_WAITING_PAYMENT = "ORDER_WAITING_PAYMENT";
    public static String ORDER_IN_REVIEW = "ORDER_IN_REVIEW";
    public static String ORDER_PAYMENT_APPROVED = "ORDER_PAYMENT_APPROVED";
    public static String ORDER_PAYMENT_FAILED = "ORDER_PAYMENT_FAILED";
    public static String ORDER_SHIPPING = "ORDER_SHIPPING";
    public static String ORDER_SUCCESS = "ORDER_SUCCESS";
    public static String ORDER_FAILED = "ORDER_FAILED";
    public static String ORDER_ALL = "ALL";

    public static String ROLE_USER = "USER";
    public static String ROLE_ADMIN = "ADMIN";
    public static HashMap<String, String> ORDER_TYPE_STRING = new HashMap<String, String>(){
        {
            put(ORDER_WAITING_PAYMENT, "Menunggu Pembayaran");
            put(ORDER_IN_REVIEW, "Menunggu Persetujuan");
            put(ORDER_PAYMENT_APPROVED, "Pembayaran Disetujui");
            put(ORDER_PAYMENT_FAILED, "Pembayaran Ditolak");
            put(ORDER_SHIPPING, "Dalam Pengiriman");
            put(ORDER_SUCCESS, "Pembelian Berhasil");
            put(ORDER_FAILED, "Pembelian Gagal");
        }
    };
}
