package com.star.k_pop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.star.k_pop.gallery.activity.Gallery;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp; SharedPreferences spp;
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void appStatusGeneration(){ //генерация первоначального статуса
        Storage tempStorage = new Storage(this);
        String nameOfStorage = "appStatus"; //хранилище всяких состояний приложения
        sp = getSharedPreferences(nameOfStorage,Context.MODE_PRIVATE);
        if (!sp.contains("noticeWatched")) {
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

    protected void readStatus() { //считывает статус/атрибуты приложения //TODO удалить
        Storage storage = new Storage(this);
        String nameOfStorage = "appStatus";
        sp = getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        final Switch noticeWatched = findViewById(R.id.noticeWatched);
        final Switch achGuessStarNormalText = findViewById(R.id.achGuessStarNormalText);
        final Switch achGuessStarExpertText = findViewById(R.id.achGuessStarExpertText);
        final Switch achGuessBandsNormalText = findViewById(R.id.achGuessBandsNormalText);
        final Switch achGuessBandsExpertText = findViewById(R.id.achGuessBandsExpertText);
        final Switch achSwipeTwoBandsNormalText = findViewById(R.id.achSwipeTwoBandsNormalText);
        final Switch achSwipeTwoBandsExpertText = findViewById(R.id.achSwipeTwoBandsExpertText);
        final Switch achGuessStarReversNormalText = findViewById(R.id.achGuessStarReversNormalText);
        final Switch achGuessStarReversExpertText = findViewById(R.id.achGuessStarReversExpertText);
        final Switch achSecretGameModeText = findViewById(R.id.achSecretGameModeText);
        final Switch achAdsFreeText = findViewById(R.id.achAdsFreeText);
        final Switch achRoyalText = findViewById(R.id.achRoyalText);
        final Switch hintModeSwitch = findViewById(R.id.optionSwitch1);


        noticeWatched.setChecked(storage.getBoolean(nameOfStorage, "noticeWatched")); //TODO по-хорошему надо переписать Storage, что бы не писать лишний раз nameOfStorage (можно указывать в конструкторе)
        achGuessStarNormalText.setChecked(storage.getBoolean(nameOfStorage, "achGuessStarNormalText"));
        achGuessStarExpertText.setChecked(storage.getBoolean(nameOfStorage, "achGuessStarExpertText"));
        achGuessBandsNormalText.setChecked(storage.getBoolean(nameOfStorage, "achGuessBandsNormalText"));
        achGuessBandsExpertText.setChecked(storage.getBoolean(nameOfStorage, "achGuessBandsExpertText"));
        achSwipeTwoBandsNormalText.setChecked(storage.getBoolean(nameOfStorage, "achSwipeTwoBandsNormalText"));
        achSwipeTwoBandsExpertText.setChecked(storage.getBoolean(nameOfStorage, "achSwipeTwoBandsExpertText"));
        achGuessStarReversNormalText.setChecked(storage.getBoolean(nameOfStorage, "achGuessStarReversNormalText"));
        achGuessStarReversExpertText.setChecked(storage.getBoolean(nameOfStorage, "achGuessStarReversExpertText"));
        achSecretGameModeText.setChecked(storage.getBoolean(nameOfStorage, "achSecretGameModeText"));
        achAdsFreeText.setChecked(storage.getBoolean(nameOfStorage, "achAdsFreeText"));
        achRoyalText.setChecked(storage.getBoolean(nameOfStorage, "achRoyalText"));
    }
    protected void saveStatus() { //сохраняет значения свичей меняя атрибуты //TODO удалить
        Storage storage = new Storage(this);
        String nameOfStorage = "appStatus";
        sp = getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        final Switch noticeWatched = findViewById(R.id.noticeWatched);
        final Switch achGuessStarNormalText = findViewById(R.id.achGuessStarNormalText);
        final Switch achGuessStarExpertText = findViewById(R.id.achGuessStarExpertText);
        final Switch achGuessBandsNormalText = findViewById(R.id.achGuessBandsNormalText);
        final Switch achGuessBandsExpertText = findViewById(R.id.achGuessBandsExpertText);
        final Switch achSwipeTwoBandsNormalText = findViewById(R.id.achSwipeTwoBandsNormalText);
        final Switch achSwipeTwoBandsExpertText = findViewById(R.id.achSwipeTwoBandsExpertText);
        final Switch achGuessStarReversNormalText = findViewById(R.id.achGuessStarReversNormalText);
        final Switch achGuessStarReversExpertText = findViewById(R.id.achGuessStarReversExpertText);
        final Switch achSecretGameModeText = findViewById(R.id.achSecretGameModeText);
        final Switch achAdsFreeText = findViewById(R.id.achAdsFreeText);
        final Switch achRoyalText = findViewById(R.id.achRoyalText);
        final Switch hintModeSwitch = findViewById(R.id.optionSwitch1);


        storage.saveValue(nameOfStorage,"noticeWatched",noticeWatched.isChecked());
        storage.saveValue(nameOfStorage,"achGuessStarNormalText",achGuessStarNormalText.isChecked());
        storage.saveValue(nameOfStorage,"achGuessStarExpertText",achGuessStarExpertText.isChecked());
        storage.saveValue(nameOfStorage,"achGuessBandsNormalText",achGuessBandsNormalText.isChecked());
        storage.saveValue(nameOfStorage,"achGuessBandsExpertText",achGuessBandsExpertText.isChecked());
        storage.saveValue(nameOfStorage,"achSwipeTwoBandsNormalText",achSwipeTwoBandsNormalText.isChecked());
        storage.saveValue(nameOfStorage,"achSwipeTwoBandsExpertText",achSwipeTwoBandsExpertText.isChecked());
        storage.saveValue(nameOfStorage,"achGuessStarReversNormalText",achGuessStarReversNormalText.isChecked());
        storage.saveValue(nameOfStorage,"achGuessStarReversExpertText",achGuessStarReversExpertText.isChecked());
        storage.saveValue(nameOfStorage,"achSecretGameModeText",achSecretGameModeText.isChecked());
        storage.saveValue(nameOfStorage,"achAdsFreeText",achAdsFreeText.isChecked());
        storage.saveValue(nameOfStorage,"achRoyalText",achRoyalText.isChecked());


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


                        //+++FirstLaunchHelp+++\\ // блок для первичного задания статусов (ачивок и прочего), а так же отображения приветсвия новых пользователей
        Storage storage = new Storage(this);
        String nameOfStorage="appStatus";
        sp = getSharedPreferences(nameOfStorage,Context.MODE_PRIVATE);
        appStatusGeneration(); //создание статусов приложения, не создаст, если статусы уже существуют
        if (sp.contains("noticeWatched")) {
            if (storage.getBoolean(nameOfStorage, "noticeWatched") == false) {
                storage.saveValue(nameOfStorage, "noticeWatched", true);

                Intent image = new Intent();
                image.setClass(MainActivity.this, BasicNotice.class);
                startActivity(image);
            }

        }

///////////////////////////////////СВИЧИ ДЛЯ ПРОВЕРКИ СТУТУСОВ - начало////////////////// //TODO удалить блок вместе с ScrollView из активити и readStatus() с saveStatus()
        readStatus();

        final Button saveStatus = findViewById(R.id.saveStatus);
        saveStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveStatus();
                readStatus();
            }
        });


         //   tempSettingsSet.hintMode = storage.getBoolean(nameOfStorage, "hintMode"); //считывание настроек из Хранилища

//////////////////////////////////СВИЧИ ДЛЯ ПРОВЕРКИ СТУТУСОВ - конец//////////////////


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
