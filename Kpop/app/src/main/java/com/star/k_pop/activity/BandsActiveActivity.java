package com.star.k_pop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.star.k_pop.R;
import com.star.k_pop.StartApplication.Importer;
import com.star.k_pop.helper.Theme;

import java.util.ArrayList;

public class BandsActiveActivity extends AppCompatActivity {

    private ArrayList<SwitchCompat> nameBandsActive;
    Theme theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme = new Theme(this);
        theme.setThemeSecond();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bands_active);

        TextView textView = findViewById(R.id.textTitleBandsActive);
        textView.setTextColor(theme.getTextColor());

        LinearLayout container = findViewById(R.id.containerSwitchBandsActive);

        nameBandsActive = new ArrayList<>();

        for(int i = 0; i< Importer.getSizeNameActive(); i++){
            SwitchCompat nameBand = new SwitchCompat(this);
            nameBand.setTextColor(theme.getTextColor());

            TableRow.LayoutParams lp = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(80, 0, 80, 80);

            nameBand.setLayoutParams(lp);
            nameBand.setTextSize(24);
            nameBand.setText(Importer.getNameActiveText(i));
            nameBand.setChecked(Importer.isGetNameActive(i));

            nameBand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    CheckedMinBands();
                }
            });

            nameBandsActive.add(nameBand);
            container.addView(nameBandsActive.get(i));
        }

        Button buttonCancelActive = findViewById(R.id.buttonCancelActive);
        buttonCancelActive.setBackgroundResource(theme.getBackgroundButton());

        buttonCancelActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button buttonSaveActive = findViewById(R.id.buttonSaveActive);
        buttonSaveActive.setBackgroundResource(theme.getBackgroundButton());

        buttonSaveActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> bandsActive = new ArrayList<>();
                for(SwitchCompat name : nameBandsActive){
                    if (!name.isChecked()){
                        bandsActive.add(name.getText().toString());
                    }
                }
                Importer.SaveBandsActive(BandsActiveActivity.this, bandsActive);
                finish();
            }
        });
        CheckedMinBands();
    }

    private void CheckedMinBands(){
        int countChecked = 0;
        for(SwitchCompat name : nameBandsActive){
            if (name.isChecked()){
                countChecked++;
            }
        }
        if (countChecked <= 8){
            for(SwitchCompat name : nameBandsActive){
                if (name.isChecked()){
                    name.setClickable(false);
                    name.setAlpha(0.5f);
                }
            }
        }else{
            for(SwitchCompat name : nameBandsActive){
                name.setClickable(true);
                name.setAlpha(1f);
            }
        }
    }
}