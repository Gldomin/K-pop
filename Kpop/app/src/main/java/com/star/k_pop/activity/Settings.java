package com.star.k_pop.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.star.k_pop.R;
import com.star.k_pop.helper.OptionsSet;
import com.star.k_pop.helper.Storage;
import com.star.k_pop.helper.Theme;
import com.star.k_pop.lib.SomeMethods;
import com.yandex.metrica.YandexMetrica;

public class Settings extends AppCompatActivity {

    Theme theme;
    SwitchCompat optionSwitch1;
    SwitchCompat optionSwitch2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        theme = new Theme(this);
        theme.setThemeSecond();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final TextView settingText = findViewById(R.id.settingTitleTextView);
        settingText.setTextColor(theme.getTextColor());

         optionSwitch1 = findViewById(R.id.optionSwitch1); //darkMode переключалка
        optionSwitch1.setTextColor(theme.getTextColor());

         optionSwitch2 = findViewById(R.id.optionSwitch2); //sound переключалка
        optionSwitch2.setTextColor(theme.getTextColor());






        final Storage recordStorage = new Storage(this, "UserScore"); //хранилищеРекорда




        Storage storage = new Storage(this, "settings");

        optionSwitch1.setChecked(storage.getBoolean("darkMode"));
        optionSwitch2.setChecked(storage.getBoolean("soundMode"));



        Button settingsConfirmButton = findViewById(R.id.settingsConfirm);
        settingsConfirmButton.setBackgroundResource(theme.getBackgroundButton());

        settingsConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("name", 1);
                setResult(RESULT_OK, intent);
                saveSettings();
                finish();
            }
        });

        Button creepyGuy = findViewById(R.id.settingsCancel);
        creepyGuy.setBackgroundResource(theme.getBackgroundButton());

        creepyGuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button resetButton = findViewById(R.id.resetRecordButton); //кнопка сброса счета
        resetButton.setBackgroundResource(theme.getBackgroundButton());
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SomeMethods.showAlertDialog(Settings.this, theme.getAlertDialogStyle(),
                        getResources().getString(R.string.questionConfirmTitle),
                        getResources().getString(R.string.questionReset),
                        getResources().getString(R.string.answerYes),
                        getResources().getString(R.string.answerNo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                YandexMetrica.reportEvent("Сброс рекорда");
                                Log.i("Settings", "" + recordStorage.getInt("userScoreGuessStar"));
                                recordStorage.saveValue("userScoreGuessStar", 0);
                                recordStorage.saveValue("userScoreGuessBand", 0);
                                recordStorage.saveValue("userScoreGuessStarReverse", 0);
                                Log.i("Settings", "" + recordStorage.getInt("userScoreGuessStar"));
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
            }
        });

        Button buttonBandsActive = findViewById(R.id.bandsActive);
        buttonBandsActive.setBackgroundResource(theme.getBackgroundButton());
        buttonBandsActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YandexMetrica.reportEvent("Setting - BandsActive");
                Intent image = new Intent();
                image.setClass(Settings.this, BandsActiveActivity.class);
                startActivity(image);
            }
        });

    }



    void saveSettings() {
        YandexMetrica.reportEvent("Темная тема: Darkmode - " + optionSwitch1.isChecked());
        Storage storage = new Storage(this, "settings");
        storage.saveValue("darkMode", optionSwitch1.isChecked());
        storage.saveValue("soundMode",optionSwitch2.isChecked());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}