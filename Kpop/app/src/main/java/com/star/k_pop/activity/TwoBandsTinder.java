package com.star.k_pop.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.cardview.widget.CardView;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.google.android.material.imageview.ShapeableImageView;
import com.star.k_pop.R;
import com.star.k_pop.StartApplication.Importer;
import com.star.k_pop.adapter.TinderAdapter;
import com.star.k_pop.helper.Theme;
import com.star.k_pop.model.Artist;
import com.star.k_pop.model.Bands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class TwoBandsTinder extends AppCompatActivity {

    private final String TAG = "TWO BANDS ";

    //-------------------------------------------------------------------------------------------------
    private final ArrayList<Bands> bands = Importer.getRandomBands(); //берем список всех групп
    private boolean conformChoices;
    private int bandsCount;

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;

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
    private byte number_of_artist;
    private boolean left;
    private boolean right;
    private TextView scoreText;
    private TextView recordText;
    private final String nonChoice = "NaN";

    private String countGroupMaxOne;
    private String countGroupMaxTwo;
    private int countGroupOne;
    private int countGroupTwo;

    Theme theme;

    //--------------------------------------------------------------------------------------------------
    Bands first_band;
    Bands second_band;
    ArrayList<Artist> artists_turn;
    Map<Artist, String> ansverMap;
    private int pictnumb;
    //--------------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    private ScrollView allActScrollViewLay;
    private GridLayout allActGridLayout;
    private LinearLayout AllActlayoutMainLay;
    private LinearLayout AllActlayoutCardLeftLay;
    private LinearLayout AllActlayoutCardRightLay;
    private LinearLayout AllActlayoutCardBaseLay;
    private LinearLayout AllActlayoutUnslvLay;
    private TextView allActLeftCardGroupText;
    private TextView allActRightCardGroupText;
    private Button allActRightCardButton;
    private Button allActLeftCardButton;
    private ImageView allActLeftSlvPict;
    private ImageView allActRightSlvPict;
    private ImageView allActUnslvPictPict;
    private Space allActSpacer;

    private CardView allActCardView;

    private LinearLayout allActGuessSolvedLay;
    private LinearLayout allActGuessSolvedLeftLay;
    private LinearLayout allActGuessSolvedRightLay;

    //----------------------------------------------------------------------------------------------

    private static final String IMAGEVIEW_TAG = "icon bitmap";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    //инициализация смотрите ебанный xml
    protected void onCreate(Bundle savedInstanceState) {
        theme = new Theme(this);
        theme.setThemeSecond();

        super.onCreate(savedInstanceState);
        Log.i(TAG + "Wrong", "We are here now");
        setContentView(R.layout.acitivity_two_bands_temp);

        Button confirmButton = findViewById(R.id.ttConfirmButton);
        Button endButton = findViewById(R.id.allAct_Accept);
        ImageButton helpButton = findViewById(R.id.helpTindButton);
        ImageButton hintButton = findViewById(R.id.podsk);

        confirmButton.setBackgroundResource(theme.getBackgroundButton());
        endButton.setBackgroundResource(theme.getBackgroundButton());
        helpButton.setBackgroundResource(theme.getBackgroundButton());
        hintButton.setBackgroundResource(theme.getBackgroundButton());

        imageBand = findViewById(R.id.imageBand);
        imBTmp = findViewById(R.id.imgBTmp);
        scoreText = findViewById(R.id.scoreBands);
        recordText = findViewById(R.id.RecordScore);
        twoBandFlip = findViewById(R.id.twoBandFlipper);
        //recyclerView1 = findViewById(R.id.groupLeft);
        //recyclerView2 = findViewById(R.id.groupRight);

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

        layout1.setBackgroundColor(theme.getColorLighter());
        layout2.setBackgroundColor(theme.getColorLighter());
        layout3.setBackgroundColor(theme.getColorLighter());

        scoreText.setTextColor(theme.getTextColor());
        recordText.setTextColor(theme.getTextColor());

        //--------------------------------------------------------------------------------------
        allActScrollViewLay = findViewById(R.id.allActorsScrlVw);
        AllActlayoutMainLay = findViewById(R.id.allActorLLay);
        //allActGridLayout = findViewById(R.id.allActor_Resolve_GridLay);
        AllActlayoutCardLeftLay = findViewById(R.id.allAct_Card_LLay);
        AllActlayoutCardRightLay = findViewById(R.id.allAct_Card_RLay);
        AllActlayoutCardBaseLay = findViewById(R.id.allAct_CardLay);
        AllActlayoutUnslvLay = findViewById(R.id.allAct_UnslvFold);
        allActLeftCardGroupText = findViewById(R.id.allAct_Unslv_LGrpText);
        allActRightCardGroupText = findViewById(R.id.allAct_Unslv_RGrpText);
        allActRightCardButton = findViewById(R.id.allAct_Card_RightABtn);
        allActLeftCardButton = findViewById(R.id.allAct_Card_LeftABtn);
        allActLeftSlvPict = findViewById(R.id.allActGuessLeftPict);
        allActRightSlvPict = findViewById(R.id.allActGuessRightPict);
        allActUnslvPictPict = findViewById(R.id.allAct_Unslv_Pict);
        //allActSpacer = findViewById(R.id.allActSpacer);
        //--------------------------------------------------------------------------------------
        allActGuessSolvedLay = findViewById(R.id.allActorGuessLayout);
        allActGuessSolvedLeftLay = findViewById(R.id.allActorGuessLeftLay);
        allActGuessSolvedRightLay = findViewById(R.id.allActorGuessRightLay);

        allActCardView = findViewById(R.id.allAct_Card);



        //----------------------not changed
        //нажатие на кнопку меняет окно на другое
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (twoBandFlip != null) {
                    menuFlipEventInstance();
                    twoBandFlip.showNext();

                    countGroupTextFirstTwo.setText(countGroupOne + countGroupMaxOne);
                    countGroupTextSecondTwo.setText(countGroupTwo + countGroupMaxTwo);
                }
//                Animation anim = AnimationUtils.loadAnimation(TwoBandsTinder.this, R.anim.wrong_answer_anim);
//                if (checkresult()) {
//                    bandsCount = bandsCount + 2;
//                    mainProcedure();
//                } else {
//                    imageBand.startAnimation(anim);
//                    losescreen();
//                }
            }
        });


        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image = new Intent();
                image.setClass(TwoBandsTinder.this, BasicNotice.class);
                image.putExtra("text", R.string.twoBandsTinderGameModeAbaut);
                image.putExtra("title", R.string.gameModeAbaut);
                startActivity(image);
            }
        });


//-----------------------working----------------------------------------------------------------------
        imageBand.setOnTouchListener(new OnSwipeTinderListener() {
            //проверка свайпа ansvermap получает ответ свайпа,setupimage устанавливает фотки
            public void onLeftCheck() {

                left = true;
                right = false;
                ansverMap.put(artists_turn.get(pictnumb), first_band.getName());
                countGroupTextFirstOne.setText(++countGroupOne + countGroupMaxOne);
                if (pictnumb < ansverMap.size() - 1) {
                    pictnumb++;
                    fadeAnimation(true);
                    setupImage(artists_turn.get(pictnumb).getFolder(),imageBand);
                }
                if (checkresult()) {
                    if (bandsCount + 2 < bands.size()) {
                        bandsCount = bandsCount + 2;
                        mainProcedure();
                    } else resultsSequence();
                }
                if(pictnumb==ansverMap.size()-1)
                {
                    countGroupTextFirstTwo.setText(countGroupOne + countGroupMaxOne);
                    countGroupTextSecondTwo.setText(countGroupTwo + countGroupMaxTwo);
                    twoBandFlip.showNext();
                    menuFlipEventInstance();
                    imageBand.animate().translationX(0).translationY(0).rotation(0).setDuration(0);
                }
            }
            //проверка свайпа ansvermap получает ответ свайпа,setupimage устанавливает фотки
            public void onRightCheck() {
                left = false;
                right = true;
                ansverMap.put(artists_turn.get(pictnumb), second_band.getName());
                countGroupTextSecondOne.setText(++countGroupTwo + countGroupMaxTwo);
                if (pictnumb < ansverMap.size() - 1) {
                    pictnumb++;
                    fadeAnimation(true);
                    setupImage(artists_turn.get(pictnumb).getFolder(),imageBand);
                }
                if (checkresult()) {
                    if (bandsCount + 2 < bands.size()) {
                        bandsCount = bandsCount + 2;
                        mainProcedure();
                    } else resultsSequence();
                }
                if(pictnumb==ansverMap.size()-1)
                {
                    countGroupTextFirstTwo.setText(countGroupOne + countGroupMaxOne);
                    countGroupTextSecondTwo.setText(countGroupTwo + countGroupMaxTwo);
                    twoBandFlip.showNext();
                    menuFlipEventInstance();
                    imageBand.animate().translationX(0).translationY(0).rotation(0).setDuration(0);
                }
            }
        });
        guessTwoBands();
    }

    //--------------------------------------------------------------------------------------------------


    private void guessTwoBands() {
        //Инициализация Основного Метода, указывается Количество груп,и обнуляются свайпы
        imageBand.setTag(IMAGEVIEW_TAG);

        bandsCount = 0;
        conformChoices = false;

        left = false;
        right = false;
        mainProcedure();
//        changeBands();
//        changeArtist(false);
    }

    public void mainProcedure() {
        //Главная процедура, запускается начальная последовательность , устанавливается главная картинка, указывается текст групп
        startSequance();
        setupImage(artists_turn.get(pictnumb).getFolder(),imageBand);
        setupBandText();
        //адаптеры на
        //TinderAdapter mAdapter1 = new TinderAdapter(getApplicationContext(), ansverMap, artists_turn, first_band.getName());
        //TinderAdapter mAdapter2 = new TinderAdapter(getApplicationContext(), ansverMap, artists_turn, second_band.getName());

//        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(getApplicationContext(), 1);
//        recyclerView1.setLayoutManager(mLayoutManager1);
//        recyclerView1.setItemAnimator(new DefaultItemAnimator());
//        recyclerView1.setAdapter(mAdapter1);
//
//        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), 1);
//        recyclerView2.setLayoutManager(mLayoutManager2);
//        recyclerView2.setItemAnimator(new DefaultItemAnimator());
//        recyclerView2.setAdapter(mAdapter2);

    }

    public void startSequance() {
        //ОсновнаяПоследовательностьРежима берутся две группы,создается лист артистов из двух групп обнуляются карты ответов , перемешиваются артисты
        //
        pictnumb = 0;
        first_band = bands.get(bandsCount);
        second_band = bands.get(bandsCount + 1);
        artists_turn = new ArrayList<Artist>();
        ansverMap = new HashMap<Artist, String>();
        artists_turn.addAll(first_band.getArtists());
        artists_turn.addAll(second_band.getArtists());
        Collections.shuffle(artists_turn);
        for (Artist i : artists_turn) {
            ansverMap.put(i, "null");
        }
        countGroupOne = 0;
        countGroupTwo = 0;
    }

//    public void setupImage(int numbpict) {
//        //установка главной фотки которая перемещается
//        if (numbpict + 1 <= artists_turn.size()) {
//            //Context contextTheme = new ContextThemeWrapper(this, R.style.roundedCorners);
//            Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + artists_turn.get(numbpict).getFolder()))
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//
//                    .transition(withCrossFade())
//                    .into(imageBand);
//        }
//    }
//    Новый метод старый не особо удобен и приходится копировать
    public void setupImage(String pathtoFolder,ImageView img) {
        //установка главной фотки которая перемещается
       {
            //Context contextTheme = new ContextThemeWrapper(this, R.style.roundedCorners);
            Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + pathtoFolder))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transition(withCrossFade())
                    .into(img);
        }
    }
    public void setupBandText() {
        //указываются названия групп и число ответов
        groupNameFirstOne.setText(first_band.getName());
        groupNameSecondOne.setText(second_band.getName());
        groupNameFirstTwo.setText(first_band.getName());
        groupNameSecondTwo.setText(second_band.getName());
        groupNameFirstThree.setText(first_band.getName());
        groupNameSecondThree.setText(second_band.getName());
        countGroupMaxOne = "/" + first_band.getNumberOfPeople();
        countGroupMaxTwo = "/" + second_band.getNumberOfPeople();
        countGroupTextFirstOne.setText("0" + countGroupMaxOne);
        countGroupTextSecondOne.setText("0" + countGroupMaxTwo);
    }

    public void fadeAnimation(boolean anim) {
        //анимация затухания, чтобы картинки красиво улетали imbTmp временная картинка которая испаряется, т.к иначе нельзя
        if (pictnumb + 1 < ansverMap.size())
            imageBand.animate().translationX(0).translationY(0).rotation(0).setDuration(0);
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
            Log.i(TAG + "Fuck", "when im going here");
            imBTmp.animate().scaleX(0.3f).scaleY(0.3f).rotation(30).alpha(0.1f).setDuration(400).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    imBTmp.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    public boolean checkresult() {
        //метод проверки результатов, если количество правильных ответов совпадает возвращает правду
        int rightAnsvers = 0;
        if (!ansverMap.values().contains("null")) {
            for (Map.Entry<Artist, String> art : ansverMap.entrySet()) {
                if (art.getKey().checkGroup(art.getValue())) rightAnsvers++;
            }
        }
        return rightAnsvers == ansverMap.size();
    }

    public void ttClickCheck(View view) {
        //не доделан кнопка финальная, на втором экране
        twoBandFlip.showNext();
        if (checkresult()) {
            if (bandsCount + 2 < bands.size()) {
                bandsCount = bandsCount + 2;
                mainProcedure();
            } else {
                resultsSequence();
            }
        } else {
            losescreen();
        }
    }

    public void resultsSequence() {
        //финальная последовательность, запускается после нее основная
        bandsCount = 0;
        Collections.shuffle(bands);
        mainProcedure();
    }

    private int mistakescount() {
        //показывает количество ошибок
        int mistakes = 0;
        for (Map.Entry<Artist, String> map : ansverMap.entrySet()) {
            if (map.getKey().getGroup() != map.getValue()) mistakes++;
        }
        return mistakes;
    }

    public void losescreen() {
        //финал пройгрыша
        AlertDialog.Builder alertbuild = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
        alertbuild.setTitle(" Готово ");
        alertbuild.setMessage("вы совершили " + mistakescount());
        alertbuild.setPositiveButton("окей", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (bandsCount + 2 < bands.size()) {
                    bandsCount = bandsCount + 2;
                    mainProcedure();
                } else resultsSequence();
            }
        });
        AlertDialog alert = alertbuild.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        //кнопка возврата
        if (twoBandFlip.getDisplayedChild() == twoBandFlip.indexOfChild(findViewById(R.id.relativeLayout))) {
            twoBandFlip.showNext();
        } else {
            super.onBackPressed();
        }
    }
    //--------------------------------------------------------------------------------------------------

    public void menuFlipEventInstance()
    { // ивент переворота на экран результата не доделан
        int i =0;
        int j = 2;
        int artcount=0;
        //очистка шаблонов ( так надо)
        allActGuessSolvedRightLay.removeAllViews();
        allActGuessSolvedLeftLay.removeAllViews();
        AllActlayoutUnslvLay.removeAllViews();
        // Отображение всех отгаданых типов
        for(Map.Entry<Artist,String> ansver: ansverMap.entrySet())
        {
            ImageView leftPict = new ImageView(this);
            ImageView rightPict = new ImageView(this);
            //Параметры отображения этих типов, задаются в шаблонах Set устанавливает их
            leftPict.setLayoutParams(allActLeftSlvPict.getLayoutParams());
            rightPict.setLayoutParams(allActRightSlvPict.getLayoutParams());
            //Чтобы нормально размеры отображались указывает ебучий зум (Надо отстандартить фотки)
            leftPict.setScaleType(ImageView.ScaleType.MATRIX);
            rightPict.setScaleType(ImageView.ScaleType.MATRIX);

            // Проверки в какую колонку они определяются
            if(ansver.getValue().equals(first_band.getName())){
                setupImage(ansver.getKey().getFolder(),leftPict);
                //LeftPict.setLayoutParams(allActLeftSlvPict.getLayoutParams());
                allActGuessSolvedLeftLay.addView(leftPict);
            }
            if(ansver.getValue().equals(second_band.getName())){
                setupImage(ansver.getKey().getFolder(),rightPict);
                //RightPict.setLayoutParams();
                allActGuessSolvedRightLay.addView(rightPict);
            }

        }
        //Не отгаданные типы, см шаблон
        for(final Artist others: artists_turn)
        {
            if (ansverMap.get(others)=="null")
            {
                // инициализация переменных final те которые не изменны, нужны для удаления и добавления
                final CardView maincard = new CardView(this);
                Button leftButton = new Button(this);
                Button rightButton = new Button(this);
                TextView leftGroupName = new TextView(this);
                TextView rightGroupName = new TextView(this);
                final ImageView cardimg = new ImageView(this);
                LinearLayout leftCardLay = new LinearLayout(this);
                LinearLayout rightCardLay = new LinearLayout(this);
                final LinearLayout cardLay = new LinearLayout(this);



                //Отображение параметров взятых из шаблона
                maincard.setLayoutParams(allActCardView.getLayoutParams());
                leftButton.setLayoutParams(allActLeftCardButton.getLayoutParams());
                rightButton.setLayoutParams(allActRightCardButton.getLayoutParams());
                leftGroupName.setLayoutParams(allActLeftCardGroupText.getLayoutParams());
                rightGroupName.setLayoutParams(allActRightCardGroupText.getLayoutParams());
                cardimg.setLayoutParams(allActUnslvPictPict.getLayoutParams());
                leftCardLay.setLayoutParams(AllActlayoutCardLeftLay.getLayoutParams());
                leftCardLay.setOrientation(LinearLayout.VERTICAL);
                rightCardLay.setLayoutParams(AllActlayoutCardRightLay.getLayoutParams());
                rightCardLay.setOrientation(LinearLayout.VERTICAL);
                cardLay.setLayoutParams(AllActlayoutMainLay.getLayoutParams());
                cardLay.setOrientation(LinearLayout.HORIZONTAL);


                //Задача переменных Картинку и путь до папки отправляем в метод он указывает глайду
                leftGroupName.setText(first_band.getName());
                rightGroupName.setText(second_band.getName());

                setupImage(others.getFolder(),cardimg);

                // разбивка по верстке как в шаблоне
                leftCardLay.addView(leftGroupName);
                leftCardLay.addView(leftButton);

                rightCardLay.addView(rightGroupName);
                rightCardLay.addView(rightButton);

                cardLay.addView(leftCardLay);
                cardLay.addView(cardimg);
                cardLay.addView(rightCardLay);

                maincard.addView(cardLay);

                // добавляет карточку в финальную верстку
                AllActlayoutUnslvLay.addView(maincard);
                //при клике на кнопку добавляет ответ в карту ответов, потом чистит карточки чтобы
                //добавить картинку в столбики ответов
                leftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ansverMap.put(others, first_band.getName());
                        AllActlayoutUnslvLay.removeView(maincard);
                        maincard.removeAllViews();
                        cardLay.removeAllViews();
                        allActGuessSolvedLeftLay.addView(cardimg);
                    }
                });
                rightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ansverMap.put(others, second_band.getName());
                        AllActlayoutUnslvLay.removeView(maincard);
                        maincard.removeAllViews();
                        cardLay.removeAllViews();
                        allActGuessSolvedRightLay.addView(cardimg);
                    }
                });
            }
        }

    }


    //-------------------------------------------------------------------------------------------------
//    private void startFinishSection() {
//        Display display = getWindowManager().getDefaultDisplay();
//        DisplayMetrics metricsB = new DisplayMetrics();
//        display.getMetrics(metricsB);
//    }
//
//    private void switchOnBands() {
//
//        GridLayout chooseActLay = findViewById(R.id.ttChoseGroupGLay);
//        if (chooseActLay != null) {
//            chooseActLay.removeAllViews();
//        }
//        for (int i = 0; i < artists.size(); i++) {
//            View view = LayoutInflater.from(this).inflate(R.layout.two_bands_group_layout, chooseActLay, false);
//            LinearLayout cardLayot = view.findViewById(R.id.cardLayout);
//            ImageView actorImage = view.findViewById(R.id.cardImage);
//            TextView actorName = view.findViewById(R.id.cardNameTop);
//            TextView GroupName = view.findViewById(R.id.cardNameBottom);
//            if (!artChoices.get(i).equals(nonChoice)) {
//                GroupName.setText(artChoices.get(i));
//            } else {
//                GroupName.setText("");
//            }
//            actorName.setText(artists.get(i).getName());
//            Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + imgList.get(i)))
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .transition(withCrossFade())
//                    .into(actorImage);
//            final int finalI = i;
//            if (chooseActLay != null) {
//                chooseActLay.addView(view);
//                chooseActLay.computeScroll();
//            }
//            cardLayot.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    number_of_artist = (byte) finalI;
//                    twoBandFlip.showNext();
//                    resetPosition();
//                    changeArtist(false);
//                }
//            });
//        }
//    }
//
//    private void changeBands() {
//        if (bandsCount >= bands.size()) {
//            return;
//        }
//        if (bandsCount + 1 >= bands.size()) {
//            return;
//        }
//        Log.i(TAG + "Test2", "bands size" + bands.size());
//        oneBand.setText(bands.get(bandsCount).getName());
//        secondBand.setText(bands.get(bandsCount + 1).getName());
//        if (artists.size() != 0) {
//            artists.clear();
//        }
//        if (imgList.size() != 0) {
//            imgList.clear();
//        }
//        artists.addAll(bands.get(bandsCount).getArtists());
//        Log.i(TAG + "Test2", "band artists count" + bands.get(bandsCount).getArtists().size());
//        artists.addAll(bands.get(bandsCount + 1).getArtists());
//        if (gridFirst.getChildCount() != 0) {
//            gridFirst.removeAllViews();
//        }
//        if (gridSecond.getChildCount() != 0) {
//            gridSecond.removeAllViews();
//        }
//
//        for (byte i = 0; i <= bands.get(bandsCount).getNumberOfPeople(); i++) {
//            ImageView gridBox = new ImageView(this);
//            gridBox.setImageResource(R.drawable.tt_rect_empty);
//            gridFirst.addView(gridBox);
//        }
//        for (byte i = 0; i <= bands.get(bandsCount + 1).getNumberOfPeople(); i++) {
//            ImageView gridBox = new ImageView(this);
//            gridBox.setImageResource(R.drawable.tt_rect_empty);
//            gridSecond.addView(gridBox);
//        }
//        Log.i(TAG + "Test2", "artist size" + artists.size());
//        Collections.shuffle(artists);
//        number_of_artist = 0;
//        artChoices = new ArrayList<>(artists.size());
//        for (Artist art : artists) {
//            artChoices.add(nonChoice);
//            imgList.add(art.getFolder());
//        }
//        if (conformChoices) changeArtist(false);
//        bandsCount = bandsCount + 2;
//    }
//
//    private boolean groupCheck(ArrayList<String> playerChoice) {
//        if (playerChoice.size() == artists.size()) {
//            byte rightGuesses = 0;
//            for (int i = 0; i <= artists.size() - 1; i++) {
//                Log.i(TAG + "tWheck", "Shit is not workin group is " + artists.get(i).getName().trim() + " and answer " + playerChoice.get(i).trim());
//                if (artists.get(i).getGroup().trim().equals(playerChoice.get(i).trim())) {
//                    rightGuesses++;
//                }
//            }
//            Log.i(TAG + "tWheck", "Shit is not workin rightGuesses" + rightGuesses + " and guys are " + artists.size());
//            return rightGuesses == playerChoice.size();
//        }
//        return false;
//    }

//
//    private void changeArtist(boolean animate) {
//        if (number_of_artist < artists.size()) {
//            if (number_of_artist + 1 < artists.size())
//                Log.i(TAG + "Wrong", "WTF " + artists.get(number_of_artist).getName());
//            imageBand.animate().translationX(0).translationY(0).rotation(0).setDuration(0);
//            if (animate) {
//                imBTmp.setVisibility(View.VISIBLE);
//                imBTmp.setY(imageBand.getY());
//                imBTmp.setX(imageBand.getX());
//                imBTmp.setImageDrawable(imageBand.getDrawable());
//                imBTmp.setRotation(0);
//                imBTmp.setScaleX(1);
//                imBTmp.setScaleY(1);
//                imBTmp.setAlpha(1.0f);
//            }
//            if (right) {
//                imBTmp.animate().scaleX(0.3f).scaleY(0.3f).rotation(-30).alpha(0.1f).setDuration(400).setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        imBTmp.setVisibility(View.INVISIBLE);
//                    }
//                });
//
//                artChoices.set(number_of_artist, secondBand.getText().toString());
//                Log.i(TAG + "Wrong", "" + number_of_artist);
//                number_of_artist++;
//            }
//            if (left) {
//                Log.i(TAG + "Fuck", "when im going here");
//                imBTmp.animate().scaleX(0.3f).scaleY(0.3f).rotation(30).alpha(0.1f).setDuration(400).setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        imBTmp.setVisibility(View.INVISIBLE);
//                    }
//                });
//                artChoices.set(number_of_artist, oneBand.getText().toString());
//                Log.i(TAG + "Wrong", "" + number_of_artist);
//                number_of_artist++;
//            }
//            //imBTmp.setVisibility(View.VISIBLE);
//            Log.i(TAG + "defX", "onTouch:imbTx " + imBTmp.animate().getDuration() + " imbTy " + imBTmp.getY() + " imagex " + imageBand.getX() + " imagey ");
//            if (number_of_artist + 1 <= artists.size()) {
//                Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + imgList.get(number_of_artist)))
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .transition(withCrossFade())
//                        .into(imageBand);
//                artistName.setText(artists.get(number_of_artist).getName());
//                score.setText(String.format("%s", number_of_artist));
//            }
//        }
//    }

    private void resetPosition() {
        left = false;
        right = false;
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
                        v.animate().y(paddingYx);
                        onLeftCheck();
                    }
                    if (rightCheck) {
                        v.animate().y(paddingYx);
                        onRightCheck();
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


