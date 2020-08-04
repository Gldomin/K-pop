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
        setContentView(R.layout.main);


        Button button = findViewById(R.id.guessStar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, GuessStar.class);
                startActivity(image);
            }
        });
        Button buttonBand = findViewById(R.id.guessGroup);
        buttonBand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass( MainActivity.this, GuessBands.class );
                startActivity(image);
            }
        });
        Button buttonLibrary = findViewById(R.id.galery);
        buttonLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(MainActivity.this, com.exam.k_pop.activity.MainActivity.class);
                startActivity(image);
            }
        });
    }

}