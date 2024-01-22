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

    public int getStyleRatingBackground() {
        if (darkMode) {
            return R.drawable.style_rating_dark;
        } else {
            return R.drawable.style_rating_light;
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

    public int getHintDrawable(){
        if (darkMode) {
            return R.drawable.hint2;
        } else {
            return R.drawable.hint;
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

    public int getColorRatingText(){
        if (darkMode) {
            return activity.getResources().getColor(R.color.colorRatingTextDark);
        } else {
            return activity.getResources().getColor(R.color.colorRatingTextLight);
        }
    }

    public int getColorRatingStar(){
        if (darkMode) {
            return activity.getResources().getColor(R.color.colorRatingStarDark);
        } else {
            return activity.getResources().getColor(R.color.colorRatingStarLight);
        }
    }

    public int getColorRatingButtonText(){
        if (darkMode) {
            return activity.getResources().getColor(R.color.colorRatingButtonTextDark);
        } else {
            return activity.getResources().getColor(R.color.colorRatingButtonTextLight);
        }
    }

    public boolean isDarkMode() {
        return darkMode;
    }
}
