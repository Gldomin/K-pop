package com.star.k_pop.StartApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.star.k_pop.R;
import com.star.k_pop.helper.Storage;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

/**
 * Класс запускающийся при старте приложения
 */
public class ApplicationStart extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        // Загрузка в Importer данных о всех артистах
        Importer.createListArtists(getResources());
        // Подключение яндекс метрики
        try {
            YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(getResources().getString(R.string.yandex_id_metrica)).build();
            YandexMetrica.activate(getApplicationContext(), config);
            YandexMetrica.enableActivityAutoTracking(this);
        } catch (Exception ignored) {

        }
        // Отправление ошибок в яндекс при крахе приложения
        try {
            final Thread.UncaughtExceptionHandler mAndroidCrashHandler = Thread.getDefaultUncaughtExceptionHandler();

            final Thread.UncaughtExceptionHandler mUncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {

                @Override
                public void uncaughtException(@NonNull Thread thread, @NonNull Throwable exception) {
                    try {
                        YandexMetrica.reportError(exception.toString(), exception);
                    } finally {
                        if (null != mAndroidCrashHandler) {
                            mAndroidCrashHandler.uncaughtException(thread, exception);
                        }
                    }
                }
            };
            Thread.setDefaultUncaughtExceptionHandler(mUncaughtExceptionHandler);
        } catch (Exception ignored) {

        }

        SharedPreferences sp = getSharedPreferences("appStatus", Context.MODE_PRIVATE);
        if (!sp.contains("noticeWatched") || !sp.contains("achGuessStarNormal")) {
            Log.v("TAG", "noticeWatched");
            Storage tempStorage = new Storage(this, "appStatus");
            tempStorage.saveValue("noticeWatched", false);  //Если равно False - игра запущена в первый раз
            tempStorage.saveValue("achGuessStarNormal", false); //ачивка, если равно True - получена
            tempStorage.saveValue("achGuessStarExpert", false); //ачивка
            tempStorage.saveValue("achGuessBandsNormal", false); //ачивка
            tempStorage.saveValue("achGuessBandsExpert", false); //ачивка
            tempStorage.saveValue("achSwipeTwoBandsNormal", false); //ачивка
            tempStorage.saveValue("achSwipeTwoBandsExpert", false); //ачивка
            tempStorage.saveValue("achGuessStarReversNormal", false); //ачивка
            tempStorage.saveValue("achGuessStarReversExpert", false); //ачивка
            tempStorage.saveValue("achTripleAdept", false); //ачивка
            tempStorage.saveValue("achTripleExpert", false); //ачивка
            tempStorage.saveValue("achRoyal", false); //ачивка
            tempStorage.saveValue("gameBuyed", false);

            Storage storage = new Storage(this, "settings");
            storage.saveValue("darkMode", false);
            storage.saveValue("themeCount", 2);
        }
    }
}
