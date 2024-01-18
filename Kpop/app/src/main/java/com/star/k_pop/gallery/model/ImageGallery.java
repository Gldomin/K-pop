package com.star.k_pop.gallery.model;

import android.net.Uri;

import java.io.Serializable;

/**
 * Модель элментов для галлереи
 */
public class ImageGallery implements Serializable {
    private String name;
    private String group;

    private boolean isGroup;
    private final String folder;

    public ImageGallery(String name, String group, String folder) {
        this.name = name;
        this.folder = folder;
        this.group = group;
        isGroup = false;
    }

    public ImageGallery(String group, String folder) {
        this.group = group;
        this.folder = folder;
        isGroup = true;
    }

    public String getName() {
        return name;
    }

    public boolean isGroup() {
        return isGroup;
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
}
