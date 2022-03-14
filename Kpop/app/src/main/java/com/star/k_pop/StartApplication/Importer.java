package com.star.k_pop.StartApplication;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.widget.Toast;

import com.star.k_pop.R;
import com.star.k_pop.model.Artist;
import com.star.k_pop.model.Bands;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    private static ArrayList<Artist> artists;   //Список артистов
    private static ArrayList<Bands> bands;      //Список групп
    private static ArrayList<String> bandsActive; //Список групп что выбрал игрок
    private static ArrayList<String> bandsAll; //Список всех групп
    private static ArrayList<Artist> artistsNotAll;   //Список артистов без нужных групп
    private static ArrayList<Bands> bandsNotAll;      //Список групп без нужных групп

    /**
     * Создание списков артистов и групп для работы приложения
     *
     * @param res ресурсы приложения
     */
    public static void createListArtists(Resources res, Context context) {
        XmlPullParser parser = res.getXml(R.xml.bands);
        artists = new ArrayList<>();
        bands = new ArrayList<>();
        artistsNotAll = new ArrayList<>();
        bandsNotAll = new ArrayList<>();
        bandsAll = new ArrayList<>();
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
            bandsActive = new ArrayList<>(bandsAll);
            LoadBandsActive(context);
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
        return new ArrayList<>(artistsNotAll);
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
        return new ArrayList<>(bandsNotAll);
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

    private static void UpdateBandsActive(){
        artistsNotAll = new ArrayList<>();
        bandsNotAll = new ArrayList<>();
        for(int i = 0; i < artists.size(); i++){
            if (!bandsActive.contains(artists.get(i).getGroup())){
                artistsNotAll.add(artists.get(i));
            }
        }
        for(int i = 0; i < bands.size(); i++){
            if (!bandsActive.contains(bands.get(i).getName())){
                bandsNotAll.add(bands.get(i));
            }
        }
    }

    private static void LoadBandsActive(Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME_FILE_BANDS_ACTIVE, Context.MODE_PRIVATE);
        if (sp.contains("nameBandsActiveSave")) {
            bandsActive = new ArrayList<>();
            Set<String> names = sp.getStringSet("nameBandsActiveSave", new HashSet<String>());
            String[] name = new String[names.size()];
            name = names.toArray(name);
            Collections.addAll(bandsActive, name);
        }
        UpdateBandsActive();
    }

    public static void SaveBandsActive(Context context, ArrayList<String> bandsActiveNew){
        bandsActive = new ArrayList<>(bandsActiveNew);
        SharedPreferences sp = context.getSharedPreferences(NAME_FILE_BANDS_ACTIVE, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putStringSet("nameBandsActiveSave", new HashSet<>(bandsActive));
        e.apply();
        UpdateBandsActive();
    }

    public static String getNameActiveText(int i){
        return bandsAll.get(i);
    }

    public static boolean isGetNameActive(int i){
        for(String bands : bandsActive){
            if (bands.equals(bandsAll.get(i))){
                return false;
            }
        }
        return true;
    }

    public static int getSizeNameActive(){
        return bandsAll.size();
    }
}
