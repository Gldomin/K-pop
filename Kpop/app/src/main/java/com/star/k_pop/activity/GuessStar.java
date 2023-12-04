package com.star.k_pop.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.star.k_pop.R;
import com.star.k_pop.StartApplication.Importer;
import com.star.k_pop.ad.InterstitialCustom;
import com.star.k_pop.ad.InterstitialCustomGoogle;
import com.star.k_pop.ad.InterstitialCustomYandex;
import com.star.k_pop.ad.RewardedCustom;
import com.star.k_pop.ad.RewardedCustomGoogle;
import com.star.k_pop.ad.RewardedCustomYandex;
import com.star.k_pop.helper.Storage;
import com.star.k_pop.helper.Theme;
import com.star.k_pop.lib.HeathBar;
import com.star.k_pop.lib.SomeMethods;
import com.star.k_pop.lib.SoundPlayer;
import com.star.k_pop.model.Artist;
import com.yandex.metrica.YandexMetrica;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GuessStar extends AppCompatActivity {

    Button[] buttons = new Button[4];
    ImageView imageView;
    TextView textScore;
    TextView textRecord;

    SoundPlayer soundPlayer = new SoundPlayer(this); //это объект для воспроизведения звуков
    boolean sound = false; //включен ли звук

    ArrayList<Artist> artists = new ArrayList<>();

    int chosenOne = -1;     //избранный артист (правильный артист)
    int scoreNow = 0;       //текущий счет
    int record = 0;         //рекорд
    int count = 0;          //номер артиста из сгенерированного списка (текущий)

    boolean onRewarded = true;      // Просмотр рекламы 1 раз
    boolean showReward = false;     // Просмотрена реклама до конца или нет
    boolean endGame = false;
    private Theme theme; //переменная для считывания состояния свиича на darkMod
    private RewardedCustom rewardedCustom;          //Класс для работы с рекламой
    private InterstitialCustom mInterstitialAd;

    private int countAd = 5;

    HeathBar heathBarTest;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme = new Theme(this);
        theme.setThemeSecond();

        if (Locale.getDefault().getLanguage().equals("ru")) {
            rewardedCustom = new RewardedCustomYandex(this);
            mInterstitialAd = new InterstitialCustomYandex(this, getResources().getString(R.string.yandex_id_interstitial_game));
        } else {
            rewardedCustom = new RewardedCustomGoogle(this, R.string.admob_id_reward_star);
            mInterstitialAd = new InterstitialCustomGoogle(this, R.string.admob_id_interstitial);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_star);

        final int pingClickID = soundPlayer.load(R.raw.ping_click); //id загруженного потока
        final int longSwitchID = soundPlayer.load(R.raw.long_switch);
        Storage storage = new Storage(this, "settings"); //хранилище для извлечения
        sound = storage.getBoolean("soundMode"); //настроек звука


        textRecord = findViewById(R.id.scoreText2);
        imageView = findViewById(R.id.imageView);
        textScore = findViewById(R.id.scoreText);
        textRecord.setTextColor(theme.getTextColor());
        textScore.setTextColor(theme.getTextColor());
        ImageButton about = findViewById(R.id.guessStarAbautButton);

        artists = Importer.getRandomArtists();
        if (artists.size() == 0){
            finish();
        }

        about.setBackgroundResource(theme.getBackgroundButton());

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

        if (savedInstanceState != null) {
            scoreNow = savedInstanceState.getInt("scoreNow");
            record = savedInstanceState.getInt("record");
        }

        for (int i = 0; i < 4; i++) {
            buttons[i] = new Button(this);
            TableRow tableRow;
            if (i / 2 == 0) {
                tableRow = findViewById(R.id.row1);
            } else {
                tableRow = findViewById(R.id.row2);
            }

            buttons[i].setBackgroundResource(theme.getBackgroundButton());
            buttons[i].setTextColor(theme.getButtonTextColor());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            lp.setMargins(10, 10, 10, 10);

            buttons[i].setPadding(10, 10, 10, 10);
            buttons[i].setLayoutParams(lp);
            buttons[i].setOnClickListener(view -> {
                if (((Button) view).getText().equals(artists.get(count).getName())) {
                    count++;
                    scoreNow++;
                    if (record < scoreNow) {
                        record++;
                    }
                    if (count >= artists.size() - 1)      //обработка конца списка. Что бы играть можно было вечно
                    {
                        artists = Importer.getRandomArtists();
                        count = 0;
                    }
                    if (scoreNow % 50 == 0) {
                        YandexMetrica.reportEvent("GuessStar", "{\"Score\":{\"Добавлено хп\"}}");
                        heathBarTest.restore();
                    }
                    if (scoreNow == 50) { //ачивка за 50 - achGuessStarNormalText. Условие ачивки
                        Storage storage1 = new Storage(getApplicationContext(), "appStatus");
                        if (!storage1.getBoolean("achGuessStarNormal")) {
                            YandexMetrica.reportEvent("Achievements", "{\"GuessStar\":{\"Ачивка 50 угаданных артистов\"}}");
                        }
                        SomeMethods.achievementGetted(GuessStar.this, R.string.achGuessStarNormal, R.drawable.normalgs, "achGuessStarNormal"); //ачивочка
                    }
                    if (scoreNow == 150) { //ачивка за 150 - achGuessStarNormalText. Условие ачивки
                        Storage storage1 = new Storage(getApplicationContext(), "appStatus");
                        if (!storage1.getBoolean("achGuessStarExpert")) {
                            YandexMetrica.reportEvent("Achievements", "{\"GuessStar\":{\"Ачивка 150 угаданных артистов\"}}");
                        }
                        SomeMethods.achievementGetted(GuessStar.this, R.string.achGuessStarExpert, R.drawable.expertgs, "achGuessStarExpert"); //ачивочка
                    }
                    nextArtist();

                    if (sound) {
                        soundPlayer.playSoundStream(longSwitchID);//звук правильного ответа
                    }
                } else {
                    view.setBackgroundResource(theme.getBackgroundButtonEnable());
                    view.setClickable(false);
                    heathBarTest.blow(); //снижение хп
                    if (heathBarTest.getHp() == 0 && !endGame) {  //обнуление игры в случае проеба
                        startLosingDialog();
                    }

                    if (sound) {
                        soundPlayer.playSoundStream(pingClickID);//звук неправильного ответа
                    }
                }
                updateScore();

            });
            tableRow.addView(buttons[i]);
        }

        createHeathBar();

        about.setOnClickListener(view -> {
            Intent image = new Intent();
            image.setClass(GuessStar.this, BasicNotice.class);
            image.putExtra("text", R.string.guessStarGameModeAbout);
            image.putExtra("title", R.string.gameModeAbout);
            startActivity(image);
            if (sound) {
                soundPlayer.playSoundStream(pingClickID);//звук кнопки
            }
        });
        updateScore();
        nextArtist();
    }

    private void createHeathBar() {
        ImageView imageView1 = findViewById(R.id.guessBandHeart1);
        ImageView imageView2 = findViewById(R.id.guessBandHeart2);
        ImageView imageView3 = findViewById(R.id.guessBandHeart3);
        ArrayList<ImageView> imageViewList = new ArrayList<>();
        imageViewList.add(imageView1);
        imageViewList.add(imageView2);
        imageViewList.add(imageView3);

        Animation lifeBrokeAnimation = AnimationUtils.loadAnimation(this, R.anim.heart_broke_animation);
        heathBarTest = new HeathBar(imageViewList, 3, lifeBrokeAnimation);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("scoreNow", scoreNow);
        outState.putInt("record", record);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        SharedPreferences sp = getSharedPreferences("UserScore", Context.MODE_PRIVATE); //сохранение Счета
        if (sp.getInt("userScoreGuessStar", -1) < record) {
            SharedPreferences.Editor e = sp.edit();
            e.putInt("userScoreGuessStar", record);
            e.apply();
        }
        super.onPause();
    }

    @SuppressLint("DefaultLocale")
    void updateScore() {
        textScore.setText(getResources().getString(R.string.score_text, scoreNow));
        textRecord.setText(getResources().getString(R.string.record_text, record));
    }

    void nextArtist() {
        chosenOne = new Random().nextInt(4);
        boolean sex = artists.get(count).isSex();

        Log.i("answer=", artists.get(count).getName()); //чит-лог
        for (int i = 0; i < 4; i++) {
            int rand;
            if (i == chosenOne) {
                rand = count;
            } else {
                rand = new Random().nextInt(artists.size()); //выбор артиста из пула артистов
                while (artists.get(rand).isInit() || artists.get(rand).isSex() != sex || rand == count) {  //перевыбор артиста из пула артистов
                    rand = new Random().nextInt(artists.size());
                }
            }
            buttons[i].setClickable(true);
            buttons[i].setBackgroundResource(theme.getBackgroundButton());
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
                .setMessage(String.format("%s! %s", getResources().getString(R.string.score_text, scoreNow), getResources().getString(R.string.endGameNewGame)))
                .setCancelable(false)
                .setNegativeButton(getResources().getString(R.string.endGameNo), (dialogInterface, i) -> {
                    YandexMetrica.reportEvent("GuessStar", "{\"Game over\":\"Выход из игры\"}");
                    String jsonValue = "{\"Score\":{\"Количество очков\":\"" + scoreNow + "\"}}";
                    YandexMetrica.reportEvent("GuessStar", jsonValue);
                    finish();
                })
                .setPositiveButton(getResources().getString(R.string.endGameYes), (dialogInterface, i) -> {
                    YandexMetrica.reportEvent("GuessStar", "{\"Game over\":\"Продолжить игру\"}");
                    String jsonValue = "{\"Game over\":{\"Количество очков\":\"" + scoreNow + "\"}}";
                    YandexMetrica.reportEvent("GuessStar", jsonValue);
                    endGame = false;
                    heathBarTest.setHp(3);
                    scoreNow = 0;
                    count++;
                    if (count >= artists.size() - 1) {
                        artists = Importer.getRandomArtists();
                        count = 0;
                    }
                    if (countAd<=0 && onRewarded){
                        countAd = 4;
                        mInterstitialAd.show();
                    }else{
                        countAd--;
                    }
                    onRewarded = true;
                    nextArtist();
                    updateScore();
                });
        if (onRewarded && rewardedCustom.onLoaded()) {
            builder.setMessage(String.format("%s! %s %s", getResources().getString(R.string.score_text, scoreNow),
                            getResources().getString(R.string.endGameNewGame), getResources().getString(R.string.endGameReward)))
                    .setNeutralButton(getResources().getString(R.string.endGameRewardShow), (dialogInterface, i) -> {
                        YandexMetrica.reportEvent("Reward", "{\"GuessStar\":{\"Game over\":\"Игра окончена, реклама\"}}");
                        endGame = false;
                        rewardedCustom.show(GuessStar.this, new RewardedCustom.RewardedInterface() {
                            @Override
                            public void onRewarded() {
                                onRewarded = false;
                                showReward = true;
                            }

                            @Override
                            public void onDismissed() {
                                if (showReward) {
                                    heathBarTest.restore();
                                    YandexMetrica.reportEvent("Reward", "{\"GuessStar\":{\"Game over\":\"Добавлено хп\"}}");
                                } else {
                                    YandexMetrica.reportEvent("Reward", "{\"GuessStar\":{\"Game over\":\"Реклама не просмотрена\"}}");
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
                    });
        }
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        YandexMetrica.reportEvent("GuessStar", "{\"Back\":\"Выход без окончания игры\"}");
        YandexMetrica.reportEvent("GuessStar", "{\"Back\":\"Количество очков: " + scoreNow + "\"}");
        super.onBackPressed();
    }
}