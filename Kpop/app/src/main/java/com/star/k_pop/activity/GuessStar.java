package com.star.k_pop.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.star.k_pop.R;
import com.star.k_pop.StartApplication.Importer;
import com.star.k_pop.helper.Rewarded;
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
    boolean onRewarded = true;      // Просмотр рекламы 1 раз
    boolean showReward = false;     // Просмотрена реклама до конца или нет
    boolean endGame = false;

    boolean cheatOn = false;//Режим читера // TODO Удалить перед релизом

    Theme theme; //переменная для считывания состояния свиича на darkmod

    Rewarded rewarded;          //Класс для работы с рекламой
    HeathBar heathBarTest;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        theme = new Theme(this);
        theme.setThemeSecond();

        rewarded = new Rewarded(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_star);

        textRecord = findViewById(R.id.scoreText2);
        imageView = findViewById(R.id.imageView);
        textScore = findViewById(R.id.scoreText);
        ImageButton about = findViewById(R.id.guessStarAbautButton);
        cheaterButton = findViewById(R.id.cheaterButton); // TODO Удалить перед релизом

        artists = Importer.getRandomArtists();

        about.setBackgroundResource(theme.getBackgroundResource());

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

            buttons[i].setBackgroundResource(theme.getBackgroundResource());
            buttons[i].setTextColor(theme.getTextColor());
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
                        if (scoreNow == 50) //ачивка за 50 - achGuessStarNormalText. Условие ачивки
                        {
                            SomeMethods.achievementGetted(GuessStar.this, R.string.achGuessStarNormal, R.drawable.normalgs, "achGuessStarNormal"); //ачивочка
                        }
                        // TODO Конец части

                        // TODO Удалить перед релизом
                        for (int i = 0; i < 4; i++) {
                            buttons[i].setTextColor(theme.getTextColor());
                        }
                        // TODO Конец удаления
                        YandexMetrica.reportEvent("GuessStarRightClick");
                        nextArtist();
                    } else {
                        heathBarTest.blow(); //снижение хп
                        YandexMetrica.reportEvent("GuessStarLoseClick");  //метрика на неправильный клик
                        if (heathBarTest.getHp() == 0 && !endGame) {  //обнуление игры в случае проеба
                            startLosingDialog();
                        }
                    }
                    updateScore();
                }
            });
            tableRow.addView(buttons[i]);
        }

        ImageView heart1 = findViewById(R.id.guessStarHeart1); //toDo тест хп
        ImageView heart2 = findViewById(R.id.guessStarHeart2);
        ImageView heart3 = findViewById(R.id.guessStarHeart3);
        final ArrayList<ImageView> imageViewList = new ArrayList<ImageView>();
        imageViewList.add(heart1);
        imageViewList.add(heart2);
        imageViewList.add(heart3);
        heathBarTest = new HeathBar(imageViewList, 3);

        // TODO Удалить перед релизом
        if (theme.isDarkMode()) {
            cheaterButton.setBackgroundResource(R.drawable.stylebutton_dark);
            cheaterButton.setTextColor(getResources().getColor(R.color.colorText));
        } else if (theme.isLightRabbit()) {
            cheaterButton.setBackgroundResource(R.drawable.stylebutton);
            cheaterButton.setTextColor(getResources().getColor(R.color.colorTextRabbit));
        } else if (theme.isLightCat()) {
            cheaterButton.setBackgroundResource(R.drawable.stylebutton_cat);
            cheaterButton.setTextColor(getResources().getColor(R.color.colorTextCat));
        } else if (theme.isLightHamster()) {
            cheaterButton.setBackgroundResource(R.drawable.stylebutton_hamster);
            cheaterButton.setTextColor(getResources().getColor(R.color.colorTextHamster));
        }

        cheaterButton.setOnClickListener(new View.OnClickListener() { //читерская кнопка для быстрого тестирования
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                for (int i = 0; i < 4; i++) {
                    if (theme.isDarkMode()) {
                        buttons[i].setTextColor(getResources().getColor(R.color.colorText));
                    } else {
                        buttons[i].setTextColor(getResources().getColor(R.color.colorTextRabbit));
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
                                buttons[i].setTextColor(getResources().getColor(R.color.colorTextRabbit));
                            }
                        }
                    }
                    Toast.makeText(GuessStar.this, "Читы активированы!", Toast.LENGTH_LONG).show(); //отправка сообщения на экран
                } else {
                    if (theme.isDarkMode()) {
                        buttons[chosenOne].setTextColor(getResources().getColor(R.color.colorText));
                    } else {
                        buttons[chosenOne].setTextColor(getResources().getColor(R.color.colorTextRabbit));
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

    @SuppressLint("DefaultLocale")
    private void startLosingDialog() {
        endGame = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
        builder.setTitle(getResources().getString(R.string.endGameCongratulate))
                .setMessage(String.format("%s %d! %s", getResources().getString(R.string.score_text), scoreNow, getResources().getString(R.string.endGameNewGame)))
                .setCancelable(false)
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        endGame = false;
                        heathBarTest.setHp(3);
                        scoreNow = 0;
                        count++;
                        if (count >= artists.size() - 1) {
                            artists = Importer.getRandomArtists();
                            count = 0;
                        }
                        onRewarded = true;
                        nextArtist();
                        updateScore();
                    }
                });
        if (rewarded.onLoaded() && onRewarded) {
            builder.setMessage(String.format("%s %d! %s %s", getResources().getString(R.string.score_text),
                    scoreNow, getResources().getString(R.string.endGameNewGame), getResources().getString(R.string.endGameReward)))
                    .setNeutralButton(getResources().getString(R.string.endGameRewardShow), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            endGame = false;
                            rewarded.show(GuessStar.this, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    onRewarded = false;
                                    showReward = true;
                                }
                            }, new Rewarded.RewardDelay() {
                                @Override
                                public void onShowDismissed() {
                                    if (showReward) {
                                        heathBarTest.restore();
                                    } else {
                                        heathBarTest.setHp(3);
                                        scoreNow = 0;
                                        count++;
                                        if (count >= artists.size() - 1) {
                                            artists = Importer.getRandomArtists();
                                            count = 0;
                                        }
                                        onRewarded = true;
                                        nextArtist();
                                        updateScore();
                                    }
                                    showReward = false;
                                }
                            });
                        }
                    });
        }
        AlertDialog alert = builder.create();
        alert.show();
    }
}