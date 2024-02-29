package com.star.k_pop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.star.k_pop.R;
import com.star.k_pop.helper.Storage;
import com.star.k_pop.helper.Theme;
import com.star.k_pop.lib.SomeMethods;

public class Settings extends AppCompatActivity {

    Theme theme;
    SwitchCompat optionSwitchDarkTheme;
    SwitchCompat optionSwitchSound;
    SwitchCompat optionSwitchGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        theme = new Theme(this);
        theme.setThemeSecond();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final TextView settingText = findViewById(R.id.settingTitleTextView);
        settingText.setTextColor(theme.getTextColor());

        final Storage recordStorage = new Storage(this, "UserScore"); //хранилищеРекорда

        Storage storage = new Storage(this, "settings");

        optionSwitchDarkTheme = findViewById(R.id.optionSwitchDarkTheme); //darkMode переключалка
        optionSwitchDarkTheme.setTextColor(theme.getTextColor());
        optionSwitchDarkTheme.setChecked(storage.getBoolean("darkMode"));

        optionSwitchSound = findViewById(R.id.optionSwitchSound); //sound переключалка
        optionSwitchSound.setTextColor(theme.getTextColor());
        optionSwitchSound.setChecked(storage.getBoolean("soundMode"));

        optionSwitchGroup = findViewById(R.id.optionSwitchGroup); //Group переключалка
        optionSwitchGroup.setTextColor(theme.getTextColor());
        optionSwitchGroup.setChecked(storage.getBoolean("modeOnlyGroup"));

        Button settingsConfirmButton = findViewById(R.id.settingsConfirm);
        settingsConfirmButton.setBackgroundResource(theme.getBackgroundButton());

        settingsConfirmButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra("name", 1);
            setResult(RESULT_OK, intent);
            saveSettings();
            finish();
        });

        Button creepyGuy = findViewById(R.id.settingsCancel);
        creepyGuy.setBackgroundResource(theme.getBackgroundButton());

        creepyGuy.setOnClickListener(view -> finish());

        Button resetButton = findViewById(R.id.resetRecordButton); //кнопка сброса счета
        resetButton.setBackgroundResource(theme.getBackgroundButton());
        resetButton.setOnClickListener(view -> SomeMethods.showAlertDialog(Settings.this, theme.getAlertDialogStyle(),
                getResources().getString(R.string.questionConfirmTitle),
                getResources().getString(R.string.questionReset),
                getResources().getString(R.string.answerYes),
                getResources().getString(R.string.answerNo),
                (dialogInterface, i) -> {
                    recordStorage.saveValue("userScoreGuessStar", 0);
                    recordStorage.saveValue("userScoreGuessBand", 0);
                    recordStorage.saveValue("userScoreTinder", 0);
                    recordStorage.saveValue("userScoreGuessBandModeTwo", 0);
                    recordStorage.saveValue("userScoreGuessStarReverse", 0);
                }, (dialogInterface, i) -> {

                }));

        Button buttonBandsActive = findViewById(R.id.bandsActive);
        buttonBandsActive.setBackgroundResource(theme.getBackgroundButton());
        buttonBandsActive.setOnClickListener(view -> {
            Intent image = new Intent();
            image.setClass(Settings.this, BandsActiveActivity.class);
            startActivity(image);
        });
    }

    void saveSettings() {
        Storage storage = new Storage(this, "settings");
        storage.saveValue("darkMode", optionSwitchDarkTheme.isChecked());
        storage.saveValue("soundMode", optionSwitchSound.isChecked());
        storage.saveValue("modeOnlyGroup", optionSwitchGroup.isChecked());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}