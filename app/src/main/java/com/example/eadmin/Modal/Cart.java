package com.example.eadmin.Modal;

public class Cart {

    private String Date, Discount, PDescription, PName, PPrice, Quantity, Time, pid;

    public Cart() {
    }

    public Cart(String date, String discount, String PDescription, String PName, String PPrice, String quantity, String time, String pid) {
        Date = date;
        Discount = discount;
        this.PDescription = PDescription;
        this.PName = PName;
        this.PPrice = PPrice;
        Quantity = quantity;
        Time = time;
        this.pid = pid;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getPDescription() {
        return PDescription;
    }

    public void setPDescription(String PDescription) {
        this.PDescription = PDescription;
    }

    public String getPName() {
        return PName;
    }

    public void setPName(String PName) {
        this.PName = PName;
    }

    public String getPPrice() {
        return PPrice;
    }

    public void setPPrice(String PPrice) {
        this.PPrice = PPrice;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}