package com.star.k_pop.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
    private final int NO_BAND = 0;
    private final int FIRST_BAND = 1;
    private final int SECOND_BAND = 2;
    //-------------------------------------------------------------------------------------------------
    private final ArrayList<Bands> bands = Importer.getRandomBands(); //берем список всех групп
    private final ArrayList<Artist> artists = new ArrayList<>(); //лист для того чтобы переносить артистов из двух групп
    private final ArrayList<String> imgList = new ArrayList<>();
    private boolean conformChoices;
    private ArrayList<String> artChoices;
    private int bandsCount;

    private GridLayout gridFirst;
    private GridLayout gridSecond;


    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;


    private ImageView imageBand;
    private ImageView imBTmp;
    private TextView oneBand;
    private TextView secondBand;
    private TextView artistName;
    private ViewFlipper twoBandFlip;
    private byte number_of_artist;
    private boolean left;
    private boolean right;
    private TextView score;
    private final String nonChoice = "NaN";

    //--------------------------------------------------------------------------------------------------
    Bands first_band;
    Bands second_band;
    ArrayList<Artist> artists_turn;
    Map<Artist, String> ansverMap;
    private int pictnumb;
//--------------------------------------------------------------------------------------------------

    private static final String IMAGEVIEW_TAG = "icon bitmap";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Theme theme = new Theme(this);
        theme.setThemeSecond();

        super.onCreate(savedInstanceState);
        Log.i(TAG + "Wrong", "We are here now");
        setContentView(R.layout.acitivity_two_bands_temp);

        ImageButton chooseActorButton = findViewById(R.id.ttChoseActorButton);
        ImageButton confirmButton = findViewById(R.id.ttConfirmButton);

        imageBand = findViewById(R.id.imageBand);
        imBTmp = findViewById(R.id.imgBTmp);
        oneBand = findViewById(R.id.oneBand);
        secondBand = findViewById(R.id.secondBand);
        score = findViewById(R.id.RecordScore);
        artistName = findViewById(R.id.artistName);
        twoBandFlip = findViewById(R.id.twoBandFlipper);
        gridFirst = findViewById(R.id.ttGridFGroupLayout);
        gridSecond = findViewById(R.id.ttGridSGroupLayout);
        recyclerView1 = findViewById(R.id.groupLeft);
        recyclerView2 = findViewById(R.id.groupRight);
//----------------------not changed
        chooseActorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (twoBandFlip != null) {
                    twoBandFlip.showNext();
                }
                //switchOnBands();
            }
        });
//----------------------not changed
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(TwoBandsTinder.this, R.anim.wrong_answer_anim);
                if (checkresult()) {
                    bandsCount = bandsCount + 2;
                    mainProcedure();
                } else {
                    imageBand.startAnimation(anim);
                    losescreen();
                }
            }
        });


//-----------------------now working----------------------------------------------------------------------
        imageBand.setOnTouchListener(new OnSwipeTinderListener() {


            public void onLeftCheck() {
                left = true;
                right = false;
                ansverMap.put(artists_turn.get(pictnumb), first_band.getName());
                if (pictnumb < ansverMap.size() - 1) {
                    pictnumb++;
                    fadeAnimation(true);
                    setupImage(pictnumb);
                }
                if (checkresult()) {
                    if (bandsCount + 2 < bands.size()) {
                        bandsCount = bandsCount + 2;
                        mainProcedure();
                    } else resultsSequence();
                }
            }

            public void onRightCheck() {
                left = false;
                right = true;
                ansverMap.put(artists_turn.get(pictnumb), second_band.getName());
                if (pictnumb < ansverMap.size() - 1) {
                    pictnumb++;
                    fadeAnimation(true);
                    setupImage(pictnumb);
                }
                if (checkresult()) {
                    if (bandsCount + 2 < bands.size()) {
                        bandsCount = bandsCount + 2;
                        mainProcedure();
                    } else resultsSequence();
                }
            }
        });
//--------------------------------------------------------------------------------------------------

//
//
//        imageBand.setOnTouchListener(new OnSwipeTinderListener() {
//
//            public void onRightCheck() {
//                left = false;
//                right = true;
//                if (number_of_artist <= artists.size()) {
//                    changeArtist(true);
//                }
//            }
//
//            public void onLeftCheck() {
//                left = true;
//                right = false;
//
//                if (number_of_artist <= artists.size()) {
//                    changeArtist(true);
//                }
//
//            }
//        });

        guessTwoBands();
    }

    //--------------------------------------------------------------------------------------------------


    private void guessTwoBands() {

        imageBand.setTag(IMAGEVIEW_TAG);

        bandsCount = 0;
        conformChoices = false;
        if (artists != null) {
            artists.clear();
        }

        left = false;
        right = false;
        mainProcedure();
//        changeBands();
//        changeArtist(false);
    }

    public void mainProcedure() {
        startSequance();
        setupImage(pictnumb);
        setupBandText();

        TinderAdapter mAdapter1 = new TinderAdapter(getApplicationContext(), ansverMap, artists_turn, first_band.getName());
        TinderAdapter mAdapter2 = new TinderAdapter(getApplicationContext(), ansverMap, artists_turn, second_band.getName());

        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView1.setLayoutManager(mLayoutManager1);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(mAdapter1);

        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView2.setLayoutManager(mLayoutManager2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(mAdapter2);

    }

    public void startSequance() {
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
    }

    public void setupImage(int numbpict) {
        if (numbpict + 1 <= artists_turn.size()) {
            Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + artists_turn.get(numbpict).getFolder()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transition(withCrossFade())
                    .into(imageBand);
            artistName.setText(artists_turn.get(numbpict).getName());
        }
    }

    public void setupBandText() {
        oneBand.setText(first_band.getName());
        secondBand.setText(second_band.getName());
    }

    public void fadeAnimation(boolean anim) {
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
        int rightAnsvers = 0;
        if (!ansverMap.values().contains("null")) {
            for (Map.Entry<Artist, String> art : ansverMap.entrySet()) {
                if (art.getKey().checkGroup(art.getValue())) rightAnsvers++;
            }
        }
        if (rightAnsvers == ansverMap.size()) {
            return true;
        }
        return false;
    }

    public void ttClickCheck(View view) {
        if (checkresult()) {

            if (bandsCount + 2 < bands.size()) {
                bandsCount = bandsCount + 2;
                mainProcedure();
            } else resultsSequence();
        }
        else losescreen();
    }

    public void resultsSequence() {
        bandsCount = 0;
        Collections.shuffle(bands);
        mainProcedure();
    }

    private int mistakescount() {
        int mistakes = 0;
        for (Map.Entry<Artist, String> map : ansverMap.entrySet()) {
            if (map.getKey().getGroup() == map.getValue()) mistakes++;
        }
        return mistakes;
    }

    public void losescreen() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(" Готово ");
        alert.setMessage("вы совершили " + mistakescount());
        alert.setPositiveButton("окей", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (bandsCount + 2 < bands.size()) {
                    bandsCount = bandsCount + 2;
                    mainProcedure();
                } else resultsSequence();
            }
        });
    }
    //--------------------------------------------------------------------------------------------------

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
    //@Override
//    public void onBackPressed() {
//        if (twoBandFlip.getDisplayedChild() == twoBandFlip.indexOfChild(findViewById(R.id.relativeLayout))) {
//            twoBandFlip.showNext();
//        } else {
//            super.onBackPressed();
//        }
//    }
//
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


