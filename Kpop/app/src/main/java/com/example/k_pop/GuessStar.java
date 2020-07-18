package com.example.k_pop;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.util.Random;

public class GuessStar extends AppCompatActivity {

    ImageView imageView;
    String[] stars;
    Button[] buttons = new Button[4];
    int chosenOne;
    int scoreNow = 0;
    String StarName;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("scoreNow", scoreNow);
        super.onSaveInstanceState(outState);
    }

    @SuppressLint("DefaultLocale")
    void updateScore(TextView text) {
        text.setText("Ваш счет: " + scoreNow);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_star);

        if (savedInstanceState != null)
            scoreNow = savedInstanceState.getInt("scoreNow");

        imageView = findViewById(R.id.imageView);
        //TextView textView = findViewById(R.id.textView);
        String str = getResources().getString(R.string.app_name);
        //textView.setText(str);

        for (int i = 0; i < 4; i++) {
            buttons[i] = new Button(this);
            TableRow tableRow;
            if (i / 2 == 0) {
                tableRow = findViewById(R.id.row1);
            } else {
                tableRow = findViewById(R.id.row2);
            }
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((Button) view).getText().equals(stars[chosenOne])) {
                        scoreNow++;
                        switch (scoreNow) {
                            case 10:
                                Toast.makeText(GuessStar.this, "Поздравляем, Вы - адепт BTS", Toast.LENGTH_LONG).show(); //отправка сообщения на экран
                                break;
                            case 20:
                                Toast.makeText(GuessStar.this, "Да вы знаток BTS!", Toast.LENGTH_LONG).show(); //отправка сообщения на экран
                                break;
                            case 30:
                                Toast.makeText(GuessStar.this, "Вау, вы просто эксперт BTS!", Toast.LENGTH_LONG).show(); //отправка сообщения на экран
                        }
                        init();
                    } else if (scoreNow > 0) scoreNow--;
                    TextView textView = findViewById(R.id.scoreText);
                    updateScore(textView);
                }
            });
            tableRow.addView(buttons[i]);
        }
        // TODO hjdhfj
        init();
    }

    private void init() {
        TextView textView = findViewById(R.id.scoreText);
        updateScore(textView);
        AssetManager assetManager = getAssets();
        stars = new String[4];
        try {
            String[] str = assetManager.list("BTS");
            for (int i = 0; i < 4; i++) {
                int rand = new Random().nextInt(str.length);
                while (str[rand].equals("")) {
                    rand = new Random().nextInt(str.length);
                }
                //обрезание по точкам
                String[] s = str[rand].split(".png");
                stars[i] = s[0];
                buttons[i].setText(stars[i]);
                str[rand] = "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            chosenOne = new Random().nextInt(4);

            //Работа библиотеки Glide с изображением
            Glide.with(this).load(Uri.parse("file:///android_asset/BTS/" + stars[chosenOne] + ".png"))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(imageView);

            Drawable drawable = Drawable.createFromStream(assetManager.open("file:///android_asset/BTS/" + stars[chosenOne] + ".png"), "123");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}