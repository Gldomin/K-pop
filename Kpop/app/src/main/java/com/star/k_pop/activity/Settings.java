package com.star.k_pop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.star.k_pop.R;
import com.star.k_pop.helper.OptionsSet;
import com.star.k_pop.helper.Storage;
import com.star.k_pop.helper.Theme;
import com.star.k_pop.lib.SomeMethods;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class Settings extends AppCompatActivity {
    OptionsSet tempSettingsSet = new OptionsSet(false, false);
    Theme theme;
    SharedPreferences sp;
    int themeCount=1;

    private static final int[] RADIO_IDS ={
            R.id.blueVar,R.id.redVar
    };

    //OptionsSet tempSettingsSet2 = new OptionsSet(false, false); //переменная для считывания состояния свиича на darkmod
    String buttonStyleChange = "stylebutton";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Storage storage = new Storage(this, "settings");

        theme = new Theme(this);
        theme.setTheme();



        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        final Storage recordStorage = new Storage(this, "UserScore"); //хранилищеРекорда

        final Switch hintModeSwitch = findViewById(R.id.optionSwitch1); //hintMode переключалка
        if (theme.isDarkMode()) {
            hintModeSwitch.setTextColor(getResources().getColor(R.color.colorText));
        } else {
            hintModeSwitch.setTextColor(getResources().getColor(R.color.colorTextLight));
        }

        final Switch hardModeSwitch = findViewById(R.id.optionSwitch2); //hardMode переключалка
        if (theme.isDarkMode()) {
            hardModeSwitch.setTextColor(getResources().getColor(R.color.colorText));
        } else {
            hardModeSwitch.setTextColor(getResources().getColor(R.color.colorTextLight));
        }

        final Switch darkThemeSwitch = findViewById(R.id.optionSwitch3); //darkMode переключалка
        if (theme.isDarkMode()) {
            darkThemeSwitch.setTextColor(getResources().getColor(R.color.colorText));
        } else {
            darkThemeSwitch.setTextColor(getResources().getColor(R.color.colorTextLight));
        }
        final  RadioGroup radGroup = findViewById(R.id.radGroup);
        final RadioButton  blueBut = findViewById(R.id.blueVar);
        if (theme.isDarkMode()) {
            blueBut.setTextColor(getResources().getColor(R.color.colorText));
        } else {
            blueBut.setTextColor(getResources().getColor(R.color.colorTextLight));
        }
        final ImageView themeIm = findViewById(R.id.exampleBack);
        final RadioButton  redBut = findViewById(R.id.redVar);
        if (theme.isDarkMode()) {
            redBut.setTextColor(getResources().getColor(R.color.colorText));
        } else {
            redBut.setTextColor(getResources().getColor(R.color.colorTextLight));
        }
        if (theme.isDarkMode()) { radGroup.setVisibility(View.GONE);
        themeIm.setVisibility(View.GONE);
        }
        else {radGroup.setVisibility(View.VISIBLE);
            themeIm.setVisibility(View.VISIBLE);
        }

        themeIm.setImageResource(R.drawable.main_background);

radGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                                    {

                                        @Override
                                        public void onCheckedChanged(RadioGroup radGroup, int checkedId) {
                                            if (checkedId == R.id.blueVar){
                                                themeIm.setImageResource(R.drawable.main_background);
                                            tempSettingsSet.themeCount=1;}
                                            else if (checkedId == R.id.redVar){
                                                themeIm.setImageResource(R.drawable.main_background2);
                                            tempSettingsSet.themeCount=2;}
                                            //switch (radGroup.getId()){
                                               // case R.id.blueVar:
                                                  //  tempSettingsSet.themeCount=1;
                                                  //  break;
                                                //case R.id.redVar:
                                                 //   tempSettingsSet.themeCount=2;
                                           // }
                                       }
                                    }
                                            );


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
        if (theme.isDarkMode()) {
            settingsConfirmButton.setBackgroundResource(R.drawable.stylebutton_dark);
        } else {
            settingsConfirmButton.setBackgroundResource(R.drawable.stylebutton);
        }

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

        Button creepyGuy = findViewById(R.id.settingsCancel); //TODO delete this shit
        if (theme.isDarkMode()) {
            creepyGuy.setBackgroundResource(R.drawable.stylebutton_dark);
        } else {
            creepyGuy.setBackgroundResource(R.drawable.stylebutton);
        }

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
        if (theme.isDarkMode()) {
            resetButton.setBackgroundResource(R.drawable.stylebutton_dark);
        } else {
            resetButton.setBackgroundResource(R.drawable.stylebutton);
        }

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SomeMethods.getBoolAnswerAlertDeialog(Settings.this, getResources().getString(R.string.questionConfirmTitle), getResources().getString(R.string.questionReset), getResources().getString(R.string.answerYes), getResources().getString(R.string.answerNo))) {
                    recordStorage.saveValue("userScoreGuessStar", 0);  //сброс рекорда. TODO надо сделать и для других режимов сброс
                    Toast.makeText(Settings.this, "Данные удалены", Toast.LENGTH_SHORT).show(); //отправка сообщения на экран
                }
            }
        });


///////Read Settings//////
        sp = getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sp.contains("hardMode") && sp.contains("hintMode") && sp.contains("option5")) {
            storage = new Storage(this, "settings");
            tempSettingsSet.hintMode = storage.getBoolean("hintMode"); //считывание настроек из Хранилища
            tempSettingsSet.hardMode = storage.getBoolean("hardMode");
            tempSettingsSet.darkMode = storage.getBoolean("darkMode");
            tempSettingsSet.option4 = storage.getBoolean("option4");
            tempSettingsSet.option5 = storage.getBoolean("option5");
            tempSettingsSet.themeCount = storage.getInt("themeCount");

            hintModeSwitch.setChecked(tempSettingsSet.hintMode); //установка значение свитча
            hardModeSwitch.setChecked(tempSettingsSet.hardMode);
            darkThemeSwitch.setChecked(tempSettingsSet.darkMode);

            if (tempSettingsSet.themeCount==1) radGroup.check(R.id.blueVar);
            if (tempSettingsSet.themeCount==2) radGroup.check(R.id.redVar);

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
        Storage storage = new Storage(this, "settings");
        storage.saveValue("hintMode", tempSettingsSet.hintMode);
        storage.saveValue("hardMode", tempSettingsSet.hardMode);
        storage.saveValue("darkMode", tempSettingsSet.darkMode);
        storage.saveValue("option4", tempSettingsSet.option4);
        storage.saveValue("option5", tempSettingsSet.option5);
        storage.saveValue("themeCount",tempSettingsSet.themeCount);
        Toast.makeText(Settings.this, getResources().getString(R.string.OptionsSet), Toast.LENGTH_LONG).show(); //отправка сообщения на экран
    }

    @Override
    protected void onPause() {
        //save Settings
        //saveSettings();
        super.onPause();
    }

}