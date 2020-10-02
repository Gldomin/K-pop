package com.star.k_pop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.star.k_pop.gallery.activity.Gallery;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void appStatusGeneration(){ //генерация первоначального статуса
        Storage tempStorage = new Storage(this);
        String nameOfStorage = "appStatus"; //хранилище всяких состояний приложения
        sp = getSharedPreferences(nameOfStorage,Context.MODE_PRIVATE);
        if (!sp.contains("firstTime")) {
            tempStorage.saveValue(nameOfStorage,"noticeWatched", false);  //Если равно False - игра запущена в первый раз

            tempStorage.saveValue(nameOfStorage,"achGuessStarNormalText", false); //ачивка, если равно True - получена
            tempStorage.saveValue(nameOfStorage,"achGuessStarExpertText", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achGuessBandsNormalText", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achGuessBandsExpertText", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achSwipeTwoBandsNormalText", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achSwipeTwoBandsExpertText", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achGuessStarReversNormalText", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achGuessStarReversExpertText", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achSecretGameModeText", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achAdsFreeText", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achRoyalText", false); //ачивка

            tempStorage.saveValue(nameOfStorage,"adActive", true); //включена ли реклама //можно было бы проверять по ачивке, но так интуитивнее

            tempStorage.saveValue(nameOfStorage,"gameBuyed", false); //TODO надо сделать возможность купить игру, отключая рекламу. за это может быть ачивка

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                        //+++FirstLaunchHelp+++\\
        Storage storage = new Storage(this);
        String nameOfStorage="appStatus";
        sp = getSharedPreferences(nameOfStorage,Context.MODE_PRIVATE);
        appStatusGeneration(); //создание статусов приложения, не создаст, если статусы уже существуют
        if (sp.contains("firstTime")) {
            if (storage.getBoolean(nameOfStorage, "fristTime") == false) {
                storage.saveValue(nameOfStorage, "firstTime", true);

                Intent image = new Intent();
                image.setClass(MainActivity.this, BasicNotice.class);
                startActivity(image);
            }

        }

      /*  sp = getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sp.contains("hardMode") && sp.contains("hintMode") && sp.contains("option5")) {
            Storage storage = new Storage(this);
            String nameOfStorage = "settings";
            tempSettingsSet.hintMode = storage.getBoolean(nameOfStorage, "hintMode"); //считывание настроек из Хранилища
            tempSettingsSet.hardMode = storage.getBoolean(nameOfStorage, "hardMode");
            tempSettingsSet.darkMode = storage.getBoolean(nameOfStorage, "darkMode");
            tempSettingsSet.option4 = storage.getBoolean(nameOfStorage, "option4");
            tempSettingsSet.option5 = storage.getBoolean(nameOfStorage, "option5");


            hintModeSwitch.setChecked(tempSettingsSet.hintMode); //установка значение свитча
            hardModeSwitch.setChecked(tempSettingsSet.hardMode);
            darkThemeSwitch.setChecked(tempSettingsSet.darkMode);
            optionSwitch4.setChecked(tempSettingsSet.option4);
            optionSwitch5.setChecked(tempSettingsSet.option5);


        } else {

            saveSettings();

        }*/

         //   tempSettingsSet.hintMode = storage.getBoolean(nameOfStorage, "hintMode"); //считывание настроек из Хранилища

                            // +++++++++++++++\\

        setContentView(R.layout.activity_main);
        Button guessStarButton = findViewById(R.id.guessStarButton);
        guessStarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, GuessStar.class);
                startActivity(image);
            }
        });

        Button guessBandButton = findViewById(R.id.guessGroupButton);
        guessBandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, GuessBands.class);
                startActivity(image);
            }
        });
        Button buttonLibrary = findViewById(R.id.galleryButton);
        buttonLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, Gallery.class);
                startActivity(image);
            }
        });
        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, Settings.class);
                startActivity(image);
            }
        });

        Button tinderButton = findViewById(R.id.chooseTinderButton);
        tinderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, TwoBandsTinder.class);
                startActivity(image);
            }
        });

        ImageView achievement = findViewById(R.id.achievementButton);
        achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, Achievements.class);
                startActivity(image);
            }
        });
        Button about = findViewById(R.id.abautButton);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, BasicNotice.class);
                startActivity(image);
            }
        });


    }

}
