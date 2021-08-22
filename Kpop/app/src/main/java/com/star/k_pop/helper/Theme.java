package com.star.k_pop.helper;

import android.app.Activity;

import com.star.k_pop.R;

public class Theme {

    Activity activity;
    boolean darkMode;
    int themeCount;

    public Theme(Activity activity) {
        this.activity = activity;
        Storage storage = new Storage(activity, "settings");
        darkMode = storage.getBoolean("darkMode");
        themeCount = storage.getInt("themeCount");
    }

    public void setTheme() { //первая тема для меню
        if (darkMode) {
            activity.setTheme(R.style.AppTheme2);
        } else
            activity.setTheme(R.style.AppThemeLightRabbit);

    }

    public void setThemeSecond() {  //вторая тема для игровых активити
        if (darkMode) {
            activity.setTheme(R.style.AppTheme2);
        } else
            activity.setTheme(R.style.AppThemeLightRabbit2);

    }

    public int getThemeBackground() {
        if (darkMode) {
            return R.drawable.light_background;
        } else
            return R.drawable.main_background2;

    }

    public int getThemeBackground2() {
        /*if (darkMode) {
            return R.drawable.main_background2;
        } else*/
        if (themeCount == 1) {
            return R.drawable.light_background;
        } else
            return R.drawable.main_background_hamster;

    }

    public int getAlertDialogStyle() {
        if (darkMode) {
            return R.style.DarkAlertDialog;
        } else
            return R.style.StandartAlertDialog;
    }

    public int getTextColor() {
        if (darkMode) {
            return activity.getResources().getColor(R.color.whiteColor);
        } else
            return activity.getResources().getColor(R.color.blackColor);

    }

    public int getButtonTextColor() {
        if (darkMode) {
            return activity.getResources().getColor(R.color.blackColor);
        } else
            return activity.getResources().getColor(R.color.blackColor);
    }

    public int getBackgroundResource() {
            if (darkMode) {
                return R.drawable.stylebutton_purple;
            } else {
                return R.drawable.stylebutton_yellow;
            }

    }

    public int getBackgroundButton() {

            if (darkMode) {
                return R.drawable.stylebutton_pink;
            } else return R.drawable.stylebutton_yellow;

    }

    public int getBackgroundButton2() {
            if (darkMode) {
                return R.drawable.stylebutton_pink;
            } else
                return R.drawable.light_background;

    }


    public boolean isDarkMode() {
        return darkMode;
    }
}
