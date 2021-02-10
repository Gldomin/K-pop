package com.star.k_pop.model;

import java.util.Random;

/**
 * Класс артист
 */
public class Artist {
    private String group;           // Имя группы
    private String name;            // Имя артиста
    private boolean init = false;   // Использовался артист или нет
    private String[] namesImages;   // Имена картинок
    private boolean sex = false;    // Пол артиста

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
