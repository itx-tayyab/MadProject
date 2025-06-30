package com.example.madproject.Modals;

public class modalrecyclerview {

    String imageUrl;
    String title;

    public modalrecyclerview() {
        // Default constructor required for Firebase
    }

    public modalrecyclerview(String imageUrl, String title) {
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
