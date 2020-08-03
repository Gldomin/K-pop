package com.exam.k_pop;

import java.util.ArrayList;

public class Bands {
    private String name;
    private ArrayList artists;
    private byte numberOfPeople;
    public Bands (String Name, ArrayList Artists, byte NumberOfPeople)
    {
        name = this.name;
        artists = Artists;
        numberOfPeople = NumberOfPeople;
    }
    public String getName(){return name;}
    public ArrayList getArtists(){return artists;}
    public byte getNumberOfPeople(){return numberOfPeople;}
}
