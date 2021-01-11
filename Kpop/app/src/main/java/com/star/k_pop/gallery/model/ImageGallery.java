package com.star.k_pop.gallery.model;

import android.net.Uri;

import java.io.Serializable;

/**
 * Модель элментов для галлереи
 */
public class ImageGallery implements Serializable {
    private String name;
    private String group;
    private String folder;

    public ImageGallery(String name) {
        this.name = name;
    }

    public ImageGallery(String name, String group, String folder) {
        this.name = name;
        this.folder = folder;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getUri() {
        return Uri.parse("file:///android_asset/Groups/" + folder);
    }

    public String getTimestamp() {
        return group;
    }

    public void setTimestamp(String timestamp) {
        this.group = timestamp;
    }
}
