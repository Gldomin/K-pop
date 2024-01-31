package com.star.k_pop.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.imageview.ShapeableImageView;
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
import com.star.k_pop.model.Bands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import io.appmetrica.analytics.AppMetrica;

public class TwoBandsTinder extends AppCompatActivity {

    private HeathBar heathBarTest;

    //----------------------------------------------------------------------------------------------
    private ShapeableImageView imageBand; //Изображение артиста
    private ShapeableImageView imBTmp; //Изображение для затухающей анимации
    private ImageView allActLeftSlvPict; // Макет изображений после распределения в списке
    private LinearLayout allActGuessSolvedLeftLay; // Макет с картинками артистов распределенных в первую группу
    private LinearLayout allActGuessSolvedRightLay; // Макет с картинками артистов распределенных во вторую группу
    //----------------------------------------------------------------------------------------------
    private ArrayList<Bands> bands; //берем список всех групп
    //----------------------------------------------------------------------------------------------
    private TextView groupNameFirstOne; // Название первой группы на первом окне
    private TextView groupNameSecondOne; // Название второй группы на первом окне
    private TextView groupNameFirstTwo; // Название первой группы на втором окне
    private TextView groupNameSecondTwo; // Название второй группы на вторм окне
    private TextView groupNameFirstThree; // Название первой группы на первом окне
    private TextView groupNameSecondThree; // Название второй группы на первом окне
    //----------------------------------------------------------------------------------------------
    private TextView countGroupTextFirstOne; // Текст количества распределенных артистов в первую группу на первом экране
    private TextView countGroupTextSecondOne; // Текст количества распределенных артистов во вторую группу на первом экране
    private TextView countGroupTextFirstTwo; // Текст количества распределенных артистов в первую группу на втором экране
    private TextView countGroupTextSecondTwo; // Текст количества распределенных артистов во вторую группу на втором экране
    private ViewFlipper twoBandFlip; // Элемент для смены вида режима
    //----------------------------------------------------------------------------------------------
    private TextView scoreText; //Текст счета
    private int score; //Счет
    private int scoreHealth; //Сколько раз получено доп хп
    private int scoreHint;//Сколько раз получена доп подсказка
    //----------------------------------------------------------------------------------------------
    private boolean isViewMissTake; // Показ ошибок
    //----------------------------------------------------------------------------------------------
    private TextView scoreRecordText; //Текст рекорда
    private int scoreRecord; //Рекорд
    //----------------------------------------------------------------------------------------------
    private ImageButton hintButton; //Кнопка подсказки
    private TextView textHint; //Текст подсказки
    private LinearLayout layoutHint; //доп макет с подсказкой
    private TextView counterHint; //Текст количества подсказок
    private int hintCount; //Количество подсказок
    private int hintCountReward; //Количество подсказок за рекламу
    private boolean isHint; //Использована ли подсказка
    //----------------------------------------------------------------------------------------------
    private SoundPlayer soundPlayer; //это объект для воспроизведения звуков
    private int pingClickID; //Звук неправильного ответа
    private int longSwitchID; // Звук правильного ответа
    private int grace; //Звук ачивки
    private boolean sound; //включен ли звук
    //----------------------------------------------------------------------------------------------
    private static int countGroupMaxOne; // Максимальное количество человек в первой группе
    private static int countGroupMaxTwo; // Максимальное количество человек во второй группе
    private static int countGroupOne;  // Текущее количество человек в первой группе
    private static int countGroupTwo;  // Текущее количество человек в первой группе
    //----------------------------------------------------------------------------------------------
    private Theme theme; // Менеджер темы приложения
    //--------------------------------------------------------------------------------------------------
    private Bands first_band; // Первая группа
    private Bands second_band; // Вторая группа
    private ArrayList<Artist> artists_turn; // Все артисты первой и второй группы
    private Map<Artist, String> ansverMap; // Карта распределения артистов по группам
    private int pictnumb; // Номер текущего не распределенного артиста
    //--------------------------------------------------------------------------------------------------
    private RewardedCustom rewardedCustom; //Реклама за вознаграждение
    boolean showReward; // Просмотрена реклама до конца или нет
    private boolean onRewarded;      // Просмотр рекламы 1 раз
    //----------------------------------------------------------------------------------------------
    private InterstitialCustom mInterstitialAd; //Межстраничная реклама
    private int countAd; //Количество проигранных игр между рекламами

    @Override
    //инициализация смотрите ебанный xml
    protected void onCreate(Bundle savedInstanceState) {

        theme = new Theme(this);
        theme.setThemeSecond();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_bands_temp);

        createSounds();
        initGame(savedInstanceState);
        createAd();

        createButtons();

        initViewElement();

        createHint();

        imageSwipeCustom();
        createHeathBar();
        restartGame();
    }

    //проверка свайпа ansvermap получает ответ свайпа,setupimage устанавливает фотки
    @SuppressLint("ClickableViewAccessibility")
    private void imageSwipeCustom() {
        imageBand.setOnTouchListener(new OnSwipeTinderListener() {
            public void onCheck(boolean isLeft) {
                if (isLeft) {
                    ansverMap.put(artists_turn.get(pictnumb), first_band.getName());
                    countGroupTextFirstOne.setText(String.format(Locale.getDefault(), "%d/%d", ++countGroupOne, countGroupMaxOne));
                } else {
                    ansverMap.put(artists_turn.get(pictnumb), second_band.getName());
                    countGroupTextSecondOne.setText(String.format(Locale.getDefault(), "%d/%d", ++countGroupTwo, countGroupMaxTwo));
                }
                pictnumb++;
                if (pictnumb < ansverMap.size()) {
                    fadeAnimation(true, isLeft);
                    setupImage(artists_turn.get(pictnumb).getFolderNotRandom(), imageBand);
                    textHint.setText(artists_turn.get(pictnumb).getName());
                }
                if (pictnumb >= ansverMap.size()) {
                    countGroupTextFirstTwo.setText(String.format(Locale.getDefault(), "%d/%d", countGroupOne, countGroupMaxOne));
                    countGroupTextSecondTwo.setText(String.format(Locale.getDefault(), "%d/%d", countGroupTwo, countGroupMaxTwo));
                    twoBandFlip.showNext();
                    menuFlipEventInstance();
                    imageBand.animate().translationX(0).translationY(0).rotation(0).setDuration(0);
                }
            }
        });
    }

    //Инициализация данных из layout
    private void initViewElement() {
        imageBand = findViewById(R.id.imageBand);
        imBTmp = findViewById(R.id.imgBTmp);

        scoreText = findViewById(R.id.Score);
        scoreText.setTextColor(theme.getTextColor());

        scoreRecordText = findViewById(R.id.scoreRecord);
        scoreRecordText.setTextColor(theme.getTextColor());

        twoBandFlip = findViewById(R.id.twoBandFlipper);

        groupNameFirstOne = findViewById(R.id.textView12);
        groupNameSecondOne = findViewById(R.id.textView13);
        groupNameFirstTwo = findViewById(R.id.textView2);
        groupNameSecondTwo = findViewById(R.id.textView3);
        groupNameFirstThree = findViewById(R.id.textView21);
        groupNameSecondThree = findViewById(R.id.textView23);

        countGroupTextFirstOne = findViewById(R.id.textView14);
        countGroupTextSecondOne = findViewById(R.id.textView15);
        countGroupTextFirstTwo = findViewById(R.id.textView4);
        countGroupTextSecondTwo = findViewById(R.id.textView5);

        LinearLayout layout1 = findViewById(R.id.titleTinderBot);
        layout1.setBackgroundColor(theme.getColorLighter());

        LinearLayout layout2 = findViewById(R.id.titleTinderGroup);
        layout2.setBackgroundColor(theme.getColorLighter());

        LinearLayout layout3 = findViewById(R.id.titleTinder);
        layout3.setBackgroundColor(theme.getColorLighter());

        allActLeftSlvPict = findViewById(R.id.allActGuessLeftPict);

        allActGuessSolvedLeftLay = findViewById(R.id.allActorGuessLeftLay);
        allActGuessSolvedRightLay = findViewById(R.id.allActorGuessRightLay);
    }

    //Создание кнопок ответа и о режиме
    private void createButtons() {
        Button endButton = findViewById(R.id.allAct_Accept);
        endButton.setBackgroundResource(theme.getBackgroundButton());
        endButton.setOnClickListener(this::answerClickCheck);

        ImageButton helpButton = findViewById(R.id.helpTindButton);
        helpButton.setBackgroundResource(theme.getBackgroundButton());
        helpButton.setOnClickListener(v -> {
            Intent image = new Intent();
            image.setClass(TwoBandsTinder.this, BasicNotice.class);
            image.putExtra("text", R.string.twoBandsTinderGameModeAbout);
            image.putExtra("title", R.string.gameModeAbout);
            startActivity(image);
        });
    }

    //Загрузка сохранненых данных и списка групп
    private void initGame(Bundle savedInstanceState) {
        Storage storage = new Storage(this, "settings"); //хранилище для извлечения
        sound = false;
        sound = storage.getBoolean("soundMode"); //настроек звука
        bands = Importer.getRandomBands();
        if (bands.size() < 2) {
            finish();
        }

        SharedPreferences sp = getSharedPreferences("UserScore", Context.MODE_PRIVATE);
        scoreRecord = sp.getInt("userScoreTinder", 0);
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt("scoreTinder");
            scoreRecord = savedInstanceState.getInt("scoreRecordTinder");
        }
    }

    // Инициализация звуков
    private void createSounds() {
        soundPlayer = new SoundPlayer(this);
        pingClickID = soundPlayer.load(R.raw.ping_click); //id загруженного потока
        longSwitchID = soundPlayer.load(R.raw.long_switch);
        grace = soundPlayer.load(R.raw.bells);
    }

    //Инициализация рекламы
    private void createAd() {
        rewardedCustom = new RewardedCustomYandex(this, getResources().getString(R.string.yandex_id_reward));
        mInterstitialAd = new InterstitialCustomYandex(this, getResources().getString(R.string.yandex_id_interstitial_game));
        countAd = 5;
    }

    //Создание подсказки
    private void createHint() {
        counterHint = findViewById(R.id.counter_hints_tint);
        counterHint.setText(String.format(Locale.getDefault(), "%d", hintCount));

        textHint = findViewById(R.id.textViewTinderName);

        layoutHint = findViewById(R.id.titleTinderGroupName);
        layoutHint.setBackgroundColor(theme.getColorLighter());
        layoutHint.setVisibility(View.GONE);

        hintButton = findViewById(R.id.hintTindButton);
        hintButton.setBackgroundResource(theme.getBackgroundButton());
        hintButton.setImageResource(theme.getHintDrawable());
        hintButton.setOnClickListener(v -> {
            if (!isHint && !isViewMissTake) {
                if (hintCount > 0) {
                    hintCount--;
                    getHint();
                } else if (hintCountReward > 0) {
                    onRewardHint();
                }
            }
        });
    }

    //Получение подсказки
    private void getHint() {
        counterHint.setText(String.format(Locale.getDefault(), "%d", hintCount));
        hintButton.setBackgroundResource(theme.getBackgroundButtonDisable());
        layoutHint.setVisibility(View.VISIBLE);
        isHint = true;
        if (twoBandFlip.getDisplayedChild() == twoBandFlip.indexOfChild(findViewById(R.id.relativeLayout))) {
            menuFlipEventInstance();
        } else {
            textHint.setText(artists_turn.get(pictnumb).getName());
        }
    }

    //Получение подсказки за рекламу
    private void onRewardHint() {
        if (rewardedCustom.onLoaded()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
            builder.setTitle(getResources().getString(R.string.endHintCongratulate))
                    .setMessage(String.format("%s", getResources().getString(R.string.endHintReward, hintCountReward)))
                    .setCancelable(false)
                    .setNegativeButton(getResources().getString(R.string.endHintNo),
                            (dialogInterface, i) -> {
                            })
                    .setPositiveButton(getResources().getString(R.string.endHintYes), (dialogInterface, i) ->
                            rewardedCustom.show(TwoBandsTinder.this, new RewardedCustom.RewardedInterface() {
                                @Override
                                public void onRewarded() {
                                    showReward = true;
                                }

                                @Override
                                public void onDismissed() {
                                    if (showReward) {
                                        hintCountReward--;
                                        getHint();
                                    }
                                    AppMetrica.reportEvent("Show ads", "{\"tinder\":\"rewarded " + showReward + "\"}");
                                    showReward = false;
                                }
                            }));
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    //Инициализация менеджера здоровья
    private void createHeathBar() {
        ImageView imageView1 = findViewById(R.id.guessBandHeart1);
        ImageView imageView2 = findViewById(R.id.guessBandHeart2);
        ImageView imageView3 = findViewById(R.id.guessBandHeart3);
        ImageView imageView4 = findViewById(R.id.guessBandHeart4);
        ImageView imageView5 = findViewById(R.id.guessBandHeart5);
        ArrayList<ImageView> imageViewList = new ArrayList<>();
        imageViewList.add(imageView1);
        imageViewList.add(imageView2);
        imageViewList.add(imageView3);
        imageViewList.add(imageView4);
        imageViewList.add(imageView5);
        Animation lifeBrokeAnimation = AnimationUtils.loadAnimation(this, R.anim.heart_broke_animation);
        heathBarTest = new HeathBar(imageViewList, 5, lifeBrokeAnimation);
    }

    //Сохраниение данных при удалении и восстановлении активити
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("scoreTinder", score);
        outState.putInt("scoreRecordTinder", scoreRecord);
        super.onSaveInstanceState(outState);
    }

    //Сохранение рекорда
    @Override
    protected void onPause() {
        SharedPreferences sp = getSharedPreferences("UserScore", Context.MODE_PRIVATE); //сохранение Счета
        if (sp.getInt("userScoreTinder", -1) < scoreRecord) {
            SharedPreferences.Editor e = sp.edit();
            e.putInt("userScoreTinder", scoreRecord);
            e.apply();
        }
        super.onPause();
    }

    //Главная процедура, запускается начальная последовательность , устанавливается главная картинка, указывается текст групп
    public void mainProcedure() {
        boolean isSexSame = true;
        do {
            first_band = bands.get(0);
            for (int i = 1; i < bands.size(); i++) {
                if (bands.get(i).getSex() == first_band.getSex()) {
                    second_band = bands.get(i);
                    isSexSame = false;
                    bands.remove(i);
                    break;
                }
            }
            bands.remove(0);
            if (bands.isEmpty()) {
                bands = Importer.getRandomBands();
            }
        } while (isSexSame);
        startSequance();
        setupImage(artists_turn.get(pictnumb).getFolderNotRandom(), imageBand);
        setupBandText();
    }

    //Основная Последовательность Режима берутся две группы,создается лист артистов из двух групп обнуляются карты ответов , перемешиваются артисты
    public void startSequance() {
        pictnumb = 0;
        if (artists_turn != null) {
            for (Artist artist : artists_turn) {
                artist.removeRandomCount();
            }
        }
        artists_turn = new ArrayList<>();
        ansverMap = new HashMap<>();
        artists_turn.addAll(first_band.getArtists());
        artists_turn.addAll(second_band.getArtists());
        Collections.shuffle(artists_turn);
        for (Artist i : artists_turn) {
            ansverMap.put(i, "null");
        }
        countGroupOne = 0;
        countGroupTwo = 0;
    }


    // Установка изображения
    public void setupImage(String pathtoFolder, ImageView img) {
        Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + pathtoFolder))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(img);
    }

    //указываются названия групп и число ответов
    public void setupBandText() {
        groupNameFirstOne.setText(first_band.getName());
        groupNameSecondOne.setText(second_band.getName());
        groupNameFirstTwo.setText(first_band.getName());
        groupNameSecondTwo.setText(second_band.getName());
        groupNameFirstThree.setText(first_band.getName());
        groupNameSecondThree.setText(second_band.getName());
        countGroupMaxOne = first_band.getNumberOfPeople();
        countGroupMaxTwo = second_band.getNumberOfPeople();
        countGroupTextFirstOne.setText(String.format(Locale.getDefault(), "0/%d", countGroupMaxOne));
        countGroupTextSecondOne.setText(String.format(Locale.getDefault(), "0/%d", countGroupMaxTwo));
    }

    //анимация затухания, чтобы картинки красиво улетали imbTmp временная картинка которая испаряется, т.к иначе нельзя
    public void fadeAnimation(boolean anim, boolean isLeft) {
        if (anim) {
            imBTmp.setVisibility(View.VISIBLE);
            imBTmp.setY(imageBand.getY());
            imBTmp.setX(imageBand.getX());
            imBTmp.setImageDrawable(imageBand.getDrawable());
            imBTmp.setRotation(0);
            imBTmp.setScaleX(1);
            imBTmp.setScaleY(1);
            imBTmp.setAlpha(1.0f);
        }
        if (isLeft) {
            imBTmp.animate().scaleX(0.3f).scaleY(0.3f).rotation(30).alpha(0.1f).setDuration(400).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    imBTmp.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            imBTmp.animate().scaleX(0.3f).scaleY(0.3f).rotation(-30).alpha(0.1f).setDuration(400).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    imBTmp.setVisibility(View.INVISIBLE);
                }
            });
        }
        imageBand.animate().translationX(0).translationY(0).rotation(0).setDuration(0);
    }

    //Кнопка проверки ответа
    public void answerClickCheck(View view) {
        if (isViewMissTake) {
            nextArtist();
        } else {
            if (countGroupOne == countGroupMaxOne && countGroupTwo == countGroupMaxTwo) {
                answerScreen();
            } else {
                errorScreen();
            }
        }
    }

    //показывает количество ошибок
    private int mistakesCount() {
        int mistakes = 0;
        for (Map.Entry<Artist, String> map : ansverMap.entrySet()) {
            if (!map.getKey().getGroup().equals(map.getValue())) {
                mistakes++;
            }
        }
        return mistakes;
    }

    // диалог при неправильном количестве букв
    public void errorScreen() {
        AlertDialog.Builder alertBuild = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
        alertBuild.setTitle(getResources().getString(R.string.endHintCongratulate));
        alertBuild.setMessage(getResources().getString(R.string.tinderCountGroupError));
        alertBuild.setPositiveButton(getResources().getString(R.string.tinderContinue), (dialogInterface, i) -> dialogInterface.cancel());
        AlertDialog alert = alertBuild.create();
        alert.show();
    }

    //Диалог проверки ответа
    public void answerScreen() {
        AlertDialog.Builder alertBuild = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
        alertBuild.setTitle(getResources().getString(R.string.tinderLoseScreenTitle));
        int missTake = mistakesCount();
        int countCorrect = ansverMap.size() - missTake;
        countCorrect /= 2;
        score += countCorrect;
        if (scoreRecord < score) {
            scoreRecord = score;
        }
        if (missTake == 0) {
            alertBuild.setMessage(getResources().getString(R.string.tinderLoseScreenWinMessage, countCorrect));
            if (sound) {
                soundPlayer.playSoundStream(longSwitchID);
            }
        } else {
            if (sound) {
                soundPlayer.playSoundStream(pingClickID);
            }
            alertBuild.setMessage(getResources().getString(R.string.tinderLoseScreenLoseMessage, countCorrect, ansverMap.size() / 2));
            alertBuild.setNeutralButton(getResources().getString(R.string.tinderMissTakeView), (dialogInterface, i) -> viewMissTake());
        }
        if (missTake > 0) {
            heathBarTest.blow();
        }
        alertBuild.setPositiveButton(getResources().getString(R.string.tinderContinue), (dialogInterface, i) -> nextArtist());
        alertBuild.setOnCancelListener(dialog -> nextArtist());
        AlertDialog alert = alertBuild.create();
        alert.show();
    }

    //Показ правильных и неправильных ответов
    private void viewMissTake() {
        allActGuessSolvedRightLay.removeAllViews();
        allActGuessSolvedLeftLay.removeAllViews();
        isViewMissTake = true;
        for (Map.Entry<Artist, String> answer : ansverMap.entrySet()) {
            // Проверки в какую колонку они определяются
            if (answer.getValue().equals(first_band.getName())) {
                allActGuessSolvedLeftLay.addView(createImageLay(answer.getKey(), true, isViewMissTake, answer.getKey().checkGroup(first_band.getName())));
            }
            if (answer.getValue().equals(second_band.getName())) {
                allActGuessSolvedRightLay.addView(createImageLay(answer.getKey(), true, isViewMissTake, answer.getKey().checkGroup(second_band.getName())));
            }
        }
    }

    //Смена групп для распределения
    private void nextArtist() {
        isViewMissTake = false;
        isHint = false;
        layoutHint.setVisibility(View.GONE);
        if (score / 50 > scoreHealth) { //Восстановление здоровья
            scoreHealth = score / 50;
            heathBarTest.restore();
        }
        if (score / 100 > scoreHint) { //Восстановление подсказки
            scoreHint = score / 100;
            hintCount++;
            counterHint.setText(String.format(Locale.getDefault(), "%d", hintCount));
        }
        if (hintCount > 0 || hintCountReward > 0) {
            hintButton.setBackgroundResource(theme.getBackgroundButton());
        }
        if (heathBarTest.getHp() > 0) {
            boolean achievemented = isAchievemented();
            if (achievemented && sound) {
                soundPlayer.playSoundStream(grace); //звук ачивки
            }
            if (twoBandFlip.getDisplayedChild() == twoBandFlip.indexOfChild(findViewById(R.id.relativeLayout))) {
                twoBandFlip.showNext();
            }
            scoreText.setText(getResources().getString(R.string.endGameTextScoreNow, score));
            scoreRecordText.setText(getResources().getString(R.string.endGameTextRecordNow, scoreRecord));
            mainProcedure();
        } else {
            startLosingDialog();
        }
    }

    //Проверка ачивок
    private boolean isAchievemented() {
        if (score >= 15 && score < 75) { //ачивка за 15 Условие ачивки
            return SomeMethods.achievementGetted(TwoBandsTinder.this, R.string.achDistributeByBandsBeginner, R.drawable.devide_bands15, "achSwipeTwoBandsBeginner"); //ачивочка
        }
        if (score >= 75 && score < 225) { //ачивка за 75. Условие ачивки
            return SomeMethods.achievementGetted(TwoBandsTinder.this, R.string.achDistributeByBandsNormal, R.drawable.devide_bands75, "achSwipeTwoBandsNormal"); //ачивочка
        }
        if (score >= 225) { //ачивка за 225. Условие ачивки
            return SomeMethods.achievementGetted(TwoBandsTinder.this, R.string.achDistributeByBandsExpert, R.drawable.devide_bands225, "achSwipeTwoBandsExpert"); //ачивочка
        }
        return false;
    }

    //Начальные значения при новой игре
    private void restartGame() {
        AppMetrica.reportEvent("Restart", "{\"tinder\":\"record " + scoreRecord + "\"}");
        score = 0;
        scoreHealth = 0;
        scoreHint = 0;
        hintCount = 3;
        hintCountReward = 3;
        showReward = false;
        onRewarded = true;
        counterHint.setText(String.format(Locale.getDefault(), "%d", hintCount));
        heathBarTest.restartHp();
        nextArtist();
        interstitialShow();
    }

    //Запуск диалога проигрыша
    private void startLosingDialog() {
        AlertDialog.Builder alertBuild = new AlertDialog.Builder(this, theme.getAlertDialogStyle());

        String textMessage = String.format("%s! %s", getResources().getString(R.string.score_text, score), getResources().getString(R.string.endGameNewGame));
        if (onRewarded && rewardedCustom.onLoaded()) {
            textMessage += String.format("\n%s", getResources().getString(R.string.endGameReward));
            alertBuild.setNeutralButton(getResources().getString(R.string.endGameRewardShow), (dialogInterface, i) ->
                    rewardedCustom.show(TwoBandsTinder.this, new RewardedGuessTinder()));
        }
        alertBuild.setTitle(getResources().getString(R.string.endGameTitle))
                .setMessage(textMessage)
                .setPositiveButton(getResources().getString(R.string.endGameYes), (dialogInterface, i) -> restartGame())
                .setNegativeButton(getResources().getString(R.string.endGameNo), (dialog, which) -> finish())
                .setOnCancelListener(dialog -> finish());
        AlertDialog alert = alertBuild.create();
        alert.show();
    }

    //Класс для показа рекламы за вознаграждение при проигрыше
    class RewardedGuessTinder implements RewardedCustom.RewardedInterface {
        @Override
        public void onRewarded() {
            onRewarded = false;
            showReward = true;
        }

        @Override
        public void onDismissed() {
            if (showReward) {
                heathBarTest.restore();
                nextArtist();
            } else {
                restartGame();
            }
            AppMetrica.reportEvent("Show ads", "{\"tinder\":\"rewarded " + showReward + "\"}");
            showReward = false;
        }
    }

    // ивент переворота на экран результата
    public void menuFlipEventInstance() {
        allActGuessSolvedRightLay.removeAllViews();
        allActGuessSolvedLeftLay.removeAllViews();
        // Отображение всех отгаданых типов
        for (Map.Entry<Artist, String> answer : ansverMap.entrySet()) {
            if (answer.getValue().equals(first_band.getName())) {
                allActGuessSolvedLeftLay.addView(createImageLay(answer.getKey(), true, isViewMissTake, false));
            }
            if (answer.getValue().equals(second_band.getName())) {
                allActGuessSolvedRightLay.addView(createImageLay(answer.getKey(), false, isViewMissTake, false));
            }
        }
    }

    //Получение картинки распределенной в один из столбцов
    private RelativeLayout createImageLay(Artist answer, boolean isLeft, boolean isMissTake, boolean isCurrent) {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(allActLeftSlvPict.getLayoutParams());

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(5, 5, 5, 5);

        ImageView pict = new ImageView(this);
        pict.setLayoutParams(allActLeftSlvPict.getLayoutParams());
        pict.setScaleType(ImageView.ScaleType.MATRIX);

        if (!isMissTake) {
            relativeLayout.setOnClickListener(new TouchSwitchImage(artists_turn.indexOf(answer), isLeft, relativeLayout));
        } else {
            layoutParams.setMargins(10, 10, 10, 10);
            relativeLayout.setPadding(20, 20, 20, 20);
            ImageView background = new ImageView(this);
            if (isCurrent) {
                background.setBackgroundResource(theme.getColorMissTakeCurrent());
            } else {
                background.setBackgroundResource(theme.getColorMissTakeError());
            }
            relativeLayout.addView(background, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        }

        relativeLayout.addView(pict, layoutParams);
        setupImage(answer.getFolderNotRandom(), pict);

        if (isHint || isMissTake) {
            TextView textView = new TextView(this);
            String textName = String.format(Locale.getDefault(), "%s", answer.getName());
            if (isMissTake) {
                textName += String.format(Locale.getDefault(), "\n(%s)", answer.getGroup());
            }
            textView.setText(textName);

            textView.setTextSize(20);
            textView.setShadowLayer(5, 3, 3, Color.BLACK);
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            RelativeLayout.LayoutParams layoutParamsText = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParamsText.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParamsText.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            layoutParamsText.setMargins(0, 0, 0, 15);
            relativeLayout.addView(textView, layoutParamsText);
        }
        return relativeLayout;
    }

    //Показ межстраничной рекламы
    private void interstitialShow() {
        Storage storage = new Storage(this, "appStatus");
        if (!storage.getBoolean("achTripleExpert")) {
            if (countAd <= 0 && onRewarded) {
                countAd = 5;
                if (mInterstitialAd.show()) {
                    AppMetrica.reportEvent("Show ads", "{\"tinder\":\"interstitial\"}");
                }
            }
            countAd--;
        } else {
            AppMetrica.reportEvent("Remove ads", "{\"tinder\":\"interstitial\"}");
        }
    }

    // Класс для перераспределения артистов по группам
    private class TouchSwitchImage implements View.OnClickListener {

        int count;
        boolean isLeft;
        RelativeLayout relativeLayout;

        public TouchSwitchImage(int count, boolean isLeft, RelativeLayout relativeLayout) {
            this.count = count;
            this.isLeft = isLeft;
            this.relativeLayout = relativeLayout;
        }

        @Override
        public void onClick(View v) {
            if (isLeft) {
                allActGuessSolvedLeftLay.removeView(relativeLayout);
                ansverMap.put(artists_turn.get(count), second_band.getName());
                relativeLayout = null;
                allActGuessSolvedRightLay.addView(createImageLay(artists_turn.get(count), false, isViewMissTake, false));
                countGroupTextSecondTwo.setText(String.format(Locale.getDefault(), "%d/%d", ++countGroupTwo, countGroupMaxTwo));
                countGroupTextFirstTwo.setText(String.format(Locale.getDefault(), "%d/%d", --countGroupOne, countGroupMaxOne));
            } else {
                allActGuessSolvedRightLay.removeView(relativeLayout);
                ansverMap.put(artists_turn.get(count), first_band.getName());
                relativeLayout = null;
                allActGuessSolvedLeftLay.addView(createImageLay(artists_turn.get(count), true, isViewMissTake, false));
                countGroupTextSecondTwo.setText(String.format(Locale.getDefault(), "%d/%d", --countGroupTwo, countGroupMaxTwo));
                countGroupTextFirstTwo.setText(String.format(Locale.getDefault(), "%d/%d", ++countGroupOne, countGroupMaxOne));
            }
        }
    }

    // класс listener для картинки распределения вправо-влево (свайп)
    static class OnSwipeTinderListener implements View.OnTouchListener {
        //переменные для положения x
        float dX;
        float defX;
        float dY;
        float defY;
        //переменные для поворота
        float rotateX;
        float dRotateX;
        //переменные для проверки финального условия
        boolean leftCheck;
        boolean rightCheck;
        float paddingYx;
        //ViewGroup.MarginLayoutParams padding;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(final View v, final MotionEvent event) {
            //получаем размер экрана чтобы различать в какую сторону скинули
            DisplayMetrics metrics = new DisplayMetrics();
            v.getDisplay().getMetrics(metrics);
            int width = metrics.widthPixels;
            //формулы для определения в какую сторону была скинута картинка
            leftCheck = (defX < (width / 2f - width + 30));
            rightCheck = (defX > (width / 2f - 30));
            //формула для для вычисление поворота
            rotateX = (event.getRawX() / width) * 45 - 17.5f;
            dRotateX = dX / width * 45 + 17.5f;

            switch (event.getAction()) {
                //обработка события нажатия на экран
                case MotionEvent.ACTION_DOWN:
                    //задаем значение dx равное обратной позиции нажатия на экран, чтобы картинка не сьехала
                    dY = v.getY() - event.getRawY();
                    dX = v.getX() - event.getRawX();
                    paddingYx = v.getY();
                    break;
                //обработка события проведение по экрану
                case MotionEvent.ACTION_MOVE:
                    // движение картинки так как dx назначается в начале,то defx будет двигать только картинку от ее начальной позиции
                    defX = dX + event.getRawX();
                    defY = dY + event.getRawY();
                    //droat и roat та же логика
                    v.animate().setDuration(0);
                    v.animate().x(defX).y(defY).rotation(rotateX + dRotateX).start();
                    break;
                // обработка события отпуск от экрана
                case MotionEvent.ACTION_UP:
                    // если картинка не находится слева и справа
                    if (!leftCheck && !rightCheck) {
                        v.animate().setDuration(400).translationX(0).translationY(0).rotation(0);
                    }
                    if (leftCheck) {
                        v.animate().y(paddingYx);
                        onCheck(true);
                    }
                    if (rightCheck) {
                        v.animate().y(paddingYx);
                        onCheck(false);
                    }
                    break;
            }
            return true;
        }

        public void onCheck(boolean isLeft) {

        }
    }
}

