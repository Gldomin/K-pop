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
    OptionsSet tempSettingsSet = new OptionsSet(false,false); //создание объекта для хранение параметров
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
                tempSettingsSet.hintMode= hintModeSwitch.isChecked();
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
                tempSettingsSet.hardMode= hardModeSwitch.isChecked();
                //saveSettings();
            }
        });
        optionSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tempSettingsSet.option3= optionSwitch3.isChecked(); }
        });
        optionSwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tempSettingsSet.option4= optionSwitch4.isChecked(); }
        });
        optionSwitch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tempSettingsSet.option5= optionSwitch5.isChecked(); }
        });

        Button settingsConfirmButton = findViewById(R.id.settingsConfirm);
        settingsConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveSettings();

            }
        });




//TODO индуский метод, надо переделать. все создается и обрабатывается вручную. то есть если добавим настроек придется продолжать индусить... но я хз как сделать иначе
///////Read Settings//////
        sp = getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sp.contains("hardMode") && sp.contains("hintMode") && sp.contains("option5"))
        {
            tempSettingsSet.hintMode = sp.getBoolean("hintMode", tempSettingsSet.hintMode); //взятие settings из Хранилища
            tempSettingsSet.hardMode = sp.getBoolean("hardMode", tempSettingsSet.hardMode);
            tempSettingsSet.option3 = sp.getBoolean("option3", tempSettingsSet.option3);
            tempSettingsSet.option4 = sp.getBoolean("option4", tempSettingsSet.option4);
            tempSettingsSet.option5 = sp.getBoolean("option5", tempSettingsSet.option5);

            hintModeSwitch.setChecked(tempSettingsSet.hintMode); //установка значение свитча
            hardModeSwitch.setChecked(tempSettingsSet.hardMode);
            optionSwitch3.setChecked(tempSettingsSet.option3);
            optionSwitch4.setChecked(tempSettingsSet.option4);
            optionSwitch5.setChecked(tempSettingsSet.option5);




            //Toast.makeText(Settings.this, "OK" + tempSettingsSet.hintMode, Toast.LENGTH_LONG).show(); //отправка сообщения на экран

        } else {

            saveSettings();

        }
// if (savedInstanceState != null)
// scoreNow = savedInstanceState.getInt("scoreNow");
///////////////////








    }
    void saveSettings(){

        sp = getSharedPreferences("settings", Context.MODE_PRIVATE);

        SharedPreferences.Editor e = sp.edit();
      //  e.putBoolean("hardMode", tempSettingsSet.hardMode);
      //  e.putBoolean("hintMode", tempSettingsSet.hintMode);
        e.putBoolean("hardMode", tempSettingsSet.hardMode);
        e.putBoolean("hintMode", tempSettingsSet.hintMode);
        e.putBoolean("option3", tempSettingsSet.option3);
        e.putBoolean("option4", tempSettingsSet.option4);
        e.putBoolean("option5", tempSettingsSet.option5);
        e.apply();




        Toast.makeText(Settings.this, tempSettingsSet.hintMode + "->" + sp.getBoolean("hintMode", tempSettingsSet.hintMode) +"|" + tempSettingsSet.hardMode + "->" + sp.getBoolean("hardMode", tempSettingsSet.hardMode) , Toast.LENGTH_LONG).show(); //отправка сообщения на экран
        //SharedPreferences sp = getSharedPreferences("settings", Context.MODE_PRIVATE);

        //String userScore = "123";
      //  userScore = sp.getString("hardMode", userScore);
       // Toast.makeText(Settings.this,"Ваш рекорд: " + userScore,Toast.LENGTH_LONG).show();

    }
    @Override
    protected void onPause() {
//save Settings

        //saveSettings();
        super.onPause();
    }

}