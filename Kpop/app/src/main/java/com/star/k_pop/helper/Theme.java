package com.star.k_pop.helper;

import android.app.Activity;

import com.star.k_pop.R;

public class Theme {

    Activity activity;
    boolean darkMode;

    public Theme(Activity activity) {
        this.activity = activity;
    }

    public void setTheme() {
        Storage storage2 = new Storage(activity, "settings");
        darkMode = storage2.getBoolean("darkMode");
        if (darkMode) {
            activity.setTheme(R.style.AppTheme2);
        } else {
            activity.setTheme(R.style.AppThemeLight);
        }
    }

    public boolean isDarkMode() {
        return darkMode;
    }
}
