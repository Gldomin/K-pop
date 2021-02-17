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
        } else if (themeCount == 1)
            activity.setTheme(R.style.AppThemeLight);
        else activity.setTheme(R.style.AppThemeLightPurp);
    }

    public int getAlertDialogStyle() {
        if (darkMode) {
            return R.style.AlertDialog3;
        } else if (themeCount == 1)
            return R.style.AlertDialog1;
        else {
            return R.style.AlertDialog2;
        }
    }

    public int getTextColor(){
        if (darkMode) {
            return activity.getResources().getColor(R.color.colorText);
        } else if (themeCount == 1)
            return activity.getResources().getColor(R.color.colorTextLight);
        else {
            return activity.getResources().getColor(R.color.colorTextLight);
        }
    }

    public int getBackgroundResource(){
        if (darkMode) {
            return R.drawable.stylebutton_dark;
        } else if (themeCount == 1)
            return R.drawable.stylebutton;
        else {
            return R.drawable.stylebutton;
        }
    }

    public boolean isDarkMode() {
        return darkMode;
    }
}
