package com.example.euser.Modal;

public class Users {

    private String Name, Number, Passward, Image, Address, Pin, Suspend, Token, UID, Deli, RCode;

    public Users() {
    }

    public Users(String name, String number, String passward, String image, String address, String pin, String suspend, String token, String UID, String deli, String rCode) {
        Name = name;
        Number = number;
        Passward = passward;
        Image = image;
        Address = address;
        Pin = pin;
        Suspend = suspend;
        Token = token;
        this.UID = UID;
        Deli = deli;
        RCode = rCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getPassward() {
        return Passward;
    }

    public void setPassward(String passward) {
        Passward = passward;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPin() {
        return Pin;
    }

    public void setPin(String pin) {
        Pin = pin;
    }

    public String getSuspend() {
        return Suspend;
    }
}
