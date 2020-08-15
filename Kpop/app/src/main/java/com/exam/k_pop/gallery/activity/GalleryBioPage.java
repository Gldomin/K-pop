package com.exam.k_pop.gallery.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.exam.k_pop.OnSwipeTouchListener;
import com.exam.k_pop.R;

public class GalleryBioPage extends AppCompatActivity {

    public String readFile(String filePath) { //метод с Инета, читает файлы txt

        String jString = null;
        StringBuilder builder = new StringBuilder();

        File yourFile = new File("/sdcard/" + filePath);
        if (yourFile.exists()) {
            Log.i("file", "file founded");

            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(yourFile));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String row = "";

            try {
                while ((row = bufferedReader.readLine()) != null) {

                    builder.append(row + "\n");
                }

                bufferedReader.close();


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            jString = builder.toString();
        }

        else {

            Log.i("FAIL", "FILE NOT FOUND");
        }

        return jString;
    }








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_bio_page);


        /////////BioContent////////
      /*  TextView textUserScore = findViewById(R.id.scoreText2);
        SharedPreferences sp = getSharedPreferences("UserScore", Context.MODE_PRIVATE);
        if (sp.contains("userScoreGuessStar")) {
            int userScore = -1;
            userScore = sp.getInt("userScoreGuessStar", userScore);
            textUserScore.setText("Ваш рекорд: " + userScore);
        } else textUserScore.setText("Ваш рекорд: " + 0);
        if (savedInstanceState != null)
            scoreNow = savedInstanceState.getInt("scoreNow");*/
        ///////////////////////////




        ImageView tempImg = findViewById(R.id.tempImg);
        tempImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tempImg.setOnTouchListener(new OnSwipeTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onSwipeDown() {
                finish();
                overridePendingTransition(R.anim.alpha_on, R.anim.bottom_on);
                return true;
            }
        });


    }
}