package com.example.eadmin.Modal;

public class AdModalClass {

    private String Image, Text, Link;

    public AdModalClass() {
    }

    public AdModalClass(String image, String text, String link) {
        Image = image;
        Text = text;
        Link = link;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

}
