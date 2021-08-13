package com.star.k_pop.StartApplication;


import android.content.res.Resources;

import com.star.k_pop.R;
import com.star.k_pop.model.Artist;
import com.star.k_pop.model.Bands;

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

/**
 * Класс ипортер из xml файла всех данных приложения
 */
public class Importer {

    private static ArrayList<Artist> artists;   //Список артистов
    private static ArrayList<Bands> bands;      //Список групп

    /**
     * Создание списков артистов и групп для работы приложения
     *
     * @param res ресурсы приложения
     */
    protected static void createListArtists(Resources res) {
        XmlPullParser parser = res.getXml(R.xml.bands);
        artists = new ArrayList<>();
        bands = new ArrayList<>();
        ArrayList<Artist> artistsBand = new ArrayList<>();
        ArrayList<String> images = new ArrayList<>();
        ArrayList<String> nameBand = new ArrayList<>();
        ArrayList<String> imageBand = new ArrayList<>();
        Bands.Sex sexBand = Bands.Sex.MIXED;
        String nameArtist = "artist";
        boolean sex = false;
        try {
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (parser.getEventType()) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("Image")) {
                            parser.next();
                            imageBand.add(parser.getText());
                            break;
                        }
                        if (parser.getName().equals("NameBand")) {
                            parser.next();
                            nameBand.add(parser.getText());
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
                        if (parser.getName().equals("Sex")) {
                            parser.next();
                            sex = parser.getText().equals("Female");
                            break;
                        }
                        if (parser.getName().equals("SexBand")) {
                            parser.next();
                            String sexBandText = parser.getText();
                            if (sexBandText.equals("Male")) {
                                sexBand = Bands.Sex.MALE;
                            } else if (sexBandText.equals("Female")) {
                                sexBand = Bands.Sex.FEMALE;
                            } else{
                                sexBand = Bands.Sex.MIXED;
                            }
                            break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("Artist")) {
                            artistsBand.add(new Artist(nameBand.toArray(new String[0]), nameArtist, images.toArray(new String[0]), sex));
                            images.clear();
                            sex = false;
                            break;
                        }
                        if (parser.getName().equals("Band")) {
                            artists.addAll(artistsBand);
                            bands.add(new Bands(nameBand.toArray(new String[0]), artistsBand, imageBand.toArray(new String[0]), sexBand));
                            nameBand.clear();
                            artistsBand.clear();
                            imageBand.clear();
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

    /**
     * Получение списка артистов
     *
     * @return список артистов
     */
    public static ArrayList<Artist> getArtists() {
        return new ArrayList<>(artists);
    }

    /**
     * Получение списка артистов
     *
     * @return перемешанный список артистов
     */
    public static ArrayList<Artist> getRandomArtists() {
        ArrayList<Artist> artists = getArtists();
        Collections.shuffle(artists);
        return artists;
    }

    /**
     * Получение списка групп
     *
     * @return список групп
     */
    public static ArrayList<Bands> getBands() {
        return new ArrayList<>(bands);
    }

    /**
     * Получение списка групп
     *
     * @return перемешанный список групп
     */
    public static ArrayList<Bands> getRandomBands() {
        ArrayList<Bands> bands = getBands();
        Collections.shuffle(bands);
        return bands;
    }
}
