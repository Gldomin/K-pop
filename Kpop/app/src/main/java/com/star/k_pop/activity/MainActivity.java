package com.star.k_pop.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.star.k_pop.R;
import com.star.k_pop.StartApplication.Importer;
import com.star.k_pop.ad.InterstitialCustom;
import com.star.k_pop.ad.InterstitialCustomYandex;
import com.star.k_pop.gallery.activity.Gallery;
import com.star.k_pop.helper.Evaluate;
import com.star.k_pop.helper.Storage;
import com.star.k_pop.helper.Theme;

import io.appmetrica.analytics.AppMetrica;

public class MainActivity extends AppCompatActivity {
    final int REQUEST_CODE = 1;

    Theme theme;//переменная для считывания состояния свиича на darkMod
    SharedPreferences sp;

    private InterstitialCustom mInterstitialAd;

    private int countAd = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Загрузка в Importer данных о всех артистах

        Importer.createListArtists(getResources(), this);

        Storage storage = new Storage(this, "appStatus");
        theme = new Theme(this);
        theme.setThemeSecond();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInterstitialAd = new InterstitialCustomYandex(this, getResources().getString(R.string.yandex_id_interstitial_menu));

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
        guessStarButton.setOnClickListener(view -> {
            AppMetrica.reportEvent("Menu", "{\"ButtonStar\":\"Start\"}");
            Intent image = new Intent();
            image.setClass(MainActivity.this, GuessStar.class);
            startActivity(image);
            interstitialShow();
        });

        Button guessBandButton = findViewById(R.id.guessGroupButton);
        guessBandButton.setBackgroundResource(theme.getBackgroundButton());
        guessBandButton.setOnClickListener(view -> {
            AppMetrica.reportEvent("Menu", "{\"ButtonGroup\":\"Start\"}");
            Intent image = new Intent();
            image.setClass(MainActivity.this, GuessBandsModeTwo.class);
            startActivity(image);
            interstitialShow();
        });

        Button guessTwoBandsTinder = findViewById(R.id.guessTwoBands);
        guessTwoBandsTinder.setBackgroundResource(theme.getBackgroundButton());
        guessTwoBandsTinder.setOnClickListener(view -> {
            AppMetrica.reportEvent("Menu", "{\"ButtonTinder\":\"Start\"}");
            Intent image = new Intent();
            image.setClass(MainActivity.this, TwoBandsTinder.class);
            startActivity(image);
            interstitialShow();
        });

        ImageView buttonLibrary = findViewById(R.id.galleryButton);
        buttonLibrary.setOnClickListener(view -> {
            AppMetrica.reportEvent("Menu", "{\"ButtonLibrary\":\"Start\"}");
            Intent image = new Intent();
            image.setClass(MainActivity.this, Gallery.class);
            startActivity(image);
            interstitialShow();
        });

        ImageView settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(view -> {
            AppMetrica.reportEvent("Menu", "{\"ButtonSettings\":\"Start\"}");
            Intent image = new Intent();
            image.setClass(MainActivity.this, Settings.class);
            startActivityForResult(image, REQUEST_CODE);
        });

        ImageView achievement = findViewById(R.id.achievementButton);
        achievement.setOnClickListener(view -> {
            AppMetrica.reportEvent("Menu", "{\"ButtonAchievement\":\"Start\"}");
            Intent image = new Intent();
            image.setClass(MainActivity.this, Achievements.class);
            startActivity(image);
        });

        Button about = findViewById(R.id.abautButton);
        about.setBackgroundResource(theme.getBackgroundButton());
        about.setOnClickListener(view -> {
            AppMetrica.reportEvent("Menu", "{\"ButtonAbout\":\"Start\"}");
            Intent image = new Intent();
            image.setClass(MainActivity.this, BasicNotice.class);
            image.putExtra("text", R.string.aboutText);
            image.putExtra("title", R.string.aboutTitle);
            startActivity(image);
        });

        if (!sp.contains("ratingClose")) {
            if (!sp.contains("countShowRating")) {
                storage.saveValue("countShowRating", 3);
            } else {
                int count = storage.getInt("countShowRating");
                count--;
                if (count > 0) {
                    storage.saveValue("countShowRating", count);
                } else {
                    if (!sp.contains("showRatingFirst")) {
                        showRatingApp(true);
                        storage.saveValue("showRatingFirst", true);
                    } else {
                        showRatingApp(false);
                    }
                    storage.saveValue("countShowRating", 4);
                }
            }
        }
    }

    private void showRatingApp(boolean isFirst) {
        Dialog rating = new Dialog(this, theme.getAlertDialogStyle());
        rating.setContentView(R.layout.rating_app);
        TextView textRating = rating.findViewById(R.id.textRating);
        textRating.setTextColor(theme.getColorRatingText());
        RatingBar ratingBar = rating.findViewById(R.id.ratingBar);
        ratingBar.setStepSize(1);
        ratingBar.setProgressTintList(ColorStateList.valueOf(theme.getColorRatingStar()));
        ratingBar.setSecondaryProgressTintList(ColorStateList.valueOf(theme.getColorRatingStar()));
        ratingBar.setProgressBackgroundTintList(ColorStateList.valueOf(theme.getColorRatingStarBackground()));
        rating.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RelativeLayout relativeLayoutRating = rating.findViewById(R.id.ratingLayout);
        relativeLayoutRating.setBackgroundResource(theme.getStyleRatingBackground());
        Button buttonClose = rating.findViewById(R.id.buttonClose);
        buttonClose.setBackgroundResource(theme.getBackgroundButton());
        buttonClose.setTextColor(theme.getColorRatingButtonText());
        buttonClose.setOnClickListener(v -> {
            AppMetrica.reportEvent("Rating", "{\"close\":\"rating: " + (int) ratingBar.getRating() + "\"}");
            Storage storage = new Storage(this, "appStatus");
            storage.saveValue("ratingClose", true);
            rating.cancel();
        });
        if (isFirst) {
            buttonClose.setVisibility(View.GONE);
        }
        Button buttonCancel = rating.findViewById(R.id.buttonCancel);
        buttonCancel.setBackgroundResource(theme.getBackgroundButton());
        buttonCancel.setTextColor(theme.getColorRatingButtonText());
        buttonCancel.setOnClickListener(v -> {
            AppMetrica.reportEvent("Rating", "{\"cancel\":\"rating: " + (int) ratingBar.getRating() + "\"}");
            rating.cancel();
        });
        Button buttonSend = rating.findViewById(R.id.buttonSend);
        buttonSend.setBackgroundResource(theme.getBackgroundButton());
        buttonSend.setTextColor(theme.getColorRatingButtonText());
        buttonSend.setOnClickListener(v -> {
            Storage storage = new Storage(this, "appStatus");
            storage.saveValue("ratingClose", true);
            AppMetrica.reportEvent("Rating", "{\"send\":\"rating: " + (int) ratingBar.getRating() + "\"}");
            if (ratingBar.getRating() >= 4) {
                Evaluate ev = new Evaluate();
                Intent instant = ev.rateApp(getPackageName());
                startActivity(instant);
                rating.cancel();
            } else {
                rating.cancel();
            }
            AlertDialog.Builder alertBuild = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
            alertBuild.setTitle(getResources().getString(R.string.ratingSendCloseTitle))
                    .setMessage(getResources().getString(R.string.ratingSendClose))
                    .setPositiveButton(getResources().getString(R.string.tinderContinue), (dialogInterface, i) -> dialogInterface.cancel())
                    .setOnCancelListener(dialogInterface -> dialogInterface.cancel());
            AlertDialog alert = alertBuild.create();
            alert.show();
        });
        rating.show();
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
        Storage storage = new Storage(this, "appStatus");
        if (!storage.getBoolean("achTripleExpert")) {
            if (countAd <= 0) {
                countAd = 4;
                if (mInterstitialAd.show()) {
                    AppMetrica.reportEvent("Show ads", "{\"menu\":\"interstitial\"}");
                }
            }
            countAd--;
        } else {
            AppMetrica.reportEvent("Remove ads", "{\"menu\":\"interstitial\"}");
        }
    }

}
