package com.star.k_pop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.star.k_pop.R;
import com.star.k_pop.helper.Storage;
import com.star.k_pop.helper.Theme;


public class Achievements extends AppCompatActivity {

    Theme theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        theme = new Theme(this);
        theme.setTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);


        final ImageView ach1 = findViewById(R.id.ach1);
        final ImageView ach2 = findViewById(R.id.ach2);
        final ImageView ach3 = findViewById(R.id.ach3);
        final ImageView ach4 = findViewById(R.id.ach4);
        final ImageView ach5 = findViewById(R.id.ach5);
        final ImageView ach6 = findViewById(R.id.ach6);
        final ImageView ach7 = findViewById(R.id.ach7);
        final ImageView ach8 = findViewById(R.id.ach8);
        final ImageView ach9 = findViewById(R.id.ach9);
        final ImageView ach10 = findViewById(R.id.ach10);
        final ImageView ach11 = findViewById(R.id.ach11);

        Storage storage = new Storage(this, "appStatus");

        if (storage.getBoolean("achGuessStarNormal")) //почему-то R.drawable.achievement слишком большая
            ach1.setImageResource(R.drawable.normalgs);
        if (storage.getBoolean("achGuessStarExpert"))
            ach2.setImageResource(R.drawable.kpoplove);
        if (storage.getBoolean("achGuessBandsNormal"))
            ach3.setImageResource(R.drawable.normalgb);
        if (storage.getBoolean("achGuessBandsExpert"))
            ach4.setImageResource(R.drawable.kpoplove);
        if (storage.getBoolean("achSwipeTwoBandsNormal"))
            ach5.setImageResource(R.drawable.normaldb);
        if (storage.getBoolean("achSwipeTwoBandsExpert"))
            ach6.setImageResource(R.drawable.kpoplove);
        if (storage.getBoolean("achGuessStarReversNormal"))
            ach7.setImageResource(R.drawable.kpoplove);
        if (storage.getBoolean("achGuessStarReversExpert"))
            ach8.setImageResource(R.drawable.kpoplove);
        if (storage.getBoolean("achSecretGameMode"))
            ach9.setImageResource(R.drawable.kpoplove);
        if (storage.getBoolean("achAdsFree"))
            ach10.setImageResource(R.drawable.kpoplove);
        if (storage.getBoolean("achRoyal"))
            ach11.setImageResource(R.drawable.kpoplove);


        //SomeMethods.showToast(this, "Достижение открыто!", R.drawable.achievement);

        Button tempButton = findViewById(R.id.button2);//временная штука, потом будем считывать из Хранилища состояния ачивокъ
        if (theme.isDarkMode()) {
            tempButton.setBackgroundResource(R.drawable.stylebutton_dark);
        } else {
            tempButton.setBackgroundResource(R.drawable.stylebutton);
        }
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SomeMethods.showToast(Achievements.this, "Достижение открыто - КУБЫ!", R.drawable.cubes);

             /*  ImageView imageView = new ImageView(Achievements.this);
                ConstraintLayout constraintLayout = findViewById(R.id.constraint);
                constraintLayout.addView(imageView);
                Glide.with(Achievements.this).load(getResources().getDrawable(R.drawable.hello))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transition(withCrossFade())
                        .into(imageView);
                Achievements.Alert alert = new Achievements.Alert();
                alert.execute();*/
                //SomeMethods.showAlertDeialog(Achievements.this,"title?","1 or 2","1","2");
                //потом надо будет вместо кнопки сделать функцию обновления ачивок. Если в хранилище будет ачивка True -> открыть картинку. КОнечно же надо будет сначала ачивки сохранить в Хранилище....
                ach1.setImageResource(R.drawable.heart);
                ach2.setImageResource(R.drawable.energy);
                ach3.setImageResource(R.drawable.cubes);
                ach4.setImageResource(R.drawable.star);
            }
        });
    }
}