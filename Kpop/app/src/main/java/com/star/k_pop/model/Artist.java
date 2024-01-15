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

    public int countRandom = -1;

    public Artist(String[] groups, String name, String[] namesImages, boolean sex) {
        this.groups = groups;
        this.name = name;
        this.namesImages = namesImages; //массив имен всех фоток артиста
        this.sex = sex;
        init = false;
    }

    public String getNamesImage(int i) {
        return namesImages[i]; //возвращаем рандомную фотку Артиста
    }

    public void removeRandomCount(){
        countRandom = -1;
    }

    public boolean isSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return groups[0];
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public void Init() {
        this.init = true;
    }

    public String getFolder() {
        Random rand = new Random();
        return groups[0] + "/" + name + "/" + getNamesImage(rand.nextInt(namesImages.length));
    }

    public int getCountImages(){
        return namesImages.length;
    }

    public String getFolder(int i) {
        return groups[0] + "/" + name + "/" + getNamesImage(i);
    }

    public String getFolderNotRandom() {
        if (countRandom < 0){
            Random rand = new Random();
            countRandom = rand.nextInt(namesImages.length);
        }
        return groups[0] + "/" + name + "/" + getNamesImage(countRandom);
    }

    public boolean checkGroup(String group) {
        for (String g : groups) {
            Log.v("TAG", group.toUpperCase().replaceAll(" ", "") + " " + g.toUpperCase().replaceAll(" ", ""));
            if (group.toUpperCase().replaceAll(" ", "").equals(g.toUpperCase().replaceAll(" ", ""))) {
                return true;
            }
        }
        return false;
    }

    public boolean isInit() {
        return init;
    }
}
