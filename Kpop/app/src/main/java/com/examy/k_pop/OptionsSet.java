package com.examy.k_pop;

public class OptionsSet { //объект хранения настроек приложения
    public Boolean hardMode; //режим хардкора
    public Boolean hintMode; //режим подсказок
    public Boolean darkMode; //темный режим
    public Boolean option4; //временное название
    public Boolean option5; //временное название

    OptionsSet(Boolean hint, Boolean hard) {
        hintMode = hint;
        hardMode = hard;
        darkMode = false; //TODO нужны ли? заменить
        option4 = false;
        option5 = false;
    }

    //OVERLOAD
    OptionsSet() {
        hintMode = false;
        hardMode = false;
        darkMode = false;
        option4 = false;
        option5 = false;
    }


}

