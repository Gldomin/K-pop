package com.exam.k_pop.StartApplication;

import android.app.Application;

public class ApplicationStart extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Importer.createListArtists(getResources());
    }
}
