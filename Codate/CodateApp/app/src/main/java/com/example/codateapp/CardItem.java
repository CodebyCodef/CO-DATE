package com.example.codateapp;

public class CardItem {
    private String adSoyad;
    private String yas;
    private int imageResId;
    private String biography;

    public CardItem(String adSoyad, String yas, int imageResId, String biography) {
        this.adSoyad = adSoyad;
        this.yas = yas;
        this.imageResId = imageResId;
        this.biography = biography;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public String getYas() {
        return yas;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getBiography() {
        return biography;
    }
}
