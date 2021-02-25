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
import com.star.k_pop.helper.Storage;
import com.star.k_pop.helper.Theme;

public class MainActivity extends AppCompatActivity {
    final int REQUEST_CODE = 1;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Storage storage = new Storage(this, "appStatus");
        theme = new Theme(this);
        theme.setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //+++FirstLaunchHelp+++\\ // блок для первичного задания статусов (ачивок и прочего), а так же отображения приветсвия новых пользователей
        //Storage storage = new Storage(this);
        String nameOfStorage = "appStatus";
        sp = getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        appStatusGeneration(); //создание статусов приложения, не создаст, если статусы уже существуют
        if (sp.contains("noticeWatched")) {
            if (!storage.getBoolean("noticeWatched")) {
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
        guessStarButton.setBackgroundResource(theme.getBackgroundResource());
        guessStarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, GuessStar.class);
                startActivity(image);
            }
        });

        Button guessStarReverseButton = findViewById(R.id.guessStarReverseButton);
        guessStarReverseButton.setBackgroundResource(theme.getBackgroundResource());
        guessStarReverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, GuessStarReverse.class);
                startActivity(image);
            }
        });

        Button guessBandButton = findViewById(R.id.guessGroupButton);
        guessBandButton.setBackgroundResource(theme.getBackgroundResource());
        guessBandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, GuessBands.class);
                startActivity(image);
            }
        });

        Button buttonLibrary = findViewById(R.id.galleryButton);
        buttonLibrary.setBackgroundResource(theme.getBackgroundResource());
        buttonLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, Gallery.class);
                startActivity(image);
            }
        });

        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setBackgroundResource(theme.getBackgroundResource());

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, Settings.class);
                startActivityForResult(image, REQUEST_CODE);
            }


        });


        Button tinderButton = findViewById(R.id.chooseTinderButton);
        tinderButton.setBackgroundResource(theme.getBackgroundResource());
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
        about.setBackgroundResource(theme.getBackgroundResource());
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, BasicNotice.class);
                image.putExtra("text", R.string.aboutText);
                image.putExtra("title", R.string.aboutTitle);
                startActivity(image);
            }
        });
    }

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
            tempStorage.saveValue("achTripleAdept", false); //ачивка
            tempStorage.saveValue("achTripleExpert", false); //ачивка
            tempStorage.saveValue("achRoyal", false); //ачивка

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

        noticeWatched.setVisibility(View.INVISIBLE);
        achGuessStarNormalText.setVisibility(View.INVISIBLE);
        achGuessStarExpertText.setVisibility(View.INVISIBLE);
        achGuessBandsNormalText.setVisibility(View.INVISIBLE);
        achGuessBandsExpertText.setVisibility(View.INVISIBLE);
        achSwipeTwoBandsNormalText.setVisibility(View.INVISIBLE);
        achSwipeTwoBandsExpertText.setVisibility(View.INVISIBLE);
        achGuessStarReversNormalText.setVisibility(View.INVISIBLE);
        achGuessStarReversExpertText.setVisibility(View.INVISIBLE);
        achSecretGameModeText.setVisibility(View.INVISIBLE);
        achAdsFreeText.setVisibility(View.INVISIBLE);
        achRoyalText.setVisibility(View.INVISIBLE);


        noticeWatched.setChecked(storage.getBoolean("noticeWatched"));
        achGuessStarNormalText.setChecked(storage.getBoolean("achGuessStarNormal"));
        achGuessStarExpertText.setChecked(storage.getBoolean("achGuessStarExpert"));
        achGuessBandsNormalText.setChecked(storage.getBoolean("achGuessBandsNormal"));
        achGuessBandsExpertText.setChecked(storage.getBoolean("achGuessBandsExpert"));
        achSwipeTwoBandsNormalText.setChecked(storage.getBoolean("achSwipeTwoBandsNormal"));
        achSwipeTwoBandsExpertText.setChecked(storage.getBoolean("achSwipeTwoBandsExpert"));
        achGuessStarReversNormalText.setChecked(storage.getBoolean("achGuessStarReversNormal"));
        achGuessStarReversExpertText.setChecked(storage.getBoolean("achGuessStarReversExpert"));
        achSecretGameModeText.setChecked(storage.getBoolean("achTripleAdept"));
        achAdsFreeText.setChecked(storage.getBoolean("achTripleExpert"));
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


        storage.saveValue("noticeWatched", noticeWatched.isChecked());
        storage.saveValue("achGuessStarNormal", achGuessStarNormalText.isChecked());
        storage.saveValue("achGuessStarExpert", achGuessStarExpertText.isChecked());
        storage.saveValue("achGuessBandsNormal", achGuessBandsNormalText.isChecked());
        storage.saveValue("achGuessBandsExpert", achGuessBandsExpertText.isChecked());
        storage.saveValue("achSwipeTwoBandsNormal", achSwipeTwoBandsNormalText.isChecked());
        storage.saveValue("achSwipeTwoBandsExpert", achSwipeTwoBandsExpertText.isChecked());
        storage.saveValue("achGuessStarReversNormal", achGuessStarReversNormalText.isChecked());
        storage.saveValue("achGuessStarReversExpert", achGuessStarReversExpertText.isChecked());
        storage.saveValue("achTripleAdept", achSecretGameModeText.isChecked());
        storage.saveValue("achTripleExpert", achAdsFreeText.isChecked());
        storage.saveValue("achRoyal", achRoyalText.isChecked());


    }

    Theme theme;//переменная для считывания состояния свиича на darkmod


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
