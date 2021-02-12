package com.star.k_pop.model;

import java.util.Random;

/**
 * Класс артист
 */
public class Artist {
    private final String group;             // Имя группы
    private final String name;              // Имя артиста
    private boolean init;                   // Использовался артист или нет
    private final String[] namesImages;     // Имена картинок
    private final boolean sex;              // Пол артиста

    public Artist(String group, String name, String[] namesImages, boolean sex) {
        this.group = group;
        this.name = name;
        this.namesImages = namesImages; //массив имен всех фоток артиста
        this.sex = sex;
        init = false;
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
