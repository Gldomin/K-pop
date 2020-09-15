package com.exam.k_pop;

public class OptionsSet { //объект хранения настроек приложения
    public Boolean hardMode; //режим хардкора
    public Boolean hintMode; //режим подсказок
    public Boolean option3; //временное название
    public Boolean option4; //временное название
    public Boolean option5; //временное название

    OptionsSet(Boolean hint, Boolean hard) {
        hintMode = hint;
        hardMode = hard;
        option3 = false; //TODO нужны ли? заменить
        option4 = false;
        option5 = false;
    }

    //OVERLOAD
    OptionsSet() {
        hintMode = false;
        hardMode = false;
        option3 = false;
        option4 = false;
        option5 = false;
    }


}

