package com.example.finalproject.Model;


import java.util.List;

public class Option {
    private String name;
    private String id;

    public Option() {
    }

    public Option(String id, String name) {
        this.name = name;
        this.id = id;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return name;
    }
}
