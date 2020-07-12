package com.example.k_pop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;

import java.io.IOException;
import java.util.Random;

public class GuessStar extends AppCompatActivity {

    ImageView imageView;
    String[] stars;
    Button[] buttons = new Button[4];
    int chosenOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_star);
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
                        init();
                    }
                }
            });
            tableRow.addView(buttons[i]);
        }
        init();
    }

    private void init() {
        AssetManager assetManager = getAssets();
        stars = new String[4];
        try {
            String[] str = assetManager.list("BTS");
            for (int i = 0; i < 4; i++) {
                int rand = new Random().nextInt(str.length);
                while (str[rand].equals("")) {
                    rand = new Random().nextInt(str.length);
                }
                stars[i] = str[rand];
                buttons[i].setText(stars[i]);
                str[rand] = "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            chosenOne = new Random().nextInt(4);
            Drawable drawable = Drawable.createFromStream(assetManager.open("BTS/" + stars[chosenOne]), "123");
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}