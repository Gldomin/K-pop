package com.star.k_pop.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Класс группа
 */
public class Bands {

    private final String[] name;                // Имя группы
    private final ArrayList<Artist> artists;  // Список артистов
    private final byte numberOfPeople;        // Количество человек
    private final String[] imagesBands;       // Названия картинок
    private final Sex sex;

    public enum Sex {
        MALE,
        FEMALE,
        MIXED
    }

    public Bands(String[] name, ArrayList<Artist> artists, String[] imagesBands, Sex sex) {
        this.name = name;
        this.artists = new ArrayList<>(artists);
        this.numberOfPeople = (byte) artists.size();
        this.imagesBands = imagesBands;
        this.sex = sex;
    }

    public String getName() {
        return name[0];
    }

    public String getNameCorrect() {
        return name[name.length - 1];
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public byte getNumberOfPeople() {
        return numberOfPeople;
    }

    /**
     * @return имя картинки группы
     */
    public String getNamesImages(int i) {
        if (imagesBands.length == 1) {
            return imagesBands[0];
        }
        return imagesBands[i];
    }

    public int getNumberImage(){
        return imagesBands.length;
    }

    public boolean checkGroup(String group) {
        for (String g : name) {
            Log.v("TAG", group.toUpperCase().replaceAll(" ", "") + " " + g.toUpperCase().replaceAll(" ", ""));
            if (group.toUpperCase().replaceAll(" ", "").equals(g.toUpperCase().replaceAll(" ", ""))) {
                return true;
            }
        }
        return false;
    }

    public int getSex() {
        switch (sex) {
            case MALE:
                return 1;
            case FEMALE:
                return 2;
            default:
                return 0;
        }
    }

    /**
     * @return путь: имя группы/картинка
     */
    public String getFolder() {
        return convertStringToPath(getNamesImages(new Random().nextInt(imagesBands.length)));
    }

    public String getFolder(int i){
        return convertStringToPath(getNamesImages(i));
    }

    private String convertStringToPath(String file){
        return "file:///android_asset/Groups/" + name[0] + "/" + file;
    }

    public String getFolderRandom() {
        if (new Random().nextInt(10) > 5) {
            return artists.get(new Random().nextInt(numberOfPeople)).getFolder();
        } else {
            return getFolder();
        }
    }
}
