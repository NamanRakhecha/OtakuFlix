package com.example.jsouptest;

public class AnimeModel {
    String img_url;
    String title;
    String vidLink;



    public AnimeModel(String img_url, String title, String vidLink) {
        this.img_url = img_url;
        this.title = title;
        this.vidLink= vidLink;
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
    public void setVidLink(String vidLink) {
        this.vidLink = vidLink;
    }

    public String getVidLink() {
        return vidLink;
    }
}
