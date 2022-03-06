package com.example.euser.Modal;

public class Order {

    private String TotalAmount, Name, PhoneNumber, Address, ByUser, City, Pin, Quantity, FromUser, Check, Buy,
            KeyWord, Return, ONO, PID, Date, Time, DeliveryDetail, PNO, FinalTime, BuyType;

    public Order() {
    }

    public Order(String totalAmount, String name, String phoneNumber, String address, String byUser, String city, String pin, String quantity, String fromUser,
                 String check, String buy, String keyWord, String aReturn, String ONO, String PID, String date, String time, String deliveryDetail, String pno, String finalTime, String buyType) {
        TotalAmount = totalAmount;
        Name = name;
        PhoneNumber = phoneNumber;
        Address = address;
        ByUser = byUser;
        City = city;
        Pin = pin;
        Quantity = quantity;
        FromUser = fromUser;
        Check = check;
        Buy = buy;
        KeyWord = keyWord;
        Return = aReturn;
        this.ONO = ONO;
        this.PID = PID;
        Date = date;
        Time = time;
        DeliveryDetail = deliveryDetail;
        PNO = pno;
        FinalTime = finalTime;
        BuyType = buyType;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPin() {
        return Pin;
    }

    public void setPin(String pin) {
        Pin = pin;
    }

    public String getBuy() {
        return Buy;
    }

    public void setBuy(String buy) {
        Buy = buy;
    }

    public String getReturn() {
        return Return;
    }

    public void setReturn(String aReturn) {
        Return = aReturn;
    }

    public String getPID() {
        return PID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPNO() {
        return PNO;
    }

    public String getByUser() {
        return ByUser;
    }
}
