package com.star.k_pop.helper;

public class OptionsSet { //объект хранения настроек приложения
    public Boolean hardMode; //режим хардкора
    public Boolean hintMode; //режим подсказок
    public Boolean darkMode; //темный режим
    public Boolean soundMode; //временное название
    public Boolean option5; //временное название
    public int themeCount;


    public OptionsSet(Boolean hint, Boolean hard, Boolean sound) {
        hintMode = hint;
        hardMode = hard;
        darkMode = false;
        soundMode = sound;
        option5 = false;
    }

}

