package com.exam.k_pop.gallery.model;

import android.net.Uri;

import java.io.Serializable;

/**
 * Модель элментов для галлереи
 */
public class ImageGallery implements Serializable {
    private String name;
    private String timestamp;

    public ImageGallery(String name) {
        this.name = name;
    }

    public ImageGallery(String name, String timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getUri() {
        return Uri.parse("file:///android_asset/Groups/" + name);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
