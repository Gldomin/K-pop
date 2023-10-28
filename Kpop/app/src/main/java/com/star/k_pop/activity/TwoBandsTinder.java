package com.star.k_pop.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.imageview.ShapeableImageView;
import com.star.k_pop.R;
import com.star.k_pop.StartApplication.Importer;
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

    private LinearLayout AllActlayoutMainLay;
    private LinearLayout AllActlayoutCardLeftLay;
    private LinearLayout AllActlayoutCardRightLay;
    private LinearLayout AllActlayoutUnslvLay;
    private TextView allActLeftCardGroupText;
    private TextView allActRightCardGroupText;
    private Button allActRightCardButton;
    private Button allActLeftCardButton;
    private ImageView allActLeftSlvPict;
    private ImageView allActRightSlvPict;
    private ImageView allActUnslvPictPict;

    private CardView allActCardView;

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

        bands = Importer.getRandomBands();
        Log.i(TAG + "Wrong", "We are here now");
        setContentView(R.layout.activity_two_bands_temp);

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
        TextView scoreText = findViewById(R.id.scoreBands);
        TextView recordText = findViewById(R.id.RecordScore);
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

        layout1.setBackgroundColor(theme.getColorLighter());
        layout2.setBackgroundColor(theme.getColorLighter());
        layout3.setBackgroundColor(theme.getColorLighter());

        scoreText.setTextColor(theme.getTextColor());
        recordText.setTextColor(theme.getTextColor());

        //--------------------------------------------------------------------------------------
        //----------------------------------------------------------------------------------------------
        AllActlayoutMainLay = findViewById(R.id.allActorLLay);
        AllActlayoutCardLeftLay = findViewById(R.id.allAct_Card_LLay);
        AllActlayoutCardRightLay = findViewById(R.id.allAct_Card_RLay);
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
        allActGuessSolvedLeftLay = findViewById(R.id.allActorGuessLeftLay);
        allActGuessSolvedRightLay = findViewById(R.id.allActorGuessRightLay);

        allActCardView = findViewById(R.id.allAct_Card);


        //----------------------not changed
        //нажатие на кнопку меняет окно на другое
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (twoBandFlip != null) {
                    menuFlipEventInstance();
                    twoBandFlip.showNext();

                    countGroupTextFirstTwo.setText(countGroupOne +"/"+ countGroupMaxOne);
                    countGroupTextSecondTwo.setText(countGroupTwo +"/"+ countGroupMaxTwo);
                }
/*
                Animation anim = AnimationUtils.loadAnimation(TwoBandsTinder.this, R.anim.wrong_answer_anim);
                if (checkresult()) {
                    bandsCount = bandsCount + 2;
                    mainProcedure();
                } else {
                    imageBand.startAnimation(anim);
                    losescreen();
                }
*/
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
            @SuppressLint("SetTextI18n")
            public void onLeftCheck() {

                left = true;
                right = false;
                ansverMap.put(artists_turn.get(pictNumbCurrent), first_band.getName());
                countGroupTextFirstOne.setText(++countGroupOne +"/"+ countGroupMaxOne);
                pictnumb++;
                if (pictnumb < ansverMap.size()) {
                    fadeAnimation(true);
                    for (int i =0; i <ansverMap.size();i++){
                        if (ansverMap.get(artists_turn.get(i)).equals("null")){
                            pictNumbCurrent = i;
                            break;
                        }
                    }
                    setupImage(artists_turn.get(pictNumbCurrent).getFolder(), imageBand);
                }
                if (checkresult()) {
                    if (bandsCount + 2 < bands.size()) {
                        bandsCount = bandsCount + 2;
                        mainProcedure();
                    } else resultsSequence();
                }
                if (pictnumb >= ansverMap.size()) {
                    countGroupTextFirstTwo.setText(countGroupOne +"/"+ countGroupMaxOne);
                    countGroupTextSecondTwo.setText(countGroupTwo +"/"+ countGroupMaxTwo);
                    twoBandFlip.showNext();
                    menuFlipEventInstance();
                    imageBand.animate().translationX(0).translationY(0).rotation(0).setDuration(0);
                }
            }

            //проверка свайпа ansvermap получает ответ свайпа,setupimage устанавливает фотки
            @SuppressLint("SetTextI18n")
            public void onRightCheck() {
                left = false;
                right = true;
                ansverMap.put(artists_turn.get(pictNumbCurrent), second_band.getName());
                countGroupTextSecondOne.setText(++countGroupTwo +"/"+ countGroupMaxTwo);
                pictnumb++;
                if (pictnumb < ansverMap.size()) {
                    fadeAnimation(true);
                    for (int i =0; i < ansverMap.size(); i++){
                        Log.e("DEBUGINGGAME", ansverMap.get(artists_turn.get(i)));
                        if (ansverMap.get(artists_turn.get(i)).equals("null")){
                            pictNumbCurrent = i;
                            break;
                        }
                    }
                    setupImage(artists_turn.get(pictNumbCurrent).getFolder(), imageBand);
                }
                if (checkresult()) {
                    if (bandsCount + 2 < bands.size()) {
                        bandsCount = bandsCount + 2;
                        mainProcedure();
                    } else resultsSequence();
                }
                if (pictnumb >= ansverMap.size()) {
                    countGroupTextFirstTwo.setText(countGroupOne +"/"+ countGroupMaxOne);
                    countGroupTextSecondTwo.setText(countGroupTwo +"/"+ countGroupMaxTwo);
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
        left = false;
        right = false;
        mainProcedure();
//        changeBands();
//        changeArtist(false);
    }

    public void mainProcedure() {
        //Главная процедура, запускается начальная последовательность , устанавливается главная картинка, указывается текст групп
        startSequance();
        setupImage(artists_turn.get(pictNumbCurrent).getFolder(), imageBand);
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
        pictNumbCurrent=0;
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


//    Новый метод старый не особо удобен и приходится копировать
    public void setupImage(String pathtoFolder, ImageView img) {
        //установка главной фотки которая перемещается
        {
            //Context contextTheme = new ContextThemeWrapper(this, R.style.roundedCorners);
            Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + pathtoFolder))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transition(withCrossFade())
                    .into(img);
        }
    }

    @SuppressLint("SetTextI18n")
    public void setupBandText() {
        //указываются названия групп и число ответов
        groupNameFirstOne.setText(first_band.getName());
        groupNameSecondOne.setText(second_band.getName());
        groupNameFirstTwo.setText(first_band.getName());
        groupNameSecondTwo.setText(second_band.getName());
        groupNameFirstThree.setText(first_band.getName());
        groupNameSecondThree.setText(second_band.getName());
        countGroupMaxOne =  first_band.getNumberOfPeople();
        countGroupMaxTwo = second_band.getNumberOfPeople();
        countGroupTextFirstOne.setText("0/" + countGroupMaxOne);
        countGroupTextSecondOne.setText("0/" + countGroupMaxTwo);
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
            Log.i(TAG + "Fuck", "when im going here");
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

    public boolean checkresult() {
        //метод проверки результатов, если количество правильных ответов совпадает возвращает правду
        int rightAnsvers = 0;
        if (!ansverMap.containsValue("null")) {
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
            if (!map.getKey().getGroup().equals(map.getValue())) mistakes++;
        }
        return mistakes;
    }

    public void losescreen() {
        //финал пройгрыша
        AlertDialog.Builder alertbuild = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
        alertbuild.setTitle(" Готово ");
        alertbuild.setMessage("У вас " + mistakescount() +" ошибок");
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
            if (pictnumb < ansverMap.size()){
                twoBandFlip.showNext();
                countGroupTextFirstOne.setText(countGroupOne +"/"+ countGroupMaxOne);
                countGroupTextSecondOne.setText(countGroupTwo +"/"+ countGroupMaxTwo);
                for (int i =0; i <ansverMap.size();i++){
                    if (ansverMap.get(artists_turn.get(i)).equals("null")){
                        pictNumbCurrent = i;
                        break;
                    }
                }
                setupImage(artists_turn.get(pictNumbCurrent).getFolder(), imageBand);
            }
        } else {
            super.onBackPressed();
        }
    }
    //--------------------------------------------------------------------------------------------------

    public void menuFlipEventInstance() { // ивент переворота на экран результата не доделан
        //очистка шаблонов ( так надо)
        allActGuessSolvedRightLay.removeAllViews();
        allActGuessSolvedLeftLay.removeAllViews();
        AllActlayoutUnslvLay.removeAllViews();
        // Отображение всех отгаданых типов
        for (Map.Entry<Artist, String> ansver : ansverMap.entrySet()) {
            ImageView leftPict = new ImageView(this);
            ImageView rightPict = new ImageView(this);
            //Параметры отображения этих типов, задаются в шаблонах Set устанавливает их
            leftPict.setLayoutParams(allActLeftSlvPict.getLayoutParams());
            rightPict.setLayoutParams(allActRightSlvPict.getLayoutParams());
            //Чтобы нормально размеры отображались указывает ебучий зум (Надо отстандартить фотки)
            leftPict.setScaleType(ImageView.ScaleType.MATRIX);
            rightPict.setScaleType(ImageView.ScaleType.MATRIX);

            // Проверки в какую колонку они определяются
            if (ansver.getValue().equals(first_band.getName())) {
                setupImage(ansver.getKey().getFolder(), leftPict);
                //LeftPict.setLayoutParams(allActLeftSlvPict.getLayoutParams());
                allActGuessSolvedLeftLay.addView(leftPict);
            }
            if (ansver.getValue().equals(second_band.getName())) {
                setupImage(ansver.getKey().getFolder(), rightPict);
                //RightPict.setLayoutParams();
                allActGuessSolvedRightLay.addView(rightPict);
            }
        }
        //Не отгаданные типы, см шаблон
        for (final Artist others : artists_turn) {
            if (ansverMap.get(others) == "null") {
                // инициализация переменных final те которые не изменны, нужны для удаления и добавления
                final CardView maincard = new CardView(this);
                Button leftButton = new Button(this);
                Button rightButton = new Button(this);
                final ImageView cardimg = new ImageView(this);
                LinearLayout leftCardLay = new LinearLayout(this);
                LinearLayout rightCardLay = new LinearLayout(this);
                final LinearLayout cardLay = new LinearLayout(this);


                //Отображение параметров взятых из шаблона
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) allActCardView.getLayoutParams();
                layoutParams.setMargins(0,10,0,10);

                maincard.setLayoutParams(layoutParams);
                leftButton.setLayoutParams(allActLeftCardButton.getLayoutParams());
                rightButton.setLayoutParams(allActRightCardButton.getLayoutParams());

                layoutParams = (LinearLayout.LayoutParams) allActUnslvPictPict.getLayoutParams();
                layoutParams.weight = 1;
                cardimg.setLayoutParams(layoutParams);

                layoutParams = (LinearLayout.LayoutParams) AllActlayoutCardLeftLay.getLayoutParams();
                layoutParams.weight = 1;
                leftCardLay.setLayoutParams(layoutParams);
                leftCardLay.setOrientation(LinearLayout.VERTICAL);

                layoutParams = (LinearLayout.LayoutParams) AllActlayoutCardRightLay.getLayoutParams();
                layoutParams.weight = 1;
                rightCardLay.setLayoutParams(layoutParams);
                rightCardLay.setOrientation(LinearLayout.VERTICAL);

                cardLay.setLayoutParams(AllActlayoutMainLay.getLayoutParams());
                cardLay.setOrientation(LinearLayout.HORIZONTAL);

                maincard.setBackgroundResource(theme.getBackgroundCart());
                leftButton.setBackgroundResource(theme.getBackgroundButton());
                rightButton.setBackgroundResource(theme.getBackgroundButton());
                leftButton.setTextColor(theme.getTextColor());
                rightButton.setTextColor(theme.getTextColor());

                //Задача переменных Картинку и путь до папки отправляем в метод он указывает глайду
                leftButton.setText(first_band.getName());
                rightButton.setText(second_band.getName());

                setupImage(others.getFolder(), cardimg);

                leftCardLay.setGravity(Gravity.CENTER);
                // разбивка по верстке как в шаблоне
                leftCardLay.addView(leftButton);


                rightCardLay.setGravity(Gravity.CENTER);
                rightCardLay.addView(rightButton);

                //cardLay.setPadding(30,0,30,0);
                cardLay.setWeightSum(3);
                cardLay.setGravity(Gravity.CENTER);
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
                        if (countGroupOne< countGroupMaxOne){
                            pictnumb++;
                            countGroupOne++;
                            countGroupTextFirstTwo.setText(countGroupOne +"/"+ countGroupMaxOne);
                            ansverMap.put(others, first_band.getName());
                            AllActlayoutUnslvLay.removeView(maincard);
                            maincard.removeAllViews();
                            cardLay.removeAllViews();
                            allActGuessSolvedLeftLay.addView(cardimg);
                        }


                    }
                });
                rightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (countGroupTwo<countGroupMaxTwo){
                            pictnumb++;
                            countGroupTwo++;
                            countGroupTextSecondTwo.setText(countGroupTwo +"/"+ countGroupMaxTwo);
                            ansverMap.put(others, second_band.getName());
                            AllActlayoutUnslvLay.removeView(maincard);
                            maincard.removeAllViews();
                            cardLay.removeAllViews();
                            allActGuessSolvedRightLay.addView(cardimg);
                        }
                    }
                });
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
                        if (countGroupOne != countGroupMaxOne){
                            v.animate().y(paddingYx);
                            onLeftCheck();
                        }else{
                            v.animate().setDuration(400).translationX(0).translationY(0).rotation(0);
                        }
                    }
                    if (rightCheck) {
                        if (countGroupTwo != countGroupMaxTwo) {
                            v.animate().y(paddingYx);
                            onRightCheck();
                        }else{
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

