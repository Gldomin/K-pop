package com.star.k_pop.helper;

import android.app.Activity;

import com.star.k_pop.R;

public class Theme {

    Activity activity;
    boolean darkMode;
    int themeCount;

    public Theme(Activity activity) {
        this.activity = activity;
    }

    public void setTheme() {
        Storage storage2 = new Storage(activity, "settings");
        darkMode = storage2.getBoolean("darkMode");
        themeCount = storage2.getInt("themeCount");
        if (darkMode) {
            activity.setTheme(R.style.AppTheme2);
        } else if(themeCount==1)
            activity.setTheme(R.style.AppThemeLight);
        else activity.setTheme(R.style.AppThemeLightPurp);
    }

    public boolean isDarkMode() {
        return darkMode;
    }
}
