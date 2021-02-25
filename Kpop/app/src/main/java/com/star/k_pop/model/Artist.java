package com.star.k_pop.model;

import android.util.Log;

import java.util.Random;

/**
 * Класс артист
 */
public class Artist {
    private final String[] groups;             // Имя группы
    private final String name;              // Имя артиста
    private boolean init;                   // Использовался артист или нет
    private final String[] namesImages;     // Имена картинок
    private final boolean sex;              // Пол артиста

    public Artist(String[] groups, String name, String[] namesImages, boolean sex) {
        this.groups = groups;
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

    public String getGroups() {
        return groups[0];
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public void Init() {
        this.init = true;
    }

    public String getFolder() {
        return groups[0] + "/" + name + "/" + getNamesImages();
    }

    public boolean checkGroup(String group) {
        for (String g : groups) {
            Log.v("TAG", group.replaceAll(" ", "") + " " + g.toUpperCase().replaceAll(" ", ""));
            if (group.replaceAll(" ", "").equals(g.toUpperCase().replaceAll(" ", ""))) {
                return true;
            }
        }
        return false;
    }

    public boolean isInit() {
        return init;
    }
}
