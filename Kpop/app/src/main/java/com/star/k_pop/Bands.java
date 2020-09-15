package com.star.k_pop;

import java.util.ArrayList;
import java.util.Random;

public class Bands {
    private String name;
    private ArrayList<Artist> artists;
    private byte numberOfPeople;
    private String[] imagesBands;

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

    public String getNamesImages() {
        if(imagesBands.length == 1){
            return imagesBands[0];
        }
        Random rand = new Random();
        return imagesBands[rand.nextInt(imagesBands.length)]; //возвращаем рандомную фотку Артиста
    }

    public String getFolder() {
        return name + "/" + getNamesImages();
    }
}
