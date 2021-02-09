package com.star.k_pop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.star.k_pop.gallery.activity.Gallery;

import static android.widget.Toast.makeText;

public class    MainActivity extends AppCompatActivity {
    final int REQUEST_CODE=1;

    SharedPreferences sp; SharedPreferences spp;
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    protected void appStatusGeneration(){ //генерация первоначального статуса
        Storage tempStorage = new Storage(this);
        String nameOfStorage = "appStatus"; //хранилище всяких состояний приложения
        sp = getSharedPreferences(nameOfStorage,Context.MODE_PRIVATE);
        if (!sp.contains("noticeWatched")||!sp.contains("achGuessStarNormal")) {
            tempStorage.saveValue(nameOfStorage,"noticeWatched", false);  //Если равно False - игра запущена в первый раз

            tempStorage.saveValue(nameOfStorage,"achGuessStarNormal", false); //ачивка, если равно True - получена
            tempStorage.saveValue(nameOfStorage,"achGuessStarExpert", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achGuessBandsNormal", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achGuessBandsExpert", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achSwipeTwoBandsNormal", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achSwipeTwoBandsExpert", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achGuessStarReversNormal", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achGuessStarReversExpert", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achSecretGameMode", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achAdsFree", false); //ачивка
            tempStorage.saveValue(nameOfStorage,"achRoyal", false); //ачивка

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
        achGuessStarNormalText.setChecked(storage.getBoolean(nameOfStorage, "achGuessStarNormal"));
        achGuessStarExpertText.setChecked(storage.getBoolean(nameOfStorage, "achGuessStarExpert"));
        achGuessBandsNormalText.setChecked(storage.getBoolean(nameOfStorage, "achGuessBandsNormal"));
        achGuessBandsExpertText.setChecked(storage.getBoolean(nameOfStorage, "achGuessBandsExpert"));
        achSwipeTwoBandsNormalText.setChecked(storage.getBoolean(nameOfStorage, "achSwipeTwoBandsNormal"));
        achSwipeTwoBandsExpertText.setChecked(storage.getBoolean(nameOfStorage, "achSwipeTwoBandsExpert"));
        achGuessStarReversNormalText.setChecked(storage.getBoolean(nameOfStorage, "achGuessStarReversNormal"));
        achGuessStarReversExpertText.setChecked(storage.getBoolean(nameOfStorage, "achGuessStarReversExpert"));
        achSecretGameModeText.setChecked(storage.getBoolean(nameOfStorage, "achSecretGameMode"));
        achAdsFreeText.setChecked(storage.getBoolean(nameOfStorage, "achAdsFree"));
        achRoyalText.setChecked(storage.getBoolean(nameOfStorage, "achRoyal"));
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
        storage.saveValue(nameOfStorage,"achGuessStarNormal",achGuessStarNormalText.isChecked());
        storage.saveValue(nameOfStorage,"achGuessStarExpert",achGuessStarExpertText.isChecked());
        storage.saveValue(nameOfStorage,"achGuessBandsNormal",achGuessBandsNormalText.isChecked());
        storage.saveValue(nameOfStorage,"achGuessBandsExpert",achGuessBandsExpertText.isChecked());
        storage.saveValue(nameOfStorage,"achSwipeTwoBandsNormal",achSwipeTwoBandsNormalText.isChecked());
        storage.saveValue(nameOfStorage,"achSwipeTwoBandsExpert",achSwipeTwoBandsExpertText.isChecked());
        storage.saveValue(nameOfStorage,"achGuessStarReversNormal",achGuessStarReversNormalText.isChecked());
        storage.saveValue(nameOfStorage,"achGuessStarReversExpert",achGuessStarReversExpertText.isChecked());
        storage.saveValue(nameOfStorage,"achSecretGameMode",achSecretGameModeText.isChecked());
        storage.saveValue(nameOfStorage,"achAdsFree",achAdsFreeText.isChecked());
        storage.saveValue(nameOfStorage,"achRoyal",achRoyalText.isChecked());


    }

    OptionsSet tempSettingsSet = new OptionsSet(false, false); //переменная для считывания состояния свиича на darkmod
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Storage storage = new Storage(this);

        Storage tempStorage = new Storage(this);
        String nameOfStorage3 = "settings";
        String nameOfValue = "darkModeCounter";


        String nameOfStorage2 = "settings";
        Storage storage2 = new Storage(this);
        tempSettingsSet.darkMode = storage2.getBoolean(nameOfStorage2, "darkMode"); //считываем состояние
        //теперь выбираем тему в зависимости от положения свича
        if (tempSettingsSet.darkMode==true) setTheme(R.style.AppTheme2);
        else setTheme(R.style.AppThemeLight);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


                        //+++FirstLaunchHelp+++\\ // блок для первичного задания статусов (ачивок и прочего), а так же отображения приветсвия новых пользователей
        //Storage storage = new Storage(this);
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
        if (tempSettingsSet.darkMode==true) guessStarButton.setBackgroundResource(R.drawable.stylebutton_dark);
        else guessStarButton.setBackgroundResource(R.drawable.stylebutton);

        guessStarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, GuessStar.class);
                startActivity(image);
            }
        });

        Button guessStarReverseButton = findViewById(R.id.guessStarReverseButton);
        guessStarReverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, GuessStarReverse.class);
                startActivity(image);
            }
        });

        Button guessBandButton = findViewById(R.id.guessGroupButton);
        if (tempSettingsSet.darkMode==true) guessBandButton.setBackgroundResource(R.drawable.stylebutton_dark);
        else guessBandButton.setBackgroundResource(R.drawable.stylebutton);

        guessBandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, GuessBands.class);
                startActivity(image);
            }
        });
        Button buttonLibrary = findViewById(R.id.galleryButton);
        if (tempSettingsSet.darkMode==true) buttonLibrary.setBackgroundResource(R.drawable.stylebutton_dark);
        else buttonLibrary.setBackgroundResource(R.drawable.stylebutton);

        buttonLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, Gallery.class);
                startActivity(image);
            }
        });
        Button settingsButton = findViewById(R.id.settingsButton);
        if (tempSettingsSet.darkMode==true) settingsButton.setBackgroundResource(R.drawable.stylebutton_dark);
        else settingsButton.setBackgroundResource(R.drawable.stylebutton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           /*     new CountDownTimer(10000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        recreate();
                    }
                }.start();*/
                Intent image = new Intent();
                image.setClass(MainActivity.this, Settings.class);
                startActivityForResult(image,REQUEST_CODE);
            }



        });


        Button tinderButton = findViewById(R.id.chooseTinderButton);
        if (tempSettingsSet.darkMode==true) tinderButton.setBackgroundResource(R.drawable.stylebutton_dark);
        else tinderButton.setBackgroundResource(R.drawable.stylebutton);



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
        if (tempSettingsSet.darkMode==true) about.setBackgroundResource(R.drawable.stylebutton_dark);
        else about.setBackgroundResource(R.drawable.stylebutton);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, BasicNotice.class);
                startActivity(image);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            recreate();
        }

    }
     /*public class Restart {
        public void reStart() {
            recreate();
        }
     }*/

}
