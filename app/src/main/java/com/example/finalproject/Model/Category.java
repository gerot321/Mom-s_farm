package com.example.finalproject.Model;


public class Category {
    private String Name;
    private String Image;
    private String Origin;

    public Category(){

    }

    public Category(String name, String image,String origin) {
        Name = name;
        Image = image;
        Origin = origin;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
