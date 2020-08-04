package com.exam.k_pop;


import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
/*
класс является посредиком между Assets и основным модулем и достает нам всех артистов и группы. класс может перемешать запрашиваемый массив артистов/группы
 */
public class Importer {
    private ArrayList tempImport;
//

    public ArrayList getAllArtists(AssetManager assetManager) //достает всех артистов и дает нам массив полных имен (с путем от Assets)
    {
        tempImport.clear();
        int i = 0;
        tempImport = new ArrayList<>();
        try {
            String[] groupName = assetManager.list("Groups");
            for (String s : groupName) {
                try {
                    String[] nameArtist = assetManager.list("Groups/" + s);
                    for (String folder : nameArtist) {
                        //Создание объекта
                        Log.i(s, "createArtist:" + i);
                        i++;
                        tempImport.add(new Artist(s, folder, assetManager.list("Groups/" + s + "/" + folder)));
                    }
                } catch (IOException ignored) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempImport;
    }
    public ArrayList getScrambledArtists(AssetManager assetManager) //возращает перемешанный arrayList (рандомно перемешанный)
    {tempImport.clear();
        getAllArtists(assetManager);
        Collections.shuffle(tempImport);
        return tempImport;
    }
    public ArrayList GetAllBands(AssetManager assetManager) {
        tempImport.clear();
        int i = 0;
        ArrayList artists = new ArrayList();
        tempImport = new ArrayList<>();
        try {
            String[] groupName = assetManager.list("Groups");
                        for (String s : groupName) {
                try {
                    String[] nameArtist = assetManager.list("Groups/" + s);

                    for (String folder : nameArtist) {
                        //Создание объекта
                        Log.i(s, "createArtist:" + i);
                        i++;
                        artists.add(new Artist(s, folder, assetManager.list("Groups/" + s + "/" + folder)));
                    }
                    tempImport.add(new Bands(s,new ArrayList(artists), (byte) nameArtist.length));
                } catch (IOException ignored) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempImport;
    }
}
