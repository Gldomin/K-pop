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

    public Bands(String[] name, ArrayList<Artist> artists, String[] imagesBands) {
        this.name = name;
        this.artists = new ArrayList<>(artists);
        this.numberOfPeople = (byte) artists.size();
        this.imagesBands = imagesBands;
    }

    public String getName() {
        return name[0];
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
    public String getNamesImages() {
        if (imagesBands.length == 1) {
            return imagesBands[0];
        }
        Random rand = new Random();
        return imagesBands[rand.nextInt(imagesBands.length)];
    }

    public boolean checkGroup(String group) {
        for (String g : name) {
            Log.v("TAG", group.replaceAll(" ", "") + " " + g.toUpperCase().replaceAll(" ", ""));
            if (group.replaceAll(" ", "").equals(g.toUpperCase().replaceAll(" ", ""))) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return путь: имя группы/картинка
     */
    public String getFolder() {
        return name[0] + "/" + getNamesImages();
    }
}
