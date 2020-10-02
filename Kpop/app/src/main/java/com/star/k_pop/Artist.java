package com.star.k_pop;

import java.util.Random;

public class Artist {
    private String group;
    private String name;
    private boolean init = false;
    private String[] namesImages;
    private boolean sex = false;

    public Artist(String group, String name, String[] namesImages, boolean sex) {
        this.group = group;
        this.name = name;
        this.namesImages = namesImages; //массив имен всех фоток артиста
        this.sex = sex;
    }

    public String getNamesImages() {
        Random rand = new Random();
        return namesImages[rand.nextInt(namesImages.length)]; //возвращаем рандомную фотку Артиста
    }

    public boolean isSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public void Init() {
        this.init = true;
    }

    public String getFolder() {
        return group + "/" + name + "/" + getNamesImages();
    }

    public boolean isInit() {
        return init;
    }
}
