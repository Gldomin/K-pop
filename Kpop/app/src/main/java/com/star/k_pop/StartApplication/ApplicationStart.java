package com.star.k_pop.StartApplication;

import android.app.Application;

import androidx.annotation.NonNull;

import com.star.k_pop.R;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class ApplicationStart extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Importer.createListArtists(getResources());

        try {
            YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(getResources().getString(R.string.id_yandex_metrica)).build();
            YandexMetrica.activate(getApplicationContext(), config);
            YandexMetrica.enableActivityAutoTracking(this);
        } catch (Exception ignored) {

        }
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
    }
}