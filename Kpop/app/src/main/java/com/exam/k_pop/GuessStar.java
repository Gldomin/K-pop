package com.exam.k_pop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.exam.k_pop.StartApplication.Importer;
import com.yandex.metrica.YandexMetrica;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class GuessStar extends AppCompatActivity {
    boolean cheatOn = false;

    ImageView imageView;
    String[] stars;
    ArrayList<Artist> artists = new ArrayList<>();
    Button[] buttons = new Button[4];
    int chosenOne = -1;
    int scoreNow = 0;
    int count = 0;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("scoreNow", scoreNow);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        SharedPreferences sp = getSharedPreferences("UserScore", Context.MODE_PRIVATE); //сохранение Счета
        if (sp.getInt("userScoreGuessStar", -1) < scoreNow) {
            SharedPreferences.Editor e = sp.edit();
            e.putInt("userScoreGuessStar", scoreNow);
            e.apply();
        }
        super.onPause();
    }

    @SuppressLint("DefaultLocale")
    void updateScore(TextView text) {
        text.setText("Ваш счет: " + scoreNow);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_star);

        ///////UserScore//////
        TextView textUserScore = findViewById(R.id.scoreText2);
        SharedPreferences sp = getSharedPreferences("UserScore", Context.MODE_PRIVATE);
        if (sp.contains("userScoreGuessStar")) {
            int userScore = -1;
            userScore = sp.getInt("userScoreGuessStar", userScore);
            textUserScore.setText("Ваш рекорд: " + userScore);
        } else textUserScore.setText("Ваш рекорд: " + 0);
        if (savedInstanceState != null)
            scoreNow = savedInstanceState.getInt("scoreNow");
        ///////////////////


        imageView = findViewById(R.id.imageView);

        String str = getResources().getString(R.string.app_name);


        for (int i = 0; i < 4; i++) {
            buttons[i] = new Button(this);
            TableRow tableRow;
            if (i / 2 == 0) {
                tableRow = findViewById(R.id.row1);
            } else {
                tableRow = findViewById(R.id.row2);
            }
            buttons[i].setBackgroundResource(R.drawable.stylebutton);
            buttons[i].setPadding(10, 10, 10, 10);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 10, 10, 10);
            buttons[i].setLayoutParams(lp);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    YandexMetrica.reportEvent("XXnjj");
                    if (((Button) view).getText().equals(artists.get(count).getName())) {
                        //Сбос значений для проверки записан ли артист на кнопку
                        for (Artist a : artists) {
                            a.setInit(false);
                        }
                        for (int i = 0; i < 4; i++) {
                            buttons[i].setTextColor(Color.BLACK); //Чит на правильный ответ
                        }
                        scoreNow++;
                        switch (scoreNow) {
                            case 10:
                                Toast.makeText(GuessStar.this, "Поздравляем, Вы - адепт K-pop", Toast.LENGTH_LONG).show(); //отправка сообщения на экран
                                break;
                            case 20:
                                Toast.makeText(GuessStar.this, "Да вы знаток K-pop!", Toast.LENGTH_LONG).show(); //отправка сообщения на экран
                                break;
                            case 30:
                                Toast.makeText(GuessStar.this, "Вау, вы просто эксперт K-pop!", Toast.LENGTH_LONG).show(); //отправка сообщения на экран
                                break;
                            case 100:
                                Toast.makeText(GuessStar.this, "Бро, да ты просто бешеный! Нет, я серьезно. Таких фанатов K-pop еще надо поискать!", Toast.LENGTH_LONG).show(); //отправка сообщения на экран
                        }
                        count++;
                        init();
                    } else if (scoreNow > 0) scoreNow--;
                    TextView textView = findViewById(R.id.scoreText);
                    updateScore(textView);
                }
            });
            tableRow.addView(buttons[i]);
            imageView.setOnClickListener(new View.OnClickListener() { //включение/выключение читов при нажатии на фотку
                @Override
                public void onClick(View view) {
                    cheatOn = !cheatOn;
                    if (cheatOn) {
                        for (int i = 0; i < 4; i++)
                            if (i == chosenOne)
                                buttons[i].setTextColor(Color.RED); //TODO при активации читов неправильно показывает ПЕРВОГО артиста. дальше норм. надо исправить
                            else buttons[i].setTextColor(Color.BLACK);
                        Toast.makeText(GuessStar.this, "Читы активированы!", Toast.LENGTH_LONG).show(); //отправка сообщения на экран
                    } else
                        Toast.makeText(GuessStar.this, "Читы деактивированы!", Toast.LENGTH_LONG).show(); //отправка сообщения на экран
                }
            });


        }
        artists = Importer.getRandomArtists();
        init();
    }



    /**
     * Метод для загрузки текста на кнопки и вывода на экран артиста
     */
    private void init() {
        TextView textView = findViewById(R.id.scoreText);
        updateScore(textView);
        chosenOne = new Random().nextInt(4);
        stars = new String[4];
        boolean sex = artists.get(count).isSex();
        for (int i = 0; i < 4; i++) {
            int rand;
            if (i == chosenOne) {
                if (cheatOn)
                    buttons[i].setTextColor(Color.RED); //Чит на правильный ответ
                rand = count;
            } else {
                rand = new Random().nextInt(artists.size()); //выбор артиста из пула артистов
                while (artists.get(rand).isInit() || artists.get(rand).isSex() != sex) {  //перевыбор артиста из пула артистов
                    rand = new Random().nextInt(artists.size());
                }
            }
            stars[i] = artists.get(rand).getName();
            buttons[i].setText(stars[i]);
            artists.get(rand).Init();
        }
        //Работа библиотеки Glide с изображением
        Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + artists.get(count).getFolder()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(imageView);
    }
}