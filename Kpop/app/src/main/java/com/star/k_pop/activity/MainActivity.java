package com.star.k_pop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.star.k_pop.R;
import com.star.k_pop.StartApplication.Importer;
import com.star.k_pop.ad.InterstitialCustomGoogle;
import com.star.k_pop.ad.InterstitialCustomYandex;
import com.star.k_pop.ad.RewardedCustomGoogle;
import com.star.k_pop.ad.RewardedCustomYandex;
import com.star.k_pop.gallery.activity.Gallery;
import com.star.k_pop.helper.Storage;
import com.star.k_pop.helper.Theme;
import com.yandex.metrica.YandexMetrica;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    final int REQUEST_CODE = 1;

    Theme theme;//переменная для считывания состояния свиича на darkMod
    SharedPreferences sp;
    private InterstitialCustomGoogle mInterstitialAdGoogle;
    private InterstitialCustomYandex mInterstitialAdYandex;

    private int countAd = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Загрузка в Importer данных о всех артистах

        Importer.createListArtists(getResources(), this);

        Storage storage = new Storage(this, "appStatus");
        theme = new Theme(this);
        theme.setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Locale.getDefault().getLanguage().equals("ru")) {
            mInterstitialAdYandex = new InterstitialCustomYandex(this);
        } else {
            mInterstitialAdGoogle = new InterstitialCustomGoogle(this, R.string.admob_id_interstitial);
        }


        String nameOfStorage = "appStatus";
        sp = getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        if (sp.contains("noticeWatched")) {
            if (!storage.getBoolean("noticeWatched")) {
                storage.saveValue("noticeWatched", true);
                Intent image = new Intent();
                image.setClass(MainActivity.this, BasicNotice.class);
                image.putExtra("text", R.string.aboutText);
                image.putExtra("title", R.string.aboutTitle);
                startActivity(image);
            }
        }

        Button guessStarButton = findViewById(R.id.guessStarButton);
        guessStarButton.setBackgroundResource(theme.getBackgroundButton());
        guessStarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YandexMetrica.reportEvent("Menu", "{\"Button\":\"guessStarButton\"}");
                Intent image = new Intent();
                image.setClass(MainActivity.this, GuessStar.class);
                startActivity(image);
                interstitialShow();
                YandexMetrica.reportEvent("Reward", "{\"Menu\":\"Show\"}");

            }
        });

        Button guessTwoBandsTinder = findViewById(R.id.guessTwoBands);
        guessTwoBandsTinder.setBackgroundResource(theme.getBackgroundButton());
        guessTwoBandsTinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YandexMetrica.reportEvent("Main - guessTwoBands");
                Intent image = new Intent();
                image.setClass(MainActivity.this, TwoBandsTinder.class);
                startActivity(image);
                interstitialShow();
            }
        });

        Button guessBandButton = findViewById(R.id.guessGroupButton);
        guessBandButton.setBackgroundResource(theme.getBackgroundButton());
        guessBandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YandexMetrica.reportEvent("Menu", "{\"Button\":\"guessBandButton\"}");
                Intent image = new Intent();
                image.setClass(MainActivity.this, GuessBandsModeTwo.class);
                startActivity(image);
                interstitialShow();
                YandexMetrica.reportEvent("Reward", "{\"Menu\":\"Show\"}");
            }
        });

        ImageView buttonLibrary = findViewById(R.id.galleryButton);
        buttonLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YandexMetrica.reportEvent("Menu", "{\"Button\":\"buttonLibrary\"}");
                Intent image = new Intent();
                image.setClass(MainActivity.this, Gallery.class);
                startActivity(image);
                interstitialShow();
            }
        });

        ImageView settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YandexMetrica.reportEvent("Menu", "{\"Button\":\"settingsButton\"}");
                Intent image = new Intent();
                image.setClass(MainActivity.this, Settings.class);
                startActivityForResult(image, REQUEST_CODE);
            }
        });

        ImageView achievement = findViewById(R.id.achievementButton);
        achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YandexMetrica.reportEvent("Menu", "{\"Button\":\"achievement\"}");
                Intent image = new Intent();
                image.setClass(MainActivity.this, Achievements.class);
                startActivity(image);
            }
        });

        Button about = findViewById(R.id.abautButton);
        about.setBackgroundResource(theme.getBackgroundButton());
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YandexMetrica.reportEvent("Menu", "{\"Button\":\"about\"}");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            recreate();
        }
    }

    private void interstitialShow() {
        if (countAd <= 0) {
            countAd = 4;
            if (Locale.getDefault().getLanguage().equals("ru")) {
                mInterstitialAdYandex.show(this);
            }else{
                mInterstitialAdGoogle.show();
            }

        }
        countAd--;
    }

}
