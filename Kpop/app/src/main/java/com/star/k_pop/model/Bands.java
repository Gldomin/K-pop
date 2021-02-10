package com.star.k_pop.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Класс группа
 */
public class Bands {

    private String name;                // Имя группы
    private ArrayList<Artist> artists;  // Список артистов
    private byte numberOfPeople;        // Количество человек
    private String[] imagesBands;       // Названия картинок

    public Bands(String name, ArrayList<Artist> artists, String[] imagesBands) {
        this.name = name;
        this.artists = new ArrayList<>(artists);
        this.numberOfPeople = (byte) artists.size();
        this.imagesBands = imagesBands;
    }

    public String getName() {
        return name;
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

    /**
     * @return путь: имя группы/картинка
     */
    public String getFolder() {
        return name + "/" + getNamesImages();
    }
}
