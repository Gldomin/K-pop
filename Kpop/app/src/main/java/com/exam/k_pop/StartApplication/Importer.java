package com.exam.k_pop.StartApplication;


import android.content.res.Resources;
import android.util.Log;

import com.exam.k_pop.Artist;
import com.exam.k_pop.Bands;
import com.exam.k_pop.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/*
класс является посредиком между Assets и основным модулем и достает нам всех артистов и группы. класс может перемешать запрашиваемый массив артистов/группы

Пример использования:
  ArrayList<Band> bands = Importer.getBands();
  ArrayList<Artist> artists = Importer.getRandomArtists();
 */
public class Importer {

    private static final String TAG = "Артисты";
    private static ArrayList<Artist> artists;
    private static ArrayList<Bands> bands;

    protected static void createListArtists(Resources res) {
        XmlPullParser parser = res.getXml(R.xml.bands);
        String nameBand = "bands";
        artists = new ArrayList<>();
        bands = new ArrayList<>();
        ArrayList<Artist> artistsBand = new ArrayList<>();
        ArrayList<String> images = new ArrayList<>();
        String nameArtist = "artist";
        try {
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (parser.getEventType()) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("NameBand")) {
                            parser.next();
                            nameBand = parser.getText();
                            break;
                        }
                        if (parser.getName().equals("NameArtist")) {
                            parser.next();
                            nameArtist = parser.getText();
                            break;
                        }
                        if (parser.getName().equals("ImageArtists")) {
                            parser.next();
                            images.add(parser.getText());
                            break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("Artist")) {
                            artistsBand.add(new Artist(nameBand, nameArtist, images.toArray(new String[0])));
                            images.removeAll(images);
                            Log.i(TAG, "createArtist:" + artistsBand.get(artistsBand.size() - 1).getName());
                            break;
                        }
                        if (parser.getName().equals("Band")) {
                            artists.addAll(artistsBand);
                            bands.add(new Bands(nameBand, artistsBand, null));
                            artistsBand.removeAll(artistsBand);
                        }
                        break;
                    default:
                        break;
                }
                parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Artist> getArtists() {
        return new ArrayList<>(artists);
    }

    public static ArrayList<Artist> getRandomArtists() {
        ArrayList<Artist> artists = getArtists();
        Collections.shuffle(artists);
        return artists;
    }

    public static ArrayList<Bands> getBands() {
        return new ArrayList<>(bands);
    }

    public static ArrayList<Bands> getRandomBands() {
        ArrayList<Bands> bands = getBands();
        Collections.shuffle(bands);
        return bands;
    }
}
