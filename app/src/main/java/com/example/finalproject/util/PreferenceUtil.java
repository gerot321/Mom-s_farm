package com.example.finalproject.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.finalproject.Model.Order;
import com.example.finalproject.Model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class PreferenceUtil {

    private static SharedPreferences sharedPreferencesToken;
    private static String PREF_TOKEN_KEY = "token";
    private static SharedPreferences sharedPreferencesStatic;
    private static SharedPreferences sharedPreferencesLive;
    private static SharedPreferences sharedPreferencesPersist;


    public static void setContext(Context context) {
        sharedPreferencesToken = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        sharedPreferencesPersist = context.getSharedPreferences("PERSIST", Context.MODE_PRIVATE);

    }

    public static void setUser(User user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        SharedPreferences.Editor editor = sharedPreferencesToken.edit();
        editor.putString("UserData", json);
        editor.commit();
    }

    public static User getUser() {
        Gson gson = new Gson();
        String json = sharedPreferencesToken.getString("UserData", "");
        User user = gson.fromJson(json, User.class);
        return user;
    }


    public static void setPersistUser(User user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        SharedPreferences.Editor editor = sharedPreferencesPersist.edit();
        editor.putString("UserData", json);
        editor.commit();
    }

    public static User getPersistUser() {
        Gson gson = new Gson();
        String json = sharedPreferencesPersist.getString("UserData", "");
        if(json.isEmpty()){
            return null;
        }else{
            User user = gson.fromJson(json, User.class);
            return user;
        }
    }

    public static void setOrders(List<Order> orderList) {

        Gson gson = new Gson();
        String json = gson.toJson(orderList);
        SharedPreferences.Editor editor = sharedPreferencesToken.edit();
        editor.putString("Orders", json);
        editor.commit();
    }

    public static ArrayList<Order> getOrders() {
        Gson gson = new Gson();
        String json = sharedPreferencesToken.getString("Orders", "");
        if(json.isEmpty()){
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<Order>>() {}.getType();
        ArrayList<Order> order = gson.fromJson(json, type);
        return order;
    }

    public static ArrayList<Order> removeOrder(int position) {
        Gson gson = new Gson();
        String json = sharedPreferencesToken.getString("Orders", "");
        if(json.isEmpty()){
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<Order>>() {}.getType();
        ArrayList<Order> order = gson.fromJson(json, type);
        order.remove(position);
        return order;
    }

    public static void clearOrder() {

        Gson gson = new Gson();
        String json = gson.toJson(new ArrayList<>());
        SharedPreferences.Editor editor = sharedPreferencesToken.edit();
        editor.putString("Orders", json);
        editor.commit();
    }

    public static Boolean isUserExist() {
        return sharedPreferencesToken.contains("UserData");
    }


    public static void clearAll(){
        sharedPreferencesToken.edit().clear().commit();
    }

    public static void clearPersist(){
        sharedPreferencesPersist.edit().clear().commit();
    }



}
