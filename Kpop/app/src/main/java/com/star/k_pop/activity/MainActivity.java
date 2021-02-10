package com.star.k_pop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.star.k_pop.R;
import com.star.k_pop.gallery.activity.Gallery;
import com.star.k_pop.helper.OptionsSet;
import com.star.k_pop.helper.Storage;

public class MainActivity extends AppCompatActivity {
    final int REQUEST_CODE = 1;

    SharedPreferences sp;
    SharedPreferences spp;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    protected void appStatusGeneration() { //генерация первоначального статуса
        Storage tempStorage = new Storage(this, "appStatus"); //хранилище всяких состояний приложения
        sp = getSharedPreferences("appStatus", Context.MODE_PRIVATE);
        if (!sp.contains("noticeWatched") || !sp.contains("achGuessStarNormal")) {
            tempStorage.saveValue("noticeWatched", false);  //Если равно False - игра запущена в первый раз

            tempStorage.saveValue("achGuessStarNormal", false); //ачивка, если равно True - получена
            tempStorage.saveValue("achGuessStarExpert", false); //ачивка
            tempStorage.saveValue("achGuessBandsNormal", false); //ачивка
            tempStorage.saveValue("achGuessBandsExpert", false); //ачивка
            tempStorage.saveValue("achSwipeTwoBandsNormal", false); //ачивка
            tempStorage.saveValue("achSwipeTwoBandsExpert", false); //ачивка
            tempStorage.saveValue("achGuessStarReversNormal", false); //ачивка
            tempStorage.saveValue("achGuessStarReversExpert", false); //ачивка
            tempStorage.saveValue("achSecretGameMode", false); //ачивка
            tempStorage.saveValue("achAdsFree", false); //ачивка
            tempStorage.saveValue("achRoyal", false); //ачивка

            tempStorage.saveValue("adActive", true); //включена ли реклама //можно было бы проверять по ачивке, но так интуитивнее

            tempStorage.saveValue("gameBuyed", false); //TODO надо сделать возможность купить игру, отключая рекламу. за это может быть ачивка

        }
    }

    protected void readStatus() { //считывает статус/атрибуты приложения //TODO удалить
        Storage storage = new Storage(this, "appStatus");
        sp = getSharedPreferences("appStatus", Context.MODE_PRIVATE);
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


        noticeWatched.setChecked(storage.getBoolean("noticeWatched")); //TODO по-хорошему надо переписать Storage, что бы не писать лишний раз nameOfStorage (можно указывать в конструкторе)
        achGuessStarNormalText.setChecked(storage.getBoolean("achGuessStarNormal"));
        achGuessStarExpertText.setChecked(storage.getBoolean("achGuessStarExpert"));
        achGuessBandsNormalText.setChecked(storage.getBoolean("achGuessBandsNormal"));
        achGuessBandsExpertText.setChecked(storage.getBoolean("achGuessBandsExpert"));
        achSwipeTwoBandsNormalText.setChecked(storage.getBoolean("achSwipeTwoBandsNormal"));
        achSwipeTwoBandsExpertText.setChecked(storage.getBoolean("achSwipeTwoBandsExpert"));
        achGuessStarReversNormalText.setChecked(storage.getBoolean("achGuessStarReversNormal"));
        achGuessStarReversExpertText.setChecked(storage.getBoolean("achGuessStarReversExpert"));
        achSecretGameModeText.setChecked(storage.getBoolean("achSecretGameMode"));
        achAdsFreeText.setChecked(storage.getBoolean("achAdsFree"));
        achRoyalText.setChecked(storage.getBoolean("achRoyal"));
    }

    protected void saveStatus() { //сохраняет значения свичей меняя атрибуты //TODO удалить
        Storage storage = new Storage(this, "appStatus");
        sp = getSharedPreferences("appStatus", Context.MODE_PRIVATE);
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


        storage.saveValue("noticeWatched", noticeWatched.isChecked());
        storage.saveValue("achGuessStarNormal", achGuessStarNormalText.isChecked());
        storage.saveValue("achGuessStarExpert", achGuessStarExpertText.isChecked());
        storage.saveValue("achGuessBandsNormal", achGuessBandsNormalText.isChecked());
        storage.saveValue("achGuessBandsExpert", achGuessBandsExpertText.isChecked());
        storage.saveValue("achSwipeTwoBandsNormal", achSwipeTwoBandsNormalText.isChecked());
        storage.saveValue("achSwipeTwoBandsExpert", achSwipeTwoBandsExpertText.isChecked());
        storage.saveValue("achGuessStarReversNormal", achGuessStarReversNormalText.isChecked());
        storage.saveValue("achGuessStarReversExpert", achGuessStarReversExpertText.isChecked());
        storage.saveValue("achSecretGameMode", achSecretGameModeText.isChecked());
        storage.saveValue("achAdsFree", achAdsFreeText.isChecked());
        storage.saveValue("achRoyal", achRoyalText.isChecked());


    }

    OptionsSet tempSettingsSet = new OptionsSet(false, false); //переменная для считывания состояния свиича на darkmod

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Storage storage = new Storage(this, "appStatus");

        Storage tempStorage = new Storage(this, "settings");
        String nameOfValue = "darkModeCounter";


        Storage storage2 = new Storage(this, "settings");
        tempSettingsSet.darkMode = storage2.getBoolean("darkMode"); //считываем состояние
        //теперь выбираем тему в зависимости от положения свича
        if (tempSettingsSet.darkMode == true) setTheme(R.style.AppTheme2);
        else setTheme(R.style.AppThemeLight);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //+++FirstLaunchHelp+++\\ // блок для первичного задания статусов (ачивок и прочего), а так же отображения приветсвия новых пользователей
        //Storage storage = new Storage(this);
        String nameOfStorage = "appStatus";
        sp = getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        appStatusGeneration(); //создание статусов приложения, не создаст, если статусы уже существуют
        if (sp.contains("noticeWatched")) {
            if (storage.getBoolean("noticeWatched") == false) {
                storage.saveValue("noticeWatched", true);

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
        if (tempSettingsSet.darkMode == true)
            guessStarButton.setBackgroundResource(R.drawable.stylebutton_dark);
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
        if (tempSettingsSet.darkMode == true)
            guessBandButton.setBackgroundResource(R.drawable.stylebutton_dark);
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
        if (tempSettingsSet.darkMode == true)
            buttonLibrary.setBackgroundResource(R.drawable.stylebutton_dark);
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
        if (tempSettingsSet.darkMode == true)
            settingsButton.setBackgroundResource(R.drawable.stylebutton_dark);
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
                startActivityForResult(image, REQUEST_CODE);
            }


        });


        Button tinderButton = findViewById(R.id.chooseTinderButton);
        if (tempSettingsSet.darkMode == true)
            tinderButton.setBackgroundResource(R.drawable.stylebutton_dark);
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
        if (tempSettingsSet.darkMode == true)
            about.setBackgroundResource(R.drawable.stylebutton_dark);
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
