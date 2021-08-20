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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.star.k_pop.R;
import com.star.k_pop.helper.OptionsSet;
import com.star.k_pop.helper.Storage;
import com.star.k_pop.helper.Theme;
import com.star.k_pop.lib.SomeMethods;
import com.yandex.metrica.YandexMetrica;

public class Settings extends AppCompatActivity {
    OptionsSet tempSettingsSet = new OptionsSet(false, false);
    Theme theme;

    RadioGroup radGroup;
    RadioButton blueBut;
    ImageView themeIm;
    RadioButton redBut;
    RadioButton catBut;
    SwitchCompat darkThemeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        theme = new Theme(this);

        theme.setThemeSecond();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        final Storage recordStorage = new Storage(this, "UserScore"); //хранилищеРекорда

        darkThemeSwitch = findViewById(R.id.optionSwitch3); //darkMode переключалка
        darkThemeSwitch.setTextColor(theme.getButtonTextColor());

        radGroup = findViewById(R.id.radGroup);
        blueBut = findViewById(R.id.blueVar);
        themeIm = findViewById(R.id.exampleBack);
        redBut = findViewById(R.id.redVar);
        catBut = findViewById(R.id.catVar);

        blueBut.setTextColor(theme.getButtonTextColor());
        redBut.setTextColor(theme.getButtonTextColor());
        catBut.setTextColor(theme.getButtonTextColor());
        if (theme.isDarkMode()) {
            blueBut.setText(getResources().getString(R.string.settingRed));
            redBut.setText(getResources().getString(R.string.settingGreen));
            catBut.setText(getResources().getString(R.string.settingPink));
        } else {
            blueBut.setText(getResources().getString(R.string.settingRabbit));
            redBut.setText(getResources().getString(R.string.settingHamster));
            catBut.setText(getResources().getString(R.string.settingCat));

        }
        themeIm.setImageResource(theme.getBackgroundButton2());

        Storage storage = new Storage(this, "settings");
        tempSettingsSet.darkMode = storage.getBoolean("darkMode");
        tempSettingsSet.themeCount = storage.getInt("themeCount");

        darkThemeSwitch.setChecked(tempSettingsSet.darkMode);

        if (tempSettingsSet.themeCount == 1) radGroup.check(R.id.blueVar);
        if (tempSettingsSet.themeCount == 2) radGroup.check(R.id.redVar);
        if (tempSettingsSet.themeCount == 3) radGroup.check(R.id.catVar);

        radGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                                                @Override
                                                public void onCheckedChanged(RadioGroup radGroup, int checkedId) {
                                                    if (checkedId == R.id.blueVar) {
                                                        chooseTheme(1);
                                                    } else if (checkedId == R.id.redVar) {
                                                        chooseTheme(2);
                                                    } else if (checkedId == R.id.catVar) {
                                                        chooseTheme(3);
                                                    }
                                                }
                                            }
        );

        darkThemeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tempSettingsSet.darkMode = darkThemeSwitch.isChecked();
                if (darkThemeSwitch.isChecked()) {
                    blueBut.setText(getResources().getString(R.string.settingRed));
                    redBut.setText(getResources().getString(R.string.settingGreen));
                    catBut.setText(getResources().getString(R.string.settingPink));
                } else {
                    blueBut.setText(getResources().getString(R.string.settingRabbit));
                    redBut.setText(getResources().getString(R.string.settingHamster));
                    catBut.setText(getResources().getString(R.string.settingCat));
                }
                if (blueBut.isChecked()) {
                    chooseTheme(1);
                } else if (redBut.isChecked()) {
                    chooseTheme(2);
                } else if (catBut.isChecked()) {
                    chooseTheme(3);
                }
            }
        });

        Button settingsConfirmButton = findViewById(R.id.settingsConfirm);
        settingsConfirmButton.setBackgroundResource(theme.getBackgroundResource());

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
        creepyGuy.setBackgroundResource(theme.getBackgroundResource());

        creepyGuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button resetButton = findViewById(R.id.resetRecordButton); //кнопка сброса счета
        resetButton.setBackgroundResource(theme.getBackgroundResource());
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

    }

    void chooseTheme(int num) {
        if (num == 1) {
            if (darkThemeSwitch.isChecked()) {
                themeIm.setImageResource(R.drawable.stylebutton_dark);
            } else {
                themeIm.setImageResource(R.drawable.main_background);
            }
            tempSettingsSet.themeCount = 1;
        } else if (num == 2) {
            if (darkThemeSwitch.isChecked()) {
                themeIm.setImageResource(R.drawable.stylebutton_dark_purple);
            } else {
                themeIm.setImageResource(R.drawable.main_background_hamster);
            }
            tempSettingsSet.themeCount = 2;
        } else if (num == 3) {
            if (darkThemeSwitch.isChecked()) {
                themeIm.setImageResource(R.drawable.stylebutton_dark_pink);
            } else {
                themeIm.setImageResource(R.drawable.main_background_cat);
            }
            tempSettingsSet.themeCount = 3;
        }
    }

    void saveSettings() {
        YandexMetrica.reportEvent("Темная тема: " + darkThemeSwitch.isChecked() + ", Выбранная тема: " + tempSettingsSet.themeCount);
        Storage storage = new Storage(this, "settings");
        storage.saveValue("darkMode", tempSettingsSet.darkMode);
        storage.saveValue("themeCount", tempSettingsSet.themeCount);
        //Toast.makeText(Settings.this, getResources().getString(R.string.OptionsSet), Toast.LENGTH_LONG).show(); //отправка сообщения на экран
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}