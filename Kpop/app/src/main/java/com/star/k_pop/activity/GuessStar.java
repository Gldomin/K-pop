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
import com.star.k_pop.ad.InterstitialCustomYandex;
import com.star.k_pop.ad.RewardedCustom;
import com.star.k_pop.ad.RewardedCustomYandex;
import com.star.k_pop.helper.Storage;
import com.star.k_pop.helper.Theme;
import com.star.k_pop.lib.HeathBar;
import com.star.k_pop.lib.SomeMethods;
import com.star.k_pop.lib.SoundPlayer;
import com.star.k_pop.model.Artist;

import java.util.ArrayList;
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

    private boolean hintUsed = false;

    private int hintCount = 4;

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

        rewardedCustom = new RewardedCustomYandex(this, getResources().getString(R.string.yandex_id_reward));
        mInterstitialAd = new InterstitialCustomYandex(this, getResources().getString(R.string.yandex_id_interstitial_game));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_star);

        final int pingClickID = soundPlayer.load(R.raw.ping_click); //id загруженного потока
        final int longSwitchID = soundPlayer.load(R.raw.long_switch);
        final int grace = soundPlayer.load(R.raw.bells);
        Storage storage = new Storage(this, "settings"); //хранилище для извлечения
        sound = storage.getBoolean("soundMode"); //настроек звука

        textRecord = findViewById(R.id.scoreText2);
        imageView = findViewById(R.id.imageView);
        textScore = findViewById(R.id.scoreText);
        textRecord.setTextColor(theme.getTextColor());
        textScore.setTextColor(theme.getTextColor());
        ImageButton about = findViewById(R.id.guessStarAbautButton);

        artists = Importer.getRandomArtists();
        if (artists.size() == 0) {
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
                        heathBarTest.restore();
                    }
                    boolean achievemented = false;
                    if (scoreNow == 10) { //ачивка за 10 - achGuessStarNormalText. Условие ачивки
                        if (SomeMethods.achievementGetted(GuessStar.this, R.string.achGuessStarBeginner, R.drawable.guess_star10, "achGuessStarBeginner")) //ачивочка
                        {
                            achievemented = true;
                        }
                    }
                    if (scoreNow == 50) { //ачивка за 50 - achGuessStarNormalText. Условие ачивки
                        if (SomeMethods.achievementGetted(GuessStar.this, R.string.achGuessStarNormal, R.drawable.guess_star50, "achGuessStarNormal")) //ачивочка
                        {
                            achievemented = true;
                        }
                    }
                    if (scoreNow == 150) { //ачивка за 150 - achGuessStarNormalText. Условие ачивки
                        if (SomeMethods.achievementGetted(GuessStar.this, R.string.achGuessStarExpert, R.drawable.guess_star150, "achGuessStarExpert")) //ачивочка
                        {
                            achievemented = true;
                        }
                    }
                    if (sound) {
                        if (achievemented) {
                            soundPlayer.playSoundStream(grace);//звук правильного ответа
                        } else {
                            soundPlayer.playSoundStream(longSwitchID);//звук правильного ответа
                        }
                    }
                    nextArtist();
                } else {
                    view.setBackgroundResource(theme.getBackgroundButtonDisable());
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

        ImageButton hintButton = findViewById(R.id.podskStart);
        hintButton.setBackgroundResource(theme.getBackgroundButton());
        hintButton.setImageResource(theme.getHintDrawable());
        hintButton.setOnClickListener(view -> {
            if (!hintUsed) {
                if (hintCount > 1) {
                    hintCount--;
                    int number = 0;
                    for (int i = 0; i < 4; i++) {
                        if (buttons[i].getText().equals(artists.get(count).getName())) {
                            number = i;
                        }
                    }
                    if (new Random().nextBoolean()) {
                        for (int i = 0; i < 2; i++) {
                            if (++number > 3) {
                                number = 0;
                            }
                            buttons[number].setBackgroundResource(theme.getBackgroundButtonDisable());
                            buttons[number].setClickable(false);
                        }
                    } else {
                        for (int i = 0; i < 2; i++) {
                            if (--number < 0) {
                                number = 3;
                            }
                            buttons[number].setBackgroundResource(theme.getBackgroundButtonDisable());
                            buttons[number].setClickable(false);
                        }
                    }
                    hintUsed = true;
                } else if (hintCount == 1) {
                    onRewardHint();
                }
            }
        });

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

    private void onRewardHint() {
        if (rewardedCustom.onLoaded()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
            builder.setTitle(getResources().getString(R.string.endHintCongratulate))
                    .setMessage(String.format("%s", getResources().getString(R.string.endHintReward)))
                    .setCancelable(false)
                    .setNegativeButton(getResources().getString(R.string.endHintNo),
                            (dialogInterface, i) -> {
                            })
                    .setPositiveButton(getResources().getString(R.string.endHintYes), (dialogInterface, i) ->
                            rewardedCustom.show(GuessStar.this, new RewardedCustom.RewardedInterface() {
                                @Override
                                public void onRewarded() {
                                    showReward = true;
                                }

                                @Override
                                public void onDismissed() {
                                    if (showReward) {
                                        int number = 0;
                                        for (int i = 0; i < 4; i++) {
                                            if (buttons[i].getText().equals(artists.get(count).getName())) {
                                                number = i;
                                            }
                                        }
                                        if (new Random().nextBoolean()) {
                                            for (int i = 0; i < 2; i++) {
                                                if (++number > 3) {
                                                    number = 0;
                                                }
                                                buttons[number].setBackgroundResource(theme.getBackgroundButtonDisable());
                                                buttons[number].setClickable(false);
                                            }
                                        } else {
                                            for (int i = 0; i < 2; i++) {
                                                if (--number < 0) {
                                                    number = 3;
                                                }
                                                buttons[number].setBackgroundResource(theme.getBackgroundButtonDisable());
                                                buttons[number].setClickable(false);
                                            }
                                        }
                                        hintUsed = true;
                                    }
                                    showReward = false;
                                }
                            }));
            AlertDialog alert = builder.create();
            alert.show();
        }
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
        hintUsed = false;
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
                .setMessage(String.format("%s.\n%s! %s", getResources().getString(R.string.last_artist_text, artists.get(count).getName()), getResources().getString(R.string.score_text, scoreNow),
                        getResources().getString(R.string.endGameNewGame)))
                .setCancelable(false)
                .setNegativeButton(getResources().getString(R.string.endGameNo), (dialogInterface, i) -> finish())
                .setPositiveButton(getResources().getString(R.string.endGameYes), (dialogInterface, i) -> {
                    endGame = false;
                    heathBarTest.setHp(3);
                    scoreNow = 0;
                    count++;
                    if (count >= artists.size() - 1) {
                        artists = Importer.getRandomArtists();
                        count = 0;
                    }
                    Storage storage = new Storage(this, "appStatus");
                    if (!storage.getBoolean("achTripleExpert")) {
                        if (countAd <= 0 && onRewarded) {
                            countAd = 4;
                            mInterstitialAd.show();
                        } else {
                            countAd--;
                        }
                    }
                    onRewarded = true;
                    nextArtist();
                    updateScore();
                });
        if (onRewarded && rewardedCustom.onLoaded()) {
            builder.setMessage(String.format("%s.\n%s %s\n%s.",getResources().getString(R.string.last_artist_text, artists.get(count).getName()),
                            getResources().getString(R.string.score_text, scoreNow),
                            getResources().getString(R.string.endGameNewGame), getResources().getString(R.string.endGameReward)))
                    .setNeutralButton(getResources().getString(R.string.endGameRewardShow), (dialogInterface, i) -> {
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
                    });
        }
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}