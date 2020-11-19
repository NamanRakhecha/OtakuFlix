package com.example.jsouptest;

public class AnimeModel {
    String img_url;
    String title;

    public AnimeModel(String img_url, String title) {
        this.img_url = img_url;
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
