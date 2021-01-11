package com.star.k_pop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import com.star.k_pop.StartApplication.Importer;
import com.yandex.metrica.YandexMetrica;

import java.util.ArrayList;
import java.util.Random;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class GuessStar extends AppCompatActivity {
    boolean cheatOn = false;
    Button cheaterButton;
    ImageView imageView;
    String[] stars;
    ArrayList<Artist> artists = new ArrayList<>();
    Button[] buttons = new Button[4];
    int chosenOne = -1;     //избранный артист (правильный артист)
    int scoreNow = 0;       //текущий счет
    int count = 0;          //номер артиста из сгенерированного списка (текущий)

    OptionsSet tempSettingsSet = new OptionsSet(false, false); //переменная для считывания состояния свиича на darkmod
    String buttonStyleChange = "stylebutton";
    SharedPreferences sp;

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

        sp = getSharedPreferences("settings", Context.MODE_PRIVATE);

        String nameOfStorage = "settings";
        Storage storage = new Storage(this);
        tempSettingsSet.darkMode = storage.getBoolean(nameOfStorage, "darkMode"); //считываем состояние
        //теперь выбираем тему в зависимости от положения свича
        if (tempSettingsSet.darkMode==true) setTheme(R.style.AppTheme2);
        else setTheme(R.style.AppThemeLight);

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
            if (tempSettingsSet.darkMode==true)
                buttons[i].setBackgroundResource(R.drawable.stylebutton_dark);
            else
                buttons[i].setBackgroundResource(R.drawable.stylebutton);

            buttons[i].setPadding(10, 10, 10, 10);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            lp.setMargins(10, 10, 10, 10);
            buttons[i].setLayoutParams(lp);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {

                    if (((Button) view).getText().equals(artists.get(count).getName())) {
                        //Сбос значений для проверки записан ли артист на кнопку
                        for (Artist a : artists) {
                            a.setInit(false);
                        }
                        for (int i = 0; i < 4; i++) {
                            //buttons[i].setTextColor(Color.BLACK); //Чит на правильный ответ
                            if (tempSettingsSet.darkMode==true) buttons[i].setTextColor(getResources().getColor(R.color.colorText));
                                else buttons[i].setTextColor(getResources().getColor(R.color.colorTextLight));

                        }
                        scoreNow++;
                        YandexMetrica.reportEvent("GuessStarRightClick"); //метрика на правильный клик
                        /*switch (scoreNow) {
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
                        }*/
                        count++;
                        if (count >= artists.size() - 1)      //обработка конца списка. Что бы играть можно было вечно
                        {
                            artists = Importer.getRandomArtists();
                            count = 0;
                        }
                        init();
                    } else if (scoreNow > 0) {
                        scoreNow--;
                        YandexMetrica.reportEvent("GuessStarLoseClick");  //метрика на неправильный клик
                    }
                    TextView textView = findViewById(R.id.scoreText);
                    updateScore(textView);
                }
            });
            tableRow.addView(buttons[i]);
            imageView.setOnClickListener(new View.OnClickListener() { //включение/выключение читов при нажатии на фотку
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    cheatOn = !cheatOn;
                    if (cheatOn) {
                        for (int i = 0; i < 4; i++)
                            if (i == chosenOne)
                                buttons[i].setTextColor(Color.RED);
                            else { if (tempSettingsSet.darkMode==true){
                                 buttons[i].setTextColor(getResources().getColor(R.color.colorText));}
                                else buttons[i].setTextColor(getResources().getColor(R.color.colorTextLight));
                            }
                        Toast.makeText(GuessStar.this, "Читы активированы!", Toast.LENGTH_LONG).show(); //отправка сообщения на экран
                    } else
                        Toast.makeText(GuessStar.this, "Читы деактивированы!", Toast.LENGTH_LONG).show(); //отправка сообщения на экран
                }
            });

            cheaterButton = findViewById(R.id.cheaterButton);
            cheaterButton.setOnClickListener(new View.OnClickListener() { //читерская кнопка для быстрого тестирования
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    for (Artist a : artists) {
                        a.setInit(false);
                    }
                    for (int i = 0; i < 4; i++) {
                       // buttons[i].setTextColor(Color.BLACK); //Чит на правильный ответ
                        if (tempSettingsSet.darkMode==true){
                            buttons[i].setTextColor(getResources().getColor(R.color.colorText));
                        }  buttons[i].setTextColor(getResources().getColor(R.color.colorTextLight));
                    }
                   //количетво скипнутых артистов
                      count++;
                      scoreNow++;
                      if (count >= artists.size() - 1)      //обработка конца списка. Что бы играть можно было вечно
                      {
                          artists = Importer.getRandomArtists();
                          count = 0;
                      }


                    init();

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