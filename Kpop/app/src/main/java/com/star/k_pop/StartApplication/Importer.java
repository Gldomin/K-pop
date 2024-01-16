package com.star.k_pop.StartApplication;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.star.k_pop.R;
import com.star.k_pop.model.Artist;
import com.star.k_pop.model.Bands;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import io.appmetrica.analytics.AppMetrica;

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

    private static final String NAME_FILE_BANDS_ACTIVE = "bandsActive";
    private static final ArrayList<Artist> artists = new ArrayList<>();   //Список артистов
    private static final ArrayList<Bands> bands = new ArrayList<>();      //Список групп
    private static final ArrayList<String> bandsActive = new ArrayList<>(); //Список групп что выбрал игрок
    private static final ArrayList<String> bandsAll = new ArrayList<>(); //Список всех групп
    private static final ArrayList<Artist> artistsNotAll = new ArrayList<>();   //Список артистов без нужных групп
    private static final ArrayList<Bands> bandsNotAll = new ArrayList<>();      //Список групп без нужных групп

    private static int countLoading = 0;

    /**
     * Создание списков артистов и групп для работы приложения
     *
     * @param res ресурсы приложения
     */
    public static void createListArtists(Resources res, Context context) {
        if (bands.size() > 0 && artists.size() > 0) {
            return;
        }
        XmlPullParser parser = res.getXml(R.xml.bands);
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
                            } else {
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
                            bandsAll.add(nameBand.get(0));
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
            LoadBandsActive(context);
        } catch (XmlPullParserException | IOException e) {
            AppMetrica.reportEvent("Importer error " + countLoading + " " + e.getMessage() + " ");
            e.printStackTrace();
            countLoading++;
            if (countLoading < 5) {
                createListArtists(res, context);
            }
        }
    }

    /**
     * Получение списка артистов
     *
     * @return список артистов
     */
    public static ArrayList<Artist> getArtists() {
        if (artistsNotAll.size() > 0) {
            return new ArrayList<>(artistsNotAll);
        }
        return new ArrayList<>(artists);
    }

    public static ArrayList<Artist> getArtistsAll() {
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
        if (bandsNotAll.size() > 0) {
            return new ArrayList<>(bandsNotAll);
        }
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

    public static ArrayList<Bands> getRandomBandsSex() {
        ArrayList<Bands> bands = getBands();
        Collections.shuffle(bands);
        ArrayList<Bands> bandsFemale = new ArrayList<>();
        ArrayList<Bands> bandsMale = new ArrayList<>();
        ArrayList<Bands> bandsMix = new ArrayList<>();
        for (Bands b : bands) {
            if (b.getSex() == 1) {
                bandsMale.add(b);
            } else if (b.getSex() == 2) {
                bandsFemale.add(b);
            } else {
                bandsMix.add(b);
            }
        }
        ArrayList<Bands> bandsReturn = new ArrayList<>();
        if (new Random().nextBoolean()) {
            bandsReturn.addAll(bandsFemale);
            bandsReturn.addAll(bandsMix);
            bandsReturn.addAll(bandsMale);
        } else {
            bandsReturn.addAll(bandsMale);
            bandsReturn.addAll(bandsMix);
            bandsReturn.addAll(bandsFemale);
        }
        return bandsReturn;
    }

    private static void UpdateBandsActive() {
        artistsNotAll.clear();
        bandsNotAll.clear();
        for (int i = 0; i < artists.size(); i++) {
            if (!bandsActive.contains(artists.get(i).getGroup())) {
                artistsNotAll.add(artists.get(i));
            }
        }
        for (int i = 0; i < bands.size(); i++) {
            if (!bandsActive.contains(bands.get(i).getName())) {
                bandsNotAll.add(bands.get(i));
            }
        }
    }

    private static void LoadBandsActive(Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME_FILE_BANDS_ACTIVE, Context.MODE_PRIVATE);
        if (sp.contains("nameBandsActiveSave")) {
            bandsActive.clear();
            Set<String> names = sp.getStringSet("nameBandsActiveSave", new HashSet<>());
            String[] name = new String[names.size()];
            name = names.toArray(name);
            if (name.length <= bands.size() - 13) {
                Collections.addAll(bandsActive, name);
            }
        }
        UpdateBandsActive();
    }

    public static void SaveBandsActive(Context context, ArrayList<String> bandsActiveNew) {
        bandsActive.clear();
        bandsActive.addAll(bandsActiveNew);
        SharedPreferences sp = context.getSharedPreferences(NAME_FILE_BANDS_ACTIVE, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putStringSet("nameBandsActiveSave", new HashSet<>(bandsActive));
        e.apply();
        UpdateBandsActive();
    }

    public static String getNameActiveText(int i) {
        return bandsAll.get(i);
    }

    public static boolean isGetNameActive(int i) {
        for (String bands : bandsActive) {
            if (bands.equals(bandsAll.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static int getSizeNameActive() {
        return bandsAll.size();
    }
}
