package com.example.euser.Modal;

public class CategoryModal {

    int image;
    String name;

    public CategoryModal() {
    }

    public CategoryModal(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
