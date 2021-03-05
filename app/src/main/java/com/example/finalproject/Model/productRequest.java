package com.example.finalproject.Model;



public class productRequest {
    private String productname;
    private String requestid;
    private String merchantid;
    private String productid;
    private String quantity;
    private String totalprice;
    private String address;
    private String status;
    private String shippingPrice;

    public productRequest(){

    }

    public productRequest(String requestid, String merchantid, String productid, String productname, String quantity, String totalprice, String address, String status,String shippingPrice) {
        this.requestid=requestid;
        this.merchantid=merchantid;
        this.productid=productid;
        this.productname=productname;
        this.quantity=quantity;
        this.totalprice=totalprice;
        this.address=address;
        this.status=status;
        this.shippingPrice = shippingPrice;

    }

    public void setShippingPrice(String shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getShippingPrice() {
        return shippingPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getAddress() {
        return address;
    }

    public String getMerchantid() {
        return merchantid;
    }

    public String getProductid() {
        return productid;
    }

    public String getProductname() {
        return productname;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }
}
