package com.star.k_pop.StartApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.bumptech.glide.Glide;
import com.star.k_pop.R;
import com.star.k_pop.helper.Storage;

import io.appmetrica.analytics.AppMetrica;
import io.appmetrica.analytics.AppMetricaConfig;

/**
 * Класс запускающийся при старте приложения
 */
public class ApplicationStart extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Importer.createListArtists(getResources(),this);
        // Подключение яндекс метрики
        try {
            AppMetricaConfig config = AppMetricaConfig.newConfigBuilder(getResources().getString(R.string.yandex_id_metrica)).build();
            AppMetrica.activate(getApplicationContext(), config);
            AppMetrica.enableActivityAutoTracking(this);
        } catch (Exception ignored) {

        }
        SharedPreferences sp = getSharedPreferences("appStatus", Context.MODE_PRIVATE);
        if (!sp.contains("noticeWatched") || !sp.contains("achGuessStarNormal")) {
            Log.v("TAG", "noticeWatched");
            Storage tempStorage = new Storage(this, "appStatus");
            tempStorage.saveValue("noticeWatched", false);  //Если равно False - игра запущена в первый раз

            tempStorage.saveValue("achGuessStarBeginner", false); //ачивка
            tempStorage.saveValue("achGuessStarNormal", false); //ачивка, если равно True - получена
            tempStorage.saveValue("achGuessStarExpert", false); //ачивка

            tempStorage.saveValue("achGuessBandsModeTwoBeginner", false); //ачивка
            tempStorage.saveValue("achGuessBandsModeTwoNormal", false); //ачивка
            tempStorage.saveValue("achGuessBandsModeTwoExpert", false); //ачивка

            tempStorage.saveValue("achSwipeTwoBandsBeginner", false); //ачивка
            tempStorage.saveValue("achSwipeTwoBandsNormal", false); //ачивка
            tempStorage.saveValue("achSwipeTwoBandsExpert", false); //ачивка

            tempStorage.saveValue("achTripleExpert", false); //ачивка
            tempStorage.saveValue("achRoyal", false); //ачивка
            tempStorage.saveValue("gameBuyed", false);

            Storage storage = new Storage(this, "settings");
            storage.saveValue("darkMode", false);
            storage.saveValue("themeCount", 2);
            storage.saveValue("soundMode", true);
        }
        if (!sp.contains("update2.0")){
            Storage tempStorage = new Storage(this, "appStatus");
            tempStorage.saveValue("update2.0", true);
            tempStorage.saveValue("achGuessStarBeginner", false); //ачивка
            tempStorage.saveValue("achGuessStarNormal", false); //ачивка, если равно True - получена
            tempStorage.saveValue("achGuessStarExpert", false); //ачивка

            tempStorage.saveValue("achGuessBandsModeTwoBeginner", false); //ачивка
            tempStorage.saveValue("achGuessBandsModeTwoNormal", false); //ачивка
            tempStorage.saveValue("achGuessBandsModeTwoExpert", false); //ачивка

            tempStorage.saveValue("achSwipeTwoBandsBeginner", false); //ачивка
            tempStorage.saveValue("achSwipeTwoBandsNormal", false); //ачивка
            tempStorage.saveValue("achSwipeTwoBandsExpert", false); //ачивка
            tempStorage.saveValue("achTripleExpert", false); //ачивка
            tempStorage.saveValue("achRoyal", false); //ачивка
        }
        if (!sp.contains("update2.1")){
            Storage tempStorage = new Storage(this, "appStatus");
            tempStorage.saveValue("update2.1", true);
            Glide.get(this).clearMemory();
        }
    }
}
