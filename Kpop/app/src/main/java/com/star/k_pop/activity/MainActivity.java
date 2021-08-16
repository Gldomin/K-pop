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
import com.star.k_pop.gallery.activity.Gallery;
import com.star.k_pop.helper.Storage;
import com.star.k_pop.helper.Theme;
import com.yandex.metrica.YandexMetrica;

public class MainActivity extends AppCompatActivity {
    final int REQUEST_CODE = 1;

    Theme theme;//переменная для считывания состояния свиича на darkMod
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Storage storage = new Storage(this, "appStatus");
        theme = new Theme(this);
        theme.setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        guessStarButton.setBackgroundResource(theme.getBackgroundResource());
        guessStarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YandexMetrica.reportEvent("Main - guessStarButton");
                Intent image = new Intent();
                image.setClass(MainActivity.this, GuessStar.class);
                startActivity(image);
            }
        });

        Button guessBandButton = findViewById(R.id.guessGroupButton);
        guessBandButton.setBackgroundResource(theme.getBackgroundResource());
        guessBandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YandexMetrica.reportEvent("Main - guessBandButton");
                Intent image = new Intent();
                image.setClass(MainActivity.this, GuessBandsModeTwo.class);
                startActivity(image);
            }
        });

        Button guessTwoBandsTinder = findViewById(R.id.guessTwoBands);
        guessTwoBandsTinder.setBackgroundResource(theme.getBackgroundResource());
        guessTwoBandsTinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YandexMetrica.reportEvent("Main - guessTwoBands");
                Intent image = new Intent();
                image.setClass(MainActivity.this, TwoBandsTinder.class);
                startActivity(image);
            }
        });

        ImageView buttonLibrary = findViewById(R.id.galleryButton);
        buttonLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YandexMetrica.reportEvent("Main - gallary");
                Intent image = new Intent();
                image.setClass(MainActivity.this, Gallery.class);
                startActivity(image);
            }
        });

        ImageView settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YandexMetrica.reportEvent("Main - setting");
                Intent image = new Intent();
                image.setClass(MainActivity.this, Settings.class);
                startActivity(image);
            }
        });

        ImageView achievement = findViewById(R.id.achievementButton);
        achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YandexMetrica.reportEvent("Main - achievement");
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
                YandexMetrica.reportEvent("Main - about");
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

}
