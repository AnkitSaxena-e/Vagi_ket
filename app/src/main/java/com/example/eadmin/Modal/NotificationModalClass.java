package com.example.eadmin.Modal;

public class NotificationModalClass {

    String NotiImage, NotiText, Pid;

    public NotificationModalClass() {
    }

    public NotificationModalClass(String notiImage, String notiText, String pid) {
        NotiImage = notiImage;
        NotiText = notiText;
        Pid = pid;
    }

    public String getNotiImage() {
        return NotiImage;
    }

    public String getNotiText() {
        return NotiText;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }
}
