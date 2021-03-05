package com.example.finalproject.Model;

public class Voucher {

    String id;
    String value;
    String status;

    public Voucher() {
    }

    public Voucher(String id,String value,String status) {
        this.id=id;
        this.value=value;
        this.status=status;

    }

    public String getValue() {
        return value;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
