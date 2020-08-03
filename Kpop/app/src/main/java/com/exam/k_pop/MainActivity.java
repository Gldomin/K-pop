package com.exam.k_pop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button guessStarButton = findViewById(R.id.guessStar);
        guessStarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, GuessStar.class);
                startActivity(image);
            }
        });
        Button guessBandButton = findViewById(R.id.guessBand);
        guessBandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass( MainActivity.this, GuessBands.class );
                startActivity(image);
            }
        });

        Button galleryButton = findViewById(R.id.gallery);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass( MainActivity.this, Gallery.class );
                startActivity(image);
            }
        });

    }

}