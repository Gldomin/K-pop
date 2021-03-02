package com.star.k_pop.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.star.k_pop.R;
import com.star.k_pop.helper.OptionsSet;
import com.star.k_pop.helper.Storage;
import com.star.k_pop.helper.Theme;

public class WhoInKpop extends AppCompatActivity {
    int druzelubie, krasota, inteligence, kindness, style;
    int number;
    Theme theme;
    RadioGroup radGr;
    RadioButton radY, radN;
    Button confirmButton;
    TextView textQuestion;

protected void OnCreate(Bundle savedInstanceState){
    theme = new Theme(this);

    theme.setThemeSecond();
    radGr = findViewById(R.id.checkGr);
    radY = findViewById(R.id.radioYes);
    radN = findViewById(R.id.radioNo);
    confirmButton = findViewById(R.id.buttonConfirm);
    textQuestion = findViewById(R.id.textQuestion);



}




    private void  countingPoints(int num,boolean b) {
        switch (num){
            case 1:
                if(b)druzelubie++;
                else druzelubie--;
                break;
            case 2:
                if(b)krasota++;
                else krasota--;
                break;
            case 3:
                if(b)inteligence++;
                else inteligence--;
                break;
            case 4:
                if(b)kindness++;
                else kindness--;
                break;
            case 5:
                if(b)style++;
                else style--;
                break;
        }
    }

}
