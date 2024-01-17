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
import android.util.Log;
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
import androidx.cardview.widget.CardView;

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
import java.util.Objects;
import java.util.Random;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import io.appmetrica.analytics.AppMetrica;

public class TwoBandsTinder extends AppCompatActivity {

    //-------------------------------------------------------------------------------------------------
    private ArrayList<Bands> bands; //берем список всех групп
    private int bandsCount;

    private ShapeableImageView imageBand;
    private ShapeableImageView imBTmp;

    private TextView groupNameFirstOne;
    private TextView groupNameSecondOne;
    private TextView groupNameFirstTwo;
    private TextView groupNameSecondTwo;
    private TextView groupNameFirstThree;
    private TextView groupNameSecondThree;

    private TextView countGroupTextFirstOne;
    private TextView countGroupTextSecondOne;

    private TextView countGroupTextFirstTwo;
    private TextView countGroupTextSecondTwo;

    private ViewFlipper twoBandFlip;
    private boolean left;
    private boolean right;

    private static int countGroupMaxOne;
    private static int countGroupMaxTwo;
    private static int countGroupOne;
    private static int countGroupTwo;

    Theme theme;

    //--------------------------------------------------------------------------------------------------
    Bands first_band;
    Bands second_band;
    ArrayList<Artist> artists_turn;
    Map<Artist, String> ansverMap;
    private int pictnumb;
    private int pictNumbCurrent;
    //--------------------------------------------------------------------------------------------------

    private LinearLayout AllActLayoutMainLay;
    private LinearLayout AllActLayoutCardLeftLay;
    private LinearLayout AllActLayoutCardRightLay;
    private LinearLayout AllActLayoutUnsLvLay;
    private Button allActRightCardButton;
    private Button allActLeftCardButton;
    private ImageView allActLeftSlvPict;
    private ImageView allActRightSlvPict;
    private ImageView allActUnsLvPictPict;

    private CardView allActCardView;

    private LinearLayout allActGuessSolvedLeftLay;
    private LinearLayout allActGuessSolvedRightLay;

    private HeathBar heathBarTest;

    int scoreHealth = 0;
    int scoreHint = 0;
    private int score = 0;
    private int scoreRecord = 0;

    private boolean isViewMissTake = false;
    //----------------------------------------------------------------------------------------------

    SoundPlayer soundPlayer = new SoundPlayer(this); //это объект для воспроизведения звуков
    int pingClickID; //id загруженного потока
    int longSwitchID;
    int grace;
    private boolean sound = false; //включен ли звук
    private static final String IMAGEVIEW_TAG = "icon bitmap";
    private TextView scoreText;
    private TextView scoreRecordText;
    private TextView counterHint;
    AlertDialog.Builder alertBuild;

    private RewardedCustom rewardedCustom;
    private InterstitialCustom mInterstitialAd;
    private int countAd = 5;
    boolean showReward = false;
    private int hintCountReward = 3;

    private int hintCount = 3;

    private boolean isHint = false;

    ImageButton hintButton;
    LinearLayout layoutHint;
    TextView textHint;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    //инициализация смотрите ебанный xml
    protected void onCreate(Bundle savedInstanceState) {
        theme = new Theme(this);
        theme.setThemeSecond();

        super.onCreate(savedInstanceState);

        pingClickID = soundPlayer.load(R.raw.ping_click); //id загруженного потока
        longSwitchID = soundPlayer.load(R.raw.long_switch);
        grace = soundPlayer.load(R.raw.bells);
        Storage storage = new Storage(this, "settings"); //хранилище для извлечения
        sound = storage.getBoolean("soundMode"); //настроек звука

        bands = Importer.getRandomBandsSex();
        if (bands.size() < 2) {
            finish();
        }

        rewardedCustom = new RewardedCustomYandex(this, getResources().getString(R.string.yandex_id_reward));
        mInterstitialAd = new InterstitialCustomYandex(this, getResources().getString(R.string.yandex_id_interstitial_game));

        setContentView(R.layout.activity_two_bands_temp);

        Button confirmButton = findViewById(R.id.ttConfirmButton);
        Button endButton = findViewById(R.id.allAct_Accept);
        ImageButton helpButton = findViewById(R.id.helpTindButton);
        hintButton = findViewById(R.id.hintTindButton);


        confirmButton.setBackgroundResource(theme.getBackgroundButton());
        endButton.setBackgroundResource(theme.getBackgroundButton());
        helpButton.setBackgroundResource(theme.getBackgroundButton());
        hintButton.setBackgroundResource(theme.getBackgroundButton());

        textHint = findViewById(R.id.textViewTinderName);

        imageBand = findViewById(R.id.imageBand);
        imBTmp = findViewById(R.id.imgBTmp);
        scoreText = findViewById(R.id.Score);
        scoreRecordText = findViewById(R.id.scoreRecord);
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
        LinearLayout layout2 = findViewById(R.id.titleTinderGroup);
        LinearLayout layout3 = findViewById(R.id.titleTinder);
        layoutHint = findViewById(R.id.titleTinderGroupName);

        layout1.setBackgroundColor(theme.getColorLighter());
        layout2.setBackgroundColor(theme.getColorLighter());
        layout3.setBackgroundColor(theme.getColorLighter());
        layoutHint.setBackgroundColor(theme.getColorLighter());
        layoutHint.setVisibility(View.GONE);

        scoreText.setTextColor(theme.getTextColor());
        scoreRecordText.setTextColor(theme.getTextColor());

        counterHint = findViewById(R.id.counter_hints_tint);
        counterHint.setText(String.format(Locale.getDefault(), "%d", hintCount));

        //--------------------------------------------------------------------------------------
        //----------------------------------------------------------------------------------------------
        AllActLayoutMainLay = findViewById(R.id.allActorLLay);
        AllActLayoutCardLeftLay = findViewById(R.id.allAct_Card_LLay);
        AllActLayoutCardRightLay = findViewById(R.id.allAct_Card_RLay);
        AllActLayoutUnsLvLay = findViewById(R.id.allAct_UnslvFold);
        allActRightCardButton = findViewById(R.id.allAct_Card_RightABtn);
        allActLeftCardButton = findViewById(R.id.allAct_Card_LeftABtn);
        allActLeftSlvPict = findViewById(R.id.allActGuessLeftPict);
        allActRightSlvPict = findViewById(R.id.allActGuessRightPict);
        allActUnsLvPictPict = findViewById(R.id.allAct_Unslv_Pict);
        //--------------------------------------------------------------------------------------
        allActGuessSolvedLeftLay = findViewById(R.id.allActorGuessLeftLay);
        allActGuessSolvedRightLay = findViewById(R.id.allActorGuessRightLay);

        allActCardView = findViewById(R.id.allAct_Card);

        SharedPreferences sp = getSharedPreferences("UserScore", Context.MODE_PRIVATE);
        scoreRecord = sp.getInt("userScoreTinder", 0);
        scoreRecordText.setText(getResources().getString(R.string.record_text, scoreRecord));
        scoreText.setText(getResources().getString(R.string.score_text, score));
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt("scoreTinder");
            scoreRecord = savedInstanceState.getInt("scoreRecordTinder");
        }
        //----------------------not changed
        //нажатие на кнопку меняет окно на другое
        confirmButton.setOnClickListener(view -> {
            if (twoBandFlip != null) {
                menuFlipEventInstance();
                twoBandFlip.showNext();
                countGroupTextFirstTwo.setText(String.format(Locale.getDefault(), "%d/%d", countGroupOne, countGroupMaxOne));
                countGroupTextSecondTwo.setText(String.format(Locale.getDefault(), "%d/%d", countGroupTwo, countGroupMaxTwo));
            }
        });


        helpButton.setOnClickListener(v -> {
            Intent image = new Intent();
            image.setClass(TwoBandsTinder.this, BasicNotice.class);
            image.putExtra("text", R.string.twoBandsTinderGameModeAbout);
            image.putExtra("title", R.string.gameModeAbout);
            startActivity(image);
        });

        hintButton.setImageResource(theme.getHintDrawable());
        hintButton.setOnClickListener(v -> {
            if (!isHint && !isViewMissTake) {
                if (hintCount > 0) {
                    hintCount--;
                    counterHint.setText(String.format(Locale.getDefault(), "%d", hintCount));
                    hintButton.setBackgroundResource(theme.getBackgroundButtonDisable());
                    layoutHint.setVisibility(View.VISIBLE);
                    textHint.setText(artists_turn.get(pictNumbCurrent).getName());
                    isHint = true;
                    if (twoBandFlip.getDisplayedChild() == twoBandFlip.indexOfChild(findViewById(R.id.relativeLayout))) {
                        menuFlipEventInstance();
                    }
                }
                else if (hintCountReward > 0){
                    onRewardHint();
                }
            }
        });

//-----------------------working----------------------------------------------------------------------
        imageBand.setOnTouchListener(new OnSwipeTinderListener() {

            //проверка свайпа ansvermap получает ответ свайпа,setupimage устанавливает фотки
            public void onLeftCheck() {
                left = true;
                right = false;
                ansverMap.put(artists_turn.get(pictNumbCurrent), first_band.getName());

                countGroupTextFirstOne.setText(String.format(Locale.getDefault(), "%d/%d", ++countGroupOne, countGroupMaxOne));
                pictnumb++;
                if (pictnumb < ansverMap.size()) {
                    fadeAnimation(true);
                    for (int i = 0; i < ansverMap.size(); i++) {
                        if (Objects.equals(ansverMap.get(artists_turn.get(i)), "null")) {
                            pictNumbCurrent = i;
                            break;
                        }
                    }
                    setupImage(artists_turn.get(pictNumbCurrent).getFolder(), imageBand);
                    textHint.setText(artists_turn.get(pictNumbCurrent).getName());
                }
                if (pictnumb >= ansverMap.size()) {

                    countGroupTextFirstTwo.setText(String.format(Locale.getDefault(), "%d/%d", countGroupOne, countGroupMaxOne));
                    countGroupTextSecondTwo.setText(String.format(Locale.getDefault(), "%d/%d", countGroupTwo, countGroupMaxTwo));
                    twoBandFlip.showNext();
                    menuFlipEventInstance();
                    imageBand.animate().translationX(0).translationY(0).rotation(0).setDuration(0);
                }
            }

            //проверка свайпа ansvermap получает ответ свайпа,setupimage устанавливает фотки
            public void onRightCheck() {
                left = false;
                right = true;
                ansverMap.put(artists_turn.get(pictNumbCurrent), second_band.getName());

                countGroupTextSecondOne.setText(String.format(Locale.getDefault(), "%d/%d", ++countGroupTwo, countGroupMaxTwo));
                pictnumb++;
                if (pictnumb < ansverMap.size()) {
                    fadeAnimation(true);
                    for (int i = 0; i < ansverMap.size(); i++) {
                        Log.e("DEBUGINGGAME", ansverMap.get(artists_turn.get(i)));
                        if (Objects.equals(ansverMap.get(artists_turn.get(i)), "null")) {
                            pictNumbCurrent = i;
                            break;
                        }
                    }
                    setupImage(artists_turn.get(pictNumbCurrent).getFolderNotRandom(), imageBand);
                    textHint.setText(artists_turn.get(pictNumbCurrent).getName());
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
        createHeathBar();
        guessTwoBands();
    }

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
                                        hintButton.setBackgroundResource(theme.getBackgroundButtonDisable());
                                        layoutHint.setVisibility(View.VISIBLE);
                                        textHint.setText(artists_turn.get(pictNumbCurrent).getName());
                                        isHint = true;
                                        if (twoBandFlip.getDisplayedChild() == twoBandFlip.indexOfChild(findViewById(R.id.relativeLayout))) {
                                            menuFlipEventInstance();
                                        }
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("scoreTinder", score);
        outState.putInt("scoreRecordTinder", scoreRecord);
        super.onSaveInstanceState(outState);
    }

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

    //--------------------------------------------------------------------------------------------------


    private void guessTwoBands() {
        //Инициализация Основного Метода, указывается Количество груп,и обнуляются свайпы
        imageBand.setTag(IMAGEVIEW_TAG);
        bandsCount = 0;
        left = false;
        right = false;
        mainProcedure();
    }

    public void mainProcedure() {
        //Главная процедура, запускается начальная последовательность , устанавливается главная картинка, указывается текст групп
        startSequance();
        setupImage(artists_turn.get(pictNumbCurrent).getFolderNotRandom(), imageBand);
        setupBandText();
    }

    public void startSequance() {
        //Основная Последовательность Режима берутся две группы,создается лист артистов из двух групп обнуляются карты ответов , перемешиваются артисты
        pictnumb = 0;
        pictNumbCurrent = 0;
        if (artists_turn != null) {
            for (Artist artist : artists_turn) {
                artist.removeRandomCount();
            }
        }
        first_band = bands.get(bandsCount);
        second_band = bands.get(bandsCount + 1);
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


    //    Новый метод старый не особо удобен и приходится копировать
    public void setupImage(String pathtoFolder, ImageView img) {
        Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + pathtoFolder))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(img);
    }

    public void setupBandText() {
        //указываются названия групп и число ответов
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

    public void fadeAnimation(boolean anim) {
        //анимация затухания, чтобы картинки красиво улетали imbTmp временная картинка которая испаряется, т.к иначе нельзя
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
        if (right) {
            imBTmp.animate().scaleX(0.3f).scaleY(0.3f).rotation(-30).alpha(0.1f).setDuration(400).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    imBTmp.setVisibility(View.INVISIBLE);
                }
            });
        }
        if (left) {
            imBTmp.animate().scaleX(0.3f).scaleY(0.3f).rotation(30).alpha(0.1f).setDuration(400).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    imBTmp.setVisibility(View.INVISIBLE);
                }
            });
        }
        imageBand.animate().translationX(0).translationY(0).rotation(0).setDuration(0);
    }

    public void ttClickCheck(View view) {
        if (isViewMissTake) {
            nextArtist();
        } else {
            if (countGroupOne == countGroupMaxOne && countGroupTwo == countGroupMaxTwo) {
                losescreen();
            } else {
                errorScreen();
            }
        }
    }

    public void resultsSequence() {
        //финальная последовательность, запускается после нее основная
        bandsCount = 0;
        bands = Importer.getRandomBandsSex();
        mainProcedure();
    }

    private int mistakescount() {
        //показывает количество ошибок
        int mistakes = 0;
        for (Map.Entry<Artist, String> map : ansverMap.entrySet()) {
            if (!map.getKey().getGroup().equals(map.getValue())) {
                mistakes++;
            }
        }
        return mistakes;
    }

    public void errorScreen() {
        AlertDialog.Builder alertbuild = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
        alertbuild.setTitle(getResources().getString(R.string.endHintCongratulate));
        alertbuild.setMessage(getResources().getString(R.string.tinderCountGroupError));
        alertbuild.setPositiveButton(getResources().getString(R.string.tinderContinue), (dialogInterface, i) -> dialogInterface.cancel());
        AlertDialog alert = alertbuild.create();
        alert.show();
    }

    public void losescreen() {
        //финал пройгрыша
        alertBuild = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
        alertBuild.setTitle(getResources().getString(R.string.tinderLoseScreenTitle));
        int misstake = mistakescount();
        int countCorrect = ansverMap.size() - misstake;
        countCorrect /= 2;
        score += countCorrect;
        if (scoreRecord < score) {
            scoreRecord = score;
        }
        if (misstake == 0) {
            alertBuild.setMessage(getResources().getString(R.string.tinderLoseScreenWinMessage, countCorrect));
        } else {
            alertBuild.setMessage(getResources().getString(R.string.tinderLoseScreenLoseMessage, countCorrect, ansverMap.size() / 2));
            alertBuild.setNeutralButton(getResources().getString(R.string.tinderMissTakeView), (dialogInterface, i) -> viewMissTake());
        }
        if (misstake > 0) {
            heathBarTest.blow();
        }
        alertBuild.setPositiveButton(getResources().getString(R.string.tinderContinue), (dialogInterface, i) -> nextArtist());
        alertBuild.setOnCancelListener(dialog -> nextArtist());
        AlertDialog alert = alertBuild.create();
        alert.show();
        if (sound) {
            soundPlayer.playSoundStream(longSwitchID);
        }
    }

    private void viewMissTake() {
        allActGuessSolvedRightLay.removeAllViews();
        allActGuessSolvedLeftLay.removeAllViews();
        AllActLayoutUnsLvLay.removeAllViews();
        isViewMissTake = true;
        for (Map.Entry<Artist, String> ansver : ansverMap.entrySet()) {
            RelativeLayout relativeLayout = new RelativeLayout(this);

            ImageView background = new ImageView(this);

            TextView textView = new TextView(this);
            RelativeLayout.LayoutParams layoutParamsText = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParamsText.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParamsText.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            layoutParamsText.setMargins(0, 0, 0, 15);
            textView.setText(String.format(Locale.getDefault(), "%s\n(%s)", ansver.getKey().getName(), ansver.getKey().getGroup()));
            textView.setTextSize(15);
            textView.setGravity(Gravity.CENTER);
            textView.setShadowLayer(4, 2, 2, Color.BLACK);
            textView.setTextColor(Color.WHITE);

            relativeLayout.setLayoutParams(allActLeftSlvPict.getLayoutParams());

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(30, 10, 30, 10);

            // Проверки в какую колонку они определяются
            if (ansver.getValue().equals(first_band.getName())) {
                if (ansver.getKey().checkGroup(first_band.getName())) {
                    background.setBackgroundResource(theme.getColorMissTakeCurrent());
                } else {
                    background.setBackgroundResource(theme.getColorMissTakeError());
                }
                relativeLayout.addView(background, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                ImageView leftPict = new ImageView(this);
                leftPict.setLayoutParams(allActLeftSlvPict.getLayoutParams());
                leftPict.setScaleType(ImageView.ScaleType.MATRIX);
                relativeLayout.addView(leftPict, layoutParams);
                setupImage(ansver.getKey().getFolderNotRandom(), leftPict);
                allActGuessSolvedLeftLay.addView(relativeLayout);
            }
            if (ansver.getValue().equals(second_band.getName())) {
                if (ansver.getKey().checkGroup(second_band.getName())) {
                    background.setBackgroundResource(theme.getColorMissTakeCurrent());
                } else {
                    background.setBackgroundResource(theme.getColorMissTakeError());
                }
                relativeLayout.addView(background, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                ImageView rightPict = new ImageView(this);
                rightPict.setLayoutParams(allActRightSlvPict.getLayoutParams());
                rightPict.setScaleType(ImageView.ScaleType.MATRIX);
                relativeLayout.addView(rightPict, layoutParams);
                setupImage(ansver.getKey().getFolderNotRandom(), rightPict);
                allActGuessSolvedRightLay.addView(relativeLayout);
            }
            relativeLayout.addView(textView, layoutParamsText);
        }
    }

    private void nextArtist() {
        isViewMissTake = false;
        isHint = false;
        layoutHint.setVisibility(View.GONE);
        if (score / 25 > scoreHealth) {
            scoreHealth = score / 25;
            heathBarTest.restore();
        }
        if (score / 25 > scoreHint){
            scoreHint = score / 25;
            hintCount++;
            counterHint.setText(String.format(Locale.getDefault(), "%d", hintCount));
        }
        if (hintCount > 0) {
            hintButton.setBackgroundResource(theme.getBackgroundButton());
        }
        if (heathBarTest.getHp() < 1) {
            alertBuild = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
            alertBuild.setTitle(getResources().getString(R.string.endGameTitle));
            alertBuild.setMessage(getResources().getString(R.string.endGameTextScoreNow, score) + "\n" + getResources().getString(R.string.endGameTextRecordNow, scoreRecord));
            alertBuild.setPositiveButton(getResources().getString(R.string.tinderContinue), (dialogInterface, i) -> {
                score = 0;
                scoreHealth = 0;
                scoreHint = 0;
                hintCount = 3;
                hintCountReward = 3;
                counterHint.setText(String.format(Locale.getDefault(), "%d", hintCount));
                heathBarTest.restartHp();
                nextArtist();
                interstitialShow();
            });
            alertBuild.setNegativeButton(getResources().getString(R.string.tinderLoseScreenExit), (dialog, which) -> finish());
            alertBuild.setOnCancelListener(dialog -> finish());
            AlertDialog alert = alertBuild.create();
            alert.show();
        } else {

            boolean achievemented = false;
            if (score >= 15 && score <= 40) { //ачивка за 15 Условие ачивки
                if (SomeMethods.achievementGetted(TwoBandsTinder.this, R.string.achDistributeByBandsBeginner, R.drawable.devide_bands15, "achSwipeTwoBandsBeginner")) //ачивочка
                {
                    achievemented = true;
                }
            }
            if (score >= 75 && score <= 90) { //ачивка за 75. Условие ачивки
                if (SomeMethods.achievementGetted(TwoBandsTinder.this, R.string.achDistributeByBandsNormal, R.drawable.devide_bands75, "achSwipeTwoBandsNormal")) //ачивочка
                {
                    achievemented = true;
                }
            }
            if (score >= 225 && score <= 260) { //ачивка за 225. Условие ачивки
                if (SomeMethods.achievementGetted(TwoBandsTinder.this, R.string.achDistributeByBandsExpert, R.drawable.devide_bands225, "achSwipeTwoBandsExpert")) //ачивочка
                {
                    achievemented = true;
                }
            }

            if (achievemented && sound) {
                soundPlayer.playSoundStream(grace); //звук ачивки
            }

            twoBandFlip.showNext();
            scoreText.setText(getResources().getString(R.string.endGameTextScoreNow, score));
            scoreRecordText.setText(getResources().getString(R.string.endGameTextRecordNow, scoreRecord));
            if (bandsCount + 3 < bands.size()) {
                bandsCount = bandsCount + 2;
                mainProcedure();
            } else {
                resultsSequence();
            }
        }

    }

    @Override
    public void onBackPressed() {
        //кнопка возврата
        if (twoBandFlip.getDisplayedChild() == twoBandFlip.indexOfChild(findViewById(R.id.relativeLayout)) && !isViewMissTake) {
            if (pictnumb < ansverMap.size()) {
                twoBandFlip.showNext();

                countGroupTextFirstOne.setText(String.format(Locale.getDefault(), "%d/%d", countGroupOne, countGroupMaxOne));
                countGroupTextSecondOne.setText(String.format(Locale.getDefault(), "%d/%d", countGroupTwo, countGroupMaxTwo));
                for (int i = 0; i < ansverMap.size(); i++) {
                    if (Objects.equals(ansverMap.get(artists_turn.get(i)), "null")) {
                        pictNumbCurrent = i;
                        break;
                    }
                }
                setupImage(artists_turn.get(pictNumbCurrent).getFolderNotRandom(), imageBand);
            }
        } else {
            super.onBackPressed();
        }
    }
    //--------------------------------------------------------------------------------------------------

    public void menuFlipEventInstance() { // ивент переворота на экран результата не доделан

        allActGuessSolvedRightLay.removeAllViews();
        allActGuessSolvedLeftLay.removeAllViews();
        AllActLayoutUnsLvLay.removeAllViews();
        // Отображение всех отгаданых типов
        for (Map.Entry<Artist, String> ansver : ansverMap.entrySet()) {
            RelativeLayout relativeLayout = new RelativeLayout(this);

            TextView textView = new TextView(this);
            RelativeLayout.LayoutParams layoutParamsText = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParamsText.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParamsText.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            layoutParamsText.setMargins(0, 0, 0, 15);
            textView.setText(String.format(Locale.getDefault(), "%s", ansver.getKey().getName()));
            textView.setTextSize(15);
            textView.setShadowLayer(4, 2, 2, Color.BLACK);
            textView.setTextColor(Color.WHITE);

            relativeLayout.setLayoutParams(allActLeftSlvPict.getLayoutParams());

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(5, 5, 5, 5);
            //textView.setLayoutParams(layoutParamsText);
            // Проверки в какую колонку они определяются
            if (ansver.getValue().equals(first_band.getName())) {
                ImageView leftPict = new ImageView(this);
                relativeLayout.setOnClickListener(new TouchSwitchImage(artists_turn.indexOf(ansver.getKey()), true, relativeLayout));
                leftPict.setLayoutParams(allActLeftSlvPict.getLayoutParams());
                leftPict.setScaleType(ImageView.ScaleType.MATRIX);
                relativeLayout.addView(leftPict, layoutParams);
                setupImage(ansver.getKey().getFolderNotRandom(), leftPict);
                allActGuessSolvedLeftLay.addView(relativeLayout);
            }
            if (ansver.getValue().equals(second_band.getName())) {
                ImageView rightPict = new ImageView(this);
                relativeLayout.setOnClickListener(new TouchSwitchImage(artists_turn.indexOf(ansver.getKey()), false, relativeLayout));
                rightPict.setLayoutParams(allActRightSlvPict.getLayoutParams());
                rightPict.setScaleType(ImageView.ScaleType.MATRIX);
                relativeLayout.addView(rightPict, layoutParams);
                setupImage(ansver.getKey().getFolderNotRandom(), rightPict);
                allActGuessSolvedRightLay.addView(relativeLayout);
            }
            if (isHint) {
                relativeLayout.addView(textView, layoutParamsText);
            }
        }
        //Не отгаданные типы, см шаблон
        for (final Artist others : artists_turn) {
            if (Objects.equals(ansverMap.get(others), "null")) {
                // инициализация переменных final те которые не изменны, нужны для удаления и добавления
                final CardView mainCard = new CardView(this);
                Button leftButton = new Button(this);
                Button rightButton = new Button(this);
                final ImageView cardImg = new ImageView(this);
                LinearLayout leftCardLay = new LinearLayout(this);
                LinearLayout rightCardLay = new LinearLayout(this);
                final LinearLayout cardLay = new LinearLayout(this);
                TextView textViewHint = new TextView(this);
                CardView.LayoutParams layoutParamsTextHint = new CardView.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParamsTextHint.gravity = Gravity.BOTTOM | Gravity.CENTER;
                layoutParamsTextHint.setMargins(0, 0, 0, 15);
                textViewHint.setTextSize(15);
                textViewHint.setShadowLayer(4, 2, 2, Color.BLACK);
                textViewHint.setTextColor(Color.WHITE);
                textViewHint.setLayoutParams(layoutParamsTextHint);
                textViewHint.setText(others.getName());

                //Отображение параметров взятых из шаблона
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) allActCardView.getLayoutParams();
                layoutParams.setMargins(0, 10, 0, 10);

                mainCard.setLayoutParams(layoutParams);
                leftButton.setLayoutParams(allActLeftCardButton.getLayoutParams());
                rightButton.setLayoutParams(allActRightCardButton.getLayoutParams());

                layoutParams = (LinearLayout.LayoutParams) allActUnsLvPictPict.getLayoutParams();
                layoutParams.weight = 1;
                cardImg.setLayoutParams(layoutParams);

                layoutParams = (LinearLayout.LayoutParams) AllActLayoutCardLeftLay.getLayoutParams();
                layoutParams.weight = 1;
                leftCardLay.setLayoutParams(layoutParams);
                leftCardLay.setOrientation(LinearLayout.VERTICAL);

                layoutParams = (LinearLayout.LayoutParams) AllActLayoutCardRightLay.getLayoutParams();
                layoutParams.weight = 1;
                rightCardLay.setLayoutParams(layoutParams);
                rightCardLay.setOrientation(LinearLayout.VERTICAL);

                cardLay.setLayoutParams(AllActLayoutMainLay.getLayoutParams());
                cardLay.setOrientation(LinearLayout.HORIZONTAL);

                mainCard.setBackgroundResource(theme.getBackgroundCart());
                leftButton.setBackgroundResource(theme.getBackgroundButton());
                rightButton.setBackgroundResource(theme.getBackgroundButton());
                leftButton.setTextColor(theme.getButtonTextColor());
                rightButton.setTextColor(theme.getButtonTextColor());

                //Задача переменных Картинку и путь до папки отправляем в метод он указывает глайду
                leftButton.setText(first_band.getName());
                rightButton.setText(second_band.getName());

                setupImage(others.getFolderNotRandom(), cardImg);

                leftCardLay.setGravity(Gravity.CENTER);
                // разбивка по верстке как в шаблоне
                leftCardLay.addView(leftButton);


                rightCardLay.setGravity(Gravity.CENTER);
                rightCardLay.addView(rightButton);

                //cardLay.setPadding(30,0,30,0);
                cardLay.setWeightSum(3);
                cardLay.setGravity(Gravity.CENTER);
                cardLay.addView(leftCardLay);
                cardLay.addView(cardImg);
                cardLay.addView(rightCardLay);

                mainCard.addView(cardLay);
                if (isHint) {
                    mainCard.addView(textViewHint);
                }
                // добавляет карточку в финальную верстку
                AllActLayoutUnsLvLay.addView(mainCard);
                //при клике на кнопку добавляет ответ в карту ответов, потом чистит карточки чтобы
                //добавить картинку в столбики ответов
                leftButton.setOnClickListener(v -> {
                    if (countGroupOne < countGroupMaxOne) {
                        pictnumb++;
                        countGroupOne++;
                        countGroupTextFirstTwo.setText(String.format(Locale.getDefault(), "%d/%d", countGroupOne, countGroupMaxOne));
                        ansverMap.put(others, first_band.getName());
                        AllActLayoutUnsLvLay.removeView(mainCard);
                        mainCard.removeAllViews();
                        cardLay.removeAllViews();

                        RelativeLayout layout = new RelativeLayout(TwoBandsTinder.this);
                        TextView textView = new TextView(this);
                        RelativeLayout.LayoutParams layoutParamsText = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        layoutParamsText.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                        layoutParamsText.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                        layoutParamsText.setMargins(0, 0, 0, 15);
                        textView.setText(String.format(Locale.getDefault(), "%s", others.getName()));
                        textView.setTextSize(15);
                        textView.setShadowLayer(4, 2, 2, Color.BLACK);
                        textView.setTextColor(Color.WHITE);

                        layout.setLayoutParams(allActLeftSlvPict.getLayoutParams());

                        RelativeLayout.LayoutParams layoutParamsRelative = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                        layoutParamsRelative.setMargins(5, 5, 5, 5);

                        ImageView pict = new ImageView(TwoBandsTinder.this);
                        layout.setOnClickListener(new TouchSwitchImage(artists_turn.indexOf(others), true, layout));
                        pict.setLayoutParams(allActLeftSlvPict.getLayoutParams());
                        pict.setScaleType(ImageView.ScaleType.MATRIX);

                        layout.addView(pict, layoutParamsRelative);
                        setupImage(others.getFolderNotRandom(), pict);
                        allActGuessSolvedLeftLay.addView(layout);
                        if (isHint) {
                            layout.addView(textView, layoutParamsText);
                        }
                    }
                });
                rightButton.setOnClickListener(v -> {
                    if (countGroupTwo < countGroupMaxTwo) {
                        pictnumb++;
                        countGroupTwo++;
                        countGroupTextSecondTwo.setText(String.format(Locale.getDefault(), "%d/%d", countGroupTwo, countGroupMaxTwo));
                        ansverMap.put(others, second_band.getName());
                        AllActLayoutUnsLvLay.removeView(mainCard);
                        mainCard.removeAllViews();
                        cardLay.removeAllViews();

                        RelativeLayout layout = new RelativeLayout(TwoBandsTinder.this);
                        TextView textView = new TextView(this);
                        RelativeLayout.LayoutParams layoutParamsText = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        layoutParamsText.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                        layoutParamsText.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                        layoutParamsText.setMargins(0, 0, 0, 15);
                        textView.setText(String.format(Locale.getDefault(), "%s", others.getName()));
                        textView.setTextSize(15);
                        textView.setShadowLayer(4, 2, 2, Color.BLACK);
                        textView.setTextColor(Color.WHITE);

                        layout.setLayoutParams(allActLeftSlvPict.getLayoutParams());

                        RelativeLayout.LayoutParams layoutParamsRelative = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                        layoutParamsRelative.setMargins(5, 5, 5, 5);

                        ImageView pict = new ImageView(TwoBandsTinder.this);
                        layout.setOnClickListener(new TouchSwitchImage(artists_turn.indexOf(others), false, layout));
                        pict.setLayoutParams(allActLeftSlvPict.getLayoutParams());
                        pict.setScaleType(ImageView.ScaleType.MATRIX);

                        layout.addView(pict, layoutParamsRelative);
                        setupImage(others.getFolderNotRandom(), pict);
                        allActGuessSolvedRightLay.addView(layout);
                        if (isHint) {
                            layout.addView(textView, layoutParamsText);
                        }
                    }
                });
            }
        }
    }

    private void interstitialShow() {
        Storage storage = new Storage(this, "appStatus");
        if (!storage.getBoolean("achTripleExpert")) {
            if (countAd <= 0) {
                countAd = 5;
                mInterstitialAd.show();
            }
            countAd--;
        } else {
            AppMetrica.reportEvent("ads 2.0", "{\"interstitial\":\"tinder\"}");
        }
    }

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
            RelativeLayout layout = new RelativeLayout(TwoBandsTinder.this);
            TextView textView = new TextView(TwoBandsTinder.this);
            RelativeLayout.LayoutParams layoutParamsText = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParamsText.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParamsText.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            layoutParamsText.setMargins(0, 0, 0, 15);
            textView.setText(String.format(Locale.getDefault(), "%s", artists_turn.get(count).getName()));
            textView.setTextSize(15);
            textView.setShadowLayer(4, 2, 2, Color.BLACK);
            textView.setTextColor(Color.WHITE);
            layout.setLayoutParams(allActLeftSlvPict.getLayoutParams());
            RelativeLayout.LayoutParams layoutParamsRelative = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParamsRelative.setMargins(5, 5, 5, 5);
            ImageView pict = new ImageView(TwoBandsTinder.this);
            pict.setLayoutParams(allActLeftSlvPict.getLayoutParams());
            pict.setScaleType(ImageView.ScaleType.MATRIX);
            setupImage(artists_turn.get(count).getFolderNotRandom(), pict);
            layout.addView(pict, layoutParamsRelative);
            if (isHint) {
                layout.addView(textView, layoutParamsText);
            }
            if (isLeft) {
                layout.setOnClickListener(new TouchSwitchImage(count, false, layout));
                allActGuessSolvedLeftLay.removeView(relativeLayout);
                ansverMap.put(artists_turn.get(count), second_band.getName());
                relativeLayout = null;
                allActGuessSolvedRightLay.addView(layout);
                countGroupTextSecondTwo.setText(String.format(Locale.getDefault(), "%d/%d", ++countGroupTwo, countGroupMaxTwo));
                countGroupTextFirstTwo.setText(String.format(Locale.getDefault(), "%d/%d", --countGroupOne, countGroupMaxOne));
            } else {
                layout.setOnClickListener(new TouchSwitchImage(count, true, layout));
                allActGuessSolvedRightLay.removeView(relativeLayout);
                ansverMap.put(artists_turn.get(count), first_band.getName());
                relativeLayout = null;
                allActGuessSolvedLeftLay.addView(layout);
                countGroupTextSecondTwo.setText(String.format(Locale.getDefault(), "%d/%d", --countGroupTwo, countGroupMaxTwo));
                countGroupTextFirstTwo.setText(String.format(Locale.getDefault(), "%d/%d", ++countGroupOne, countGroupMaxOne));
            }

        }
    }

    // класс listener для моего обьекта
    static class OnSwipeTinderListener implements View.OnTouchListener {
        //переменные для положения x
        float dX;
        float defX;
        float dY;
        float defY;
        //переменные для поворота
        float roatx;
        float droatx;
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
            //padding = (ViewGroup.MarginLayoutParams)  v.getLayoutParams();
            v.getDisplay().getMetrics(metrics);
            int width = metrics.widthPixels;
            //формулы для определения в какую сторону была скинута картинка
            leftCheck = (defX < (width / 2f - width + 30));
            rightCheck = (defX > (width / 2f - 30));
            //формула для для вычисление поворота
            roatx = (event.getRawX() / width) * 45 - 17.5f;
            droatx = dX / width * 45 + 17.5f;

            switch (event.getAction()) {
                //обработка события нажатия на экран
                case MotionEvent.ACTION_DOWN:

                    //Log.i("deX", "onTouch:getrawX "+defX+"dX"+dX+ "vgetx "+v.getX());
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
                    v.animate().x(defX).y(defY).rotation(roatx + droatx).start();
                    // Log.i("dX", "onTouch:getrawX "+defX+"dX"+roatx);
                    break;
                // обработка события отпуск от экрана
                case MotionEvent.ACTION_UP:
                    // Log.i("defX", "onTouch:right "+leftCheck+" left"+rightCheck+" padding "+paddingYx);
                    // если картинка не находится слева и справа
                    if (!leftCheck && !rightCheck) {
                        v.animate().setDuration(400).translationX(0).translationY(0).rotation(0);
                    }//v.animate().x(paddingX).y(paddingY).rotation(0);

                    if (leftCheck) {
                        if (countGroupOne != countGroupMaxOne) {
                            v.animate().y(paddingYx);
                            onLeftCheck();
                        } else {
                            v.animate().setDuration(400).translationX(0).translationY(0).rotation(0);
                        }
                    }
                    if (rightCheck) {
                        if (countGroupTwo != countGroupMaxTwo) {
                            v.animate().y(paddingYx);
                            onRightCheck();
                        } else {
                            v.animate().setDuration(400).translationX(0).translationY(0).rotation(0);
                        }
                    }
                    break;
            }
            return true;
        }

        public void onLeftCheck() {
        }

        public void onRightCheck() {
        }
    }
}

