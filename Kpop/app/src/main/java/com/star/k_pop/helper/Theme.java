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
        } else {
            activity.setTheme(R.style.AppThemeLight);
        }

    }

    public void setThemeSecond() {  //вторая тема для игровых активити
        if (darkMode) {
            activity.setTheme(R.style.AppTheme2);
        } else {
            activity.setTheme(R.style.AppThemeLight);
        }

    }

    public int getAlertDialogStyle() {
        if (darkMode) {
            return R.style.DarkAlertDialog;
        } else {
            return R.style.StandartAlertDialog;
        }
    }

    public int getTextColor() {
        if (darkMode) {
            return activity.getResources().getColor(R.color.whiteColor);
        } else {
            return activity.getResources().getColor(R.color.blackColor);
        }

    }

    public int getColorMissTakeCurrent(){
        if (darkMode) {
            return R.color.missTakeCurrentDarkColor;
        } else {
            return R.color.missTakeCurrentLightColor;
        }
    }

    public int getColorMissTakeError(){
        if (darkMode) {
            return R.color.missTakeErrorDarkColor;
        } else {
            return R.color.missTakeErrorLightColor;
        }
    }

    public int getColorLighter() {
        if (darkMode) {
            return activity.getResources().getColor(R.color.lighterPurpleButtonColor);
        } else {
            return activity.getResources().getColor(R.color.lighterYellowButtonColor);
        }
    }

    public int getButtonTextColor() {
        return activity.getResources().getColor(R.color.blackColor);
    }

    public int getBackgroundButton() {

        if (darkMode) {
            return R.drawable.stylebutton_pink;
        } else {
            return R.drawable.stylebutton_yellow;
        }

    }

    public int getBackgroundButtonDisable() {
        if (darkMode) {
            return R.drawable.stylebutton_purple;
        } else {
            return R.drawable.stylebutton_yellow_dark;
        }

    }

    public int getBackgroundCart(){
        if (darkMode) {
            return R.drawable.gradient_background;
        } else {
            return R.drawable.gradient_background_light;
        }
    }

    public boolean isDarkMode() {
        return darkMode;
    }
}
