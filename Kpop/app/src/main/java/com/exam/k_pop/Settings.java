package com.exam.k_pop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class Settings extends AppCompatActivity {
    OptionsSet tempSettingsSet = new OptionsSet(false, false); //создание объекта для хранение параметров
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        final Switch hintModeSwitch = findViewById(R.id.optionSwitch1); //hintMode переключалка
        final Switch hardModeSwitch = findViewById(R.id.optionSwitch2); //hardMode переключалка
        final Switch optionSwitch3 = findViewById(R.id.optionSwitch3); //заменить когда появится новая опция
        final Switch optionSwitch4 = findViewById(R.id.optionSwitch4); //заменить когда появится новая опция
        final Switch optionSwitch5 = findViewById(R.id.optionSwitch5); //заменить когда появится новая опция
//hintModeSwitch.setOnCheckedChangeListener
        hintModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
// Switch temp =findViewById(R.id.optionSwitch1);
// settings.hintMode=temp.isChecked();
                tempSettingsSet.hintMode = hintModeSwitch.isChecked();
                //tempSettingsSet.hardMode= !tempSettingsSet.hardMode;
                //saveSettings();

            }
        });
        hardModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
// Switch temp =findViewById(R.id.optionSwitch2);
// settings.hardMode =temp.isChecked();
                //tempSettingsSet.hintMode= !tempSettingsSet.hintMode;
                tempSettingsSet.hardMode = hardModeSwitch.isChecked();
                //saveSettings();
            }
        });
        optionSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tempSettingsSet.option3 = optionSwitch3.isChecked();
            }
        });
        optionSwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tempSettingsSet.option4 = optionSwitch4.isChecked();
            }
        });
        optionSwitch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tempSettingsSet.option5 = optionSwitch5.isChecked();
            }
        });

        Button settingsConfirmButton = findViewById(R.id.settingsConfirm);
        settingsConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveSettings();
                finish();
            }
        });



///////Read Settings//////
        sp = getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sp.contains("hardMode") && sp.contains("hintMode") && sp.contains("option5")) {
            Storage storage= new Storage(this);
            String nameOfStorage = "settings";
            tempSettingsSet.hintMode =storage.getBoolean(nameOfStorage,"hintMode"); //считывание настроек из Хранилища
            tempSettingsSet.hardMode =storage.getBoolean(nameOfStorage,"hardMode");
            tempSettingsSet.option3 =storage.getBoolean(nameOfStorage,"option3");
            tempSettingsSet.option4 =storage.getBoolean(nameOfStorage,"option4");
            tempSettingsSet.option5 =storage.getBoolean(nameOfStorage,"option5");


            hintModeSwitch.setChecked(tempSettingsSet.hintMode); //установка значение свитча
            hardModeSwitch.setChecked(tempSettingsSet.hardMode);
            optionSwitch3.setChecked(tempSettingsSet.option3);
            optionSwitch4.setChecked(tempSettingsSet.option4);
            optionSwitch5.setChecked(tempSettingsSet.option5);



        } else {

            saveSettings();

        }


    }

    void saveSettings() {

        Storage storage = new Storage(this);
        storage.saveValue("settings","hintMode",tempSettingsSet.hintMode);
        storage.saveValue("settings","hardMode",tempSettingsSet.hardMode);
        storage.saveValue("settings","option3",tempSettingsSet.option3);
        storage.saveValue("settings","option4",tempSettingsSet.option4);
        storage.saveValue("settings","option5",tempSettingsSet.option5);
        Toast.makeText(Settings.this,getResources().getString(R.string.OptionsSet), Toast.LENGTH_LONG).show(); //отправка сообщения на экран

    }

    @Override
    protected void onPause() {
//save Settings

        //saveSettings();
        super.onPause();
    }

}