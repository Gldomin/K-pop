package com.star.k_pop.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
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
import com.star.k_pop.R;
import com.star.k_pop.StartApplication.Importer;
import com.star.k_pop.helper.Theme;
import com.star.k_pop.lib.HeathBar;
import com.star.k_pop.lib.SomeMethods;
import com.star.k_pop.model.Artist;
import com.yandex.metrica.YandexMetrica;

import java.util.ArrayList;
import java.util.Random;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GuessStar extends AppCompatActivity {


    Button cheaterButton; // TODO Удалить перед релизом
    Button[] buttons = new Button[4];
    ImageView imageView;
    TextView textScore;
    TextView textRecord;

    ArrayList<Artist> artists = new ArrayList<>();

    int chosenOne = -1;     //избранный артист (правильный артист)
    int scoreNow = 0;       //текущий счет
    int record = 0;         //рекорд
    int count = 0;          //номер артиста из сгенерированного списка (текущий)
    boolean cheatOn = false;//Режим читера // TODO Удалить перед релизом

    Theme theme; //переменная для считывания состояния свиича на darkmod
    HeathBar heathBarTest;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        theme = new Theme(this);
        theme.setTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_star);

        textRecord = findViewById(R.id.scoreText2);
        imageView = findViewById(R.id.imageView);
        textScore = findViewById(R.id.scoreText);
        cheaterButton = findViewById(R.id.cheaterButton); // TODO Удалить перед релизом

        artists = Importer.getRandomArtists();

        SharedPreferences sp = getSharedPreferences("UserScore", Context.MODE_PRIVATE);
        if (sp.contains("userScoreGuessStar")) {
            record = 0;
            record = sp.getInt("userScoreGuessStar", record);
            textRecord.setText(String.format("%s %d",
                    getResources().getString(R.string.record_text), record));
        } else {
            textRecord.setText(String.format("%s %d",
                    getResources().getString(R.string.record_text), 0));
        }

        if (savedInstanceState != null)
            scoreNow = savedInstanceState.getInt("scoreNow");

        for (int i = 0; i < 4; i++) {
            buttons[i] = new Button(this);
            TableRow tableRow;
            if (i / 2 == 0) {
                tableRow = findViewById(R.id.row1);
            } else {
                tableRow = findViewById(R.id.row2);
            }

            if (theme.isDarkMode()) {
                buttons[i].setBackgroundResource(R.drawable.stylebutton_dark);
                buttons[i].setTextColor(getResources().getColor(R.color.colorText));
            } else {
                buttons[i].setBackgroundResource(R.drawable.stylebutton);
                buttons[i].setTextColor(getResources().getColor(R.color.colorTextLight));
            }

            TableRow.LayoutParams lp = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            lp.setMargins(10, 10, 10, 10);

            buttons[i].setPadding(10, 10, 10, 10);
            buttons[i].setLayoutParams(lp);

            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((Button) view).getText().equals(artists.get(count).getName())) {
                        count++;
                        scoreNow++;
                        if (count >= artists.size() - 1)      //обработка конца списка. Что бы играть можно было вечно
                        {
                            artists = Importer.getRandomArtists();
                            count = 0;
                        }

                        // TODO Ваня доделай эту часть
                        if (scoreNow == 50) { //ачивка за 50 - achGuessStarNormalText. Условие ачивки
                            SomeMethods.achievementGetted(GuessStar.this, R.string.achGuessStarNormal, R.drawable.kpoplove, "achGuessStarNormal"); //ачивочка
                            /*Storage storage = new Storage(GuessStar.this); //блок кодя для получения, уведомления и записи ачивок
                            String nameOfStorage = "appStatus"; String nameOfAchievement = "achGuessStarNormal";
                            if (!storage.getBoolean(nameOfStorage, "achGuessStarNormalText"))
                                SomeMethods.showAchievementToast(GuessStar.this, getResources().getString(R.string.achievementUnlocked), getResources().getString(R.string.achGuessStarNormal), R.drawable.achievement);
                            storage.saveValue(nameOfStorage,nameOfAchievement,true);*/
                        }
                        // TODO Конец части

                        // TODO Удалить перед релизом
                        for (int i = 0; i < 4; i++) {
                            if (theme.isDarkMode()) {
                                buttons[i].setTextColor(getResources().getColor(R.color.colorText));
                            } else {
                                buttons[i].setTextColor(getResources().getColor(R.color.colorTextLight));
                            }
                        }
                        // TODO Конец удаления
                        YandexMetrica.reportEvent("GuessStarRightClick");
                        nextArtist();
                    } else {
                        heathBarTest.blow(); //снижение хп
                        YandexMetrica.reportEvent("GuessStarLoseClick");  //метрика на неправильный клик
                        if (heathBarTest.getHp() == 0) {  //обнуление игры в случае проеба
                            SomeMethods.showAchievementToast(GuessStar.this, "Поздравляем!", "Вы набрали " + scoreNow + " очков! Начинаем новую игру!", R.drawable.achievement);
                            heathBarTest.setHp(3);
                            scoreNow = 0;
                            nextArtist();
                        }
                    }
                    updateScore();
                }
            });
            tableRow.addView(buttons[i]);
        }

        // TODO Удалить перед релизом
        if (theme.isDarkMode()) {
            cheaterButton.setBackgroundResource(R.drawable.stylebutton_dark);
            cheaterButton.setTextColor(getResources().getColor(R.color.colorText));
        } else {
            cheaterButton.setBackgroundResource(R.drawable.stylebutton);
            cheaterButton.setTextColor(getResources().getColor(R.color.colorTextLight));
        }

        ImageView heart1 = findViewById(R.id.guessStarHeart1); //toDo тест хп
        ImageView heart2 = findViewById(R.id.guessStarHeart2);
        ImageView heart3 = findViewById(R.id.guessStarHeart3);
        final ArrayList<ImageView> imageViewList = new ArrayList<ImageView>();
        imageViewList.add(heart1);
        imageViewList.add(heart2);
        imageViewList.add(heart3);
        heathBarTest = new HeathBar(imageViewList, 3);


        cheaterButton.setOnClickListener(new View.OnClickListener() { //читерская кнопка для быстрого тестирования
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                for (int i = 0; i < 4; i++) {
                    if (theme.isDarkMode()) {
                        buttons[i].setTextColor(getResources().getColor(R.color.colorText));
                    } else {
                        buttons[i].setTextColor(getResources().getColor(R.color.colorTextLight));
                    }
                }
                //количетво скипнутых артистов
                count++;
                scoreNow++;
                if (count >= artists.size() - 1)      //обработка конца списка. Что бы играть можно было вечно
                {
                    artists = Importer.getRandomArtists();
                    count = 0;
                }
                updateScore();
                nextArtist();
                
                heathBarTest.restore();

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() { //включение/выключение читов при нажатии на фотку
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {




                cheatOn = !cheatOn;
                if (cheatOn) {
                    for (int i = 0; i < 4; i++) {
                        if (i == chosenOne)
                            buttons[i].setTextColor(Color.RED);
                        else {
                            if (theme.isDarkMode()) {
                                buttons[i].setTextColor(getResources().getColor(R.color.colorText));
                            } else {
                                buttons[i].setTextColor(getResources().getColor(R.color.colorTextLight));
                            }
                        }
                    }
                    Toast.makeText(GuessStar.this, "Читы активированы!", Toast.LENGTH_LONG).show(); //отправка сообщения на экран
                } else {
                    if (theme.isDarkMode()) {
                        buttons[chosenOne].setTextColor(getResources().getColor(R.color.colorText));
                    } else {
                        buttons[chosenOne].setTextColor(getResources().getColor(R.color.colorTextLight));
                    }
                    Toast.makeText(GuessStar.this, "Читы деактивированы!", Toast.LENGTH_LONG).show(); //отправка сообщения на экран
                }
            }
        });
        // TODO Конец удаления
        updateScore();
        nextArtist();
    }

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
    void updateScore() {
        textScore.setText(String.format("%s %d",
                getResources().getString(R.string.score_text), scoreNow));
        if (scoreNow > record) {
            textRecord.setText(String.format("%s %d",
                    getResources().getString(R.string.record_text), scoreNow));
        }
    }

    void nextArtist() {
        chosenOne = new Random().nextInt(4);
        boolean sex = artists.get(count).isSex();
        for (int i = 0; i < 4; i++) {
            int rand;
            if (i == chosenOne) {
                // TODO Удалить перед релизом
                if (cheatOn) {
                    buttons[i].setTextColor(Color.RED);
                }
                // TODO Конец удаления
                rand = count;
            } else {
                rand = new Random().nextInt(artists.size()); //выбор артиста из пула артистов
                while (artists.get(rand).isInit() || artists.get(rand).isSex() != sex) {  //перевыбор артиста из пула артистов
                    rand = new Random().nextInt(artists.size());
                }
            }
            buttons[i].setText(artists.get(rand).getName());
            artists.get(rand).Init();
        }
        for (Artist a : artists) {
            a.setInit(false);
        }
        Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + artists.get(count).getFolder()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(imageView);
    }

}