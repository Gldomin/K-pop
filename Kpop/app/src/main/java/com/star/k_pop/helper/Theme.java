package com.star.k_pop.helper;

import android.app.Activity;

import com.star.k_pop.R;

public class Theme {

    Activity activity;
    boolean darkMode;
    boolean lightCat, lightHamster, lightRabbit = false;
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
        } else if(themeCount==1){
            activity.setTheme(R.style.AppThemeLightRabbit);
            lightRabbit=true;
        }
        else if(themeCount==2){ activity.setTheme(R.style.AppThemeLightHamster);
            lightHamster=true;
        }
        else if(themeCount==3){ activity.setTheme(R.style.AppThemeLightCat);
            lightCat=true;
        }
    }

    public boolean isDarkMode() {
        return darkMode;
    }
    public boolean isLightCat(){return lightCat;}
    public boolean isLightHamster(){return lightHamster;}
    public boolean isLightRabbit(){return lightRabbit;}
}
