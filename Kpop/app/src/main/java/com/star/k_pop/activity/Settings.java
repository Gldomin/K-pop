package com.star.k_pop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.star.k_pop.helper.OptionsSet;
import com.star.k_pop.R;
import com.star.k_pop.helper.Storage;
import com.star.k_pop.lib.SomeMethods;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class Settings extends AppCompatActivity {
    OptionsSet tempSettingsSet = new OptionsSet(false, false); //создание объекта для хранение параметров
    SharedPreferences sp;


    //OptionsSet tempSettingsSet2 = new OptionsSet(false, false); //переменная для считывания состояния свиича на darkmod
    String buttonStyleChange = "stylebutton";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Storage storage = new Storage(this);
        String nameOfStorage2 = "settings";


        tempSettingsSet.darkMode = storage.getBoolean(nameOfStorage2, "darkMode"); //считываем состояние
        //теперь выбираем тему в зависимости от положения свича
        if (tempSettingsSet.darkMode==true) setTheme(R.style.AppTheme2);
        else setTheme(R.style.AppThemeLight);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final Storage recordStorage = new Storage(this); //хранилищеРекорда

        final Switch hintModeSwitch = findViewById(R.id.optionSwitch1); //hintMode переключалка
        if (tempSettingsSet.darkMode==true) hintModeSwitch.setTextColor(getResources().getColor(R.color.colorText));
        else hintModeSwitch.setTextColor(getResources().getColor(R.color.colorTextLight));
        final Switch hardModeSwitch = findViewById(R.id.optionSwitch2); //hardMode переключалка
        if (tempSettingsSet.darkMode==true) hardModeSwitch.setTextColor(getResources().getColor(R.color.colorText));
        else hardModeSwitch.setTextColor(getResources().getColor(R.color.colorTextLight));
        final Switch darkThemeSwitch = findViewById(R.id.optionSwitch3); //darkMode переключалка
        if (tempSettingsSet.darkMode==true) darkThemeSwitch.setTextColor(getResources().getColor(R.color.colorText));
        else darkThemeSwitch.setTextColor(getResources().getColor(R.color.colorTextLight));
        //   final Switch optionSwitch4 = findViewById(R.id.optionSwitch4); //заменить когда появится новая опция
        //   final Switch optionSwitch5 = findViewById(R.id.optionSwitch5); //заменить когда появится новая опция
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
        darkThemeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tempSettingsSet.darkMode = darkThemeSwitch.isChecked(); //TODO Надо сделать темную тему
                // final ConstraintLayout cl = findViewById(R.id.constraint);
                // cl.setBackgroundColor(000000);

            }
        });
       /* optionSwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        });*/

        Button settingsConfirmButton = findViewById(R.id.settingsConfirm);
        if (tempSettingsSet.darkMode==true) settingsConfirmButton.setBackgroundResource(R.drawable.stylebutton_dark);
        else settingsConfirmButton.setBackgroundResource(R.drawable.stylebutton);

        settingsConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("name",1);
                setResult(RESULT_OK, intent);
                saveSettings();
                finish();
            }
        });

        Button creepyGuy = findViewById(R.id.settingsCancel); //TODO delete this shit

        if (tempSettingsSet.darkMode==true) creepyGuy.setBackgroundResource(R.drawable.stylebutton_dark);
        else creepyGuy.setBackgroundResource(R.drawable.stylebutton);

        creepyGuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = new ImageView(Settings.this);
                ConstraintLayout constraintLayout = findViewById(R.id.constraint);
                constraintLayout.addView(imageView);
                Glide.with(Settings.this).load(getResources().getDrawable(R.drawable.error))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transition(withCrossFade())
                        .into(imageView);
                Alert alert = new Alert();
                alert.execute();
            }
        });


        Button resetButton = findViewById(R.id.resetRecordButton); //кнопка сброса счета
        if (tempSettingsSet.darkMode==true) resetButton.setBackgroundResource(R.drawable.stylebutton_dark);
        else resetButton.setBackgroundResource(R.drawable.stylebutton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (SomeMethods.getBoolAnswerAlertDeialog(Settings.this, getResources().getString(R.string.questionConfirmTitle), getResources().getString(R.string.questionReset), getResources().getString(R.string.answerYes), getResources().getString(R.string.answerNo))) {
                    recordStorage.saveValue("UserScore", "userScoreGuessStar", 0);  //сброс рекорда. TODO надо сделать и для других режимов сброс
                    Toast.makeText(Settings.this, "Данные удалены", Toast.LENGTH_SHORT).show(); //отправка сообщения на экран
                }
            }
        });


///////Read Settings//////
        sp = getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sp.contains("hardMode") && sp.contains("hintMode") && sp.contains("option5")) {
            storage = new Storage(this);
            String nameOfStorage = "settings";
            tempSettingsSet.hintMode = storage.getBoolean(nameOfStorage, "hintMode"); //считывание настроек из Хранилища
            tempSettingsSet.hardMode = storage.getBoolean(nameOfStorage, "hardMode");
            tempSettingsSet.darkMode = storage.getBoolean(nameOfStorage, "darkMode");
            tempSettingsSet.option4 = storage.getBoolean(nameOfStorage, "option4");
            tempSettingsSet.option5 = storage.getBoolean(nameOfStorage, "option5");


            hintModeSwitch.setChecked(tempSettingsSet.hintMode); //установка значение свитча
            hardModeSwitch.setChecked(tempSettingsSet.hardMode);
            darkThemeSwitch.setChecked(tempSettingsSet.darkMode);
            //optionSwitch4.setChecked(tempSettingsSet.option4);
            //optionSwitch5.setChecked(tempSettingsSet.option5);


        } else {

            saveSettings();

        }


    }

    class Alert extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            finish();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(5350);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    void saveSettings() {

        Storage storage = new Storage(this);
        storage.saveValue("settings", "hintMode", tempSettingsSet.hintMode);
        storage.saveValue("settings", "hardMode", tempSettingsSet.hardMode);
        storage.saveValue("settings", "darkMode", tempSettingsSet.darkMode);
        storage.saveValue("settings", "option4", tempSettingsSet.option4);
        storage.saveValue("settings", "option5", tempSettingsSet.option5);
        Toast.makeText(Settings.this, getResources().getString(R.string.OptionsSet), Toast.LENGTH_LONG).show(); //отправка сообщения на экран

    }

    @Override
    protected void onPause() {
//save Settings

        //saveSettings();
        super.onPause();
    }

}