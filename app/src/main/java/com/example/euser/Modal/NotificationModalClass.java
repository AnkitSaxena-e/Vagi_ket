package com.example.euser.Modal;

public class NotificationModalClass {

    String NotiImage, NotiText;

    public NotificationModalClass() {
    }

    public NotificationModalClass(String notiImage, String notiText) {
        NotiImage = notiImage;
        NotiText = notiText;
    }

    public String getNotiImage() {
        return NotiImage;
    }

    public String getNotiText() {
        return NotiText;
    }
}
