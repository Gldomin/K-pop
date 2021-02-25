package com.star.k_pop.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.star.k_pop.R;
import com.star.k_pop.StartApplication.Importer;
import com.star.k_pop.helper.Storage;
import com.star.k_pop.helper.Theme;
import com.star.k_pop.model.Artist;
import com.star.k_pop.model.Bands;

import java.util.ArrayList;
import java.util.Collections;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class TwoBandsTinder extends AppCompatActivity {

    ArrayList<Bands> bands = Importer.getRandomBands(); //берем список всех групп
    ArrayList<Artist> artists = new ArrayList<>(); //лист для того чтобы переносить артистов из двух групп
    ArrayList<String> imgList = new ArrayList<>();
    boolean menuChanged , conformChoices;
    int artistCount;
    ArrayList<String> artChoices;
    int bandsCount;
    float paddingY;
    float paddingX;
    ImageView imageBand;
    ImageView imBTmp;
    TextView oneBand;
    TextView secondBand;
    TextView artistName;
    ViewFlipper twoBandFlip;
    ImageButton chooseActorButton, podsk, podsk2;
    Button confirm;
    byte number_of_artist;
    boolean left;
    boolean right;
    TextView score;
    final String nonChoice = "NaN";
    ViewGroup.MarginLayoutParams padding;
    Storage storage2 = new Storage(this, "settings");


    Theme theme;

    private static final String IMAGEVIEW_TAG = "icon bitmap";

    private void startFinishSection() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
    }

    private void switchOnBands() {

        GridLayout chooseActLay = findViewById(R.id.ttChoseGroupGLay);
        if(chooseActLay!=null) chooseActLay.removeAllViews();
        for(int i = 0; i < artists.size();i++)
        {
            View view = LayoutInflater.from(this).inflate(R.layout.two_bands_group_layout, null, false);
            LinearLayout cardLayot = view.findViewById(R.id.cardLayout);
            ImageView actorImage = view.findViewById(R.id.cardImage);
            TextView actorName = view.findViewById(R.id.cardNameTop);
            TextView GroupName = view.findViewById(R.id.cardNameBottom);
            if(artChoices.get(i) != nonChoice){
                GroupName.setText(artChoices.get(i));
            }
            else {
                GroupName.setText("");
            }
            actorName.setText(artists.get(i).getName());
            TextView groupName = view.findViewById(R.id.cardNameBottom);
//            groupName.setText(artChoices.get(i).toString());
            Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + imgList.get(i)))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transition(withCrossFade())
                    .into(actorImage);
            final int finalI = i;
            chooseActLay.addView(view);
            chooseActLay.computeScroll();
            cardLayot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    number_of_artist = (byte) finalI;
                    twoBandFlip.showNext();
                    resetPosition();
                    menuChanged = true;
                    changeArtist();
                }
            });
        }
    }

    public void onButtonGroupPress(View view)
    {
        if (twoBandFlip != null) twoBandFlip.showNext();
        switchOnBands();
    }

    public void onButtonConfirmPress(View view)
    {
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.wrong_answer_anim);
        if(groupCheck(artChoices)) {
            changeBands();
            resetPosition();
            number_of_artist = 0;
            changeArtist();
        }
        else {
            imageBand.startAnimation(anim);

        }
    }

    private void changeBands() {
        if (bandsCount >= bands.size()) return;
        if (bandsCount + 1 >= bands.size()) return;
        Log.i("Test2", "bands size" + bands.size());
        oneBand.setText(bands.get(bandsCount).getName());
        secondBand.setText(bands.get(bandsCount + 1).getName());
        if (artists.size() != 0) artists.clear();
        if (imgList.size() != 0) imgList.clear();
        artists.addAll(bands.get(bandsCount).getArtists());
        Log.i("Test2", "band artists count" + bands.get(bandsCount).getArtists().size());
        artists.addAll(bands.get(bandsCount + 1).getArtists());

        Log.i("Test2", "artist size" + artists.size());
        Collections.shuffle(artists);
        number_of_artist = 0;
        artChoices = new ArrayList<>(artists.size());
        for(Artist art: artists){
            artChoices.add(nonChoice);
            imgList.add(art.getFolder());
        }
        if (conformChoices) changeArtist();
        bandsCount = bandsCount + 2;
    }

    private boolean groupCheck(ArrayList<String> playerChoice) {
        if (playerChoice.size() == artists.size()) {
            byte rightGuesses = 0;
            for (int i = 0; i <= artists.size() - 1; i++) {
                Log.i("tWheck", "Shit is not workin group is " + artists.get(i).getName().trim() + " and answer " + playerChoice.get(i).trim());
                if (artists.get(i).getGroups().trim() == playerChoice.get(i).trim()) rightGuesses++;
            }
            Log.i("tWheck", "Shit is not workin rightGuesses" + rightGuesses + " and guys are " + artists.size());
            if (rightGuesses == playerChoice.size()) return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if(twoBandFlip.getDisplayedChild() == twoBandFlip.indexOfChild(findViewById(R.id.twoBandsGroup))) {twoBandFlip.showNext();}
        else{super.onBackPressed();}
    }

    private void changeArtist() {


        if (number_of_artist < artists.size()) {

            if (number_of_artist + 1 < artists.size())
                Log.i("Wrong", "WTF " + artists.get(number_of_artist).getName());
            imageBand.animate().translationX(0).translationY(0).rotation(0).setDuration(0);
            if(!menuChanged)
            {
                if(number_of_artist +1 <= artists.size()) {
                    imBTmp.setVisibility(View.VISIBLE);
                    imBTmp.setY(imageBand.getY());
                    imBTmp.setX(imageBand.getX());
                    imBTmp.setImageDrawable(imageBand.getDrawable());
                    imBTmp.setRotation(0);
                    imBTmp.setScaleX(1);
                    imBTmp.setScaleY(1);
                    imBTmp.setAlpha(1.0f);
                }
            }
            if (left) {
                Log.i("Fuck", "when im going here");
                imBTmp.animate().scaleX(0.3f).scaleY(0.3f).rotation(30).alpha(0.1f).setDuration(400).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        imBTmp.setVisibility(View.GONE);
                    }
                });
                String choice = secondBand.getText()+"";
                artChoices.set(number_of_artist,oneBand.getText().toString());
                Log.i("Wrong", "" + number_of_artist);
                number_of_artist++;
            }
            if (right) {

                imBTmp.animate().scaleX(0.3f).scaleY(0.3f).rotation(-30).alpha(0.1f).setDuration(400).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        imBTmp.setVisibility(View.GONE);
                    }
                });
                String choice = secondBand.getText()+"";
                artChoices.set(number_of_artist,secondBand.getText().toString());
                Log.i("Wrong", "" + number_of_artist);
                number_of_artist++;
            }
            //imBTmp.setVisibility(View.VISIBLE);
            Log.i("defX", "onTouch:imbTx " + imBTmp.animate().getDuration() + " imbTy " + imBTmp.getY() + " imagex " + imageBand.getX() + " imagey " + paddingY + " ");
            if (number_of_artist + 1 <= artists.size()) {
                Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + imgList.get(number_of_artist)))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transition(withCrossFade())
                        .into(imageBand);
                artistName.setText(artists.get(number_of_artist).getName());
                score.setText("" + number_of_artist);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void guessTwoBands() {

        imageBand = findViewById(R.id.imageBand);
        imBTmp = findViewById(R.id.imgBTmp);
        oneBand = findViewById(R.id.oneBand);
        secondBand = findViewById(R.id.secondBand);
        score = findViewById(R.id.RecordScore);
        artistName = findViewById(R.id.artistName);
        twoBandFlip = findViewById(R.id.twoBandFlipper);
        chooseActorButton = findViewById(R.id.ttChoseActorButton);
        padding = (ViewGroup.MarginLayoutParams) imageBand.getLayoutParams();
        imageBand.setTag(IMAGEVIEW_TAG);
        bandsCount = 0;
        artistCount = 0;
        menuChanged = false;
        conformChoices = false;
        if (artists != null) artists.clear();
        imageBand.setOnTouchListener(new OnSwipeTinderListener() {
            public boolean onRightCheck() {
                left = false;
                right = true;
                if (number_of_artist <= artists.size()) changeArtist();
                return true;
            }

            public boolean onLeftCheck() {
                left = true;
                right = false;
                menuChanged = true ;
                if (number_of_artist <= artists.size()) changeArtist();

                return true;
            }
        });
        left = false;
        right = false;
        changeBands();
        changeArtist();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme = new Theme(this);
        theme.setThemeSecond();

        super.onCreate(savedInstanceState);
        Log.i("Wrong", "We are here now");
        setContentView(R.layout.acitivity_two_bands_temp);

        podsk = findViewById(R.id.hintTind);
        podsk2 = findViewById(R.id.hintTind_dark);
        confirm = findViewById(R.id.ttConfirmButton);

        confirm.setBackgroundResource(theme.getBackgroundResource());
        if (theme.isDarkMode()) {
            podsk.setVisibility(View.INVISIBLE);
            podsk2.setVisibility(View.VISIBLE);
        }
        else {
            podsk.setVisibility(View.VISIBLE);
            podsk2.setVisibility(View.INVISIBLE);
        }

        guessTwoBands();



    }

    private void resetPosition(){
        left = false;
        right = false;
    }


    // класс listener для моего обьекта
    class OnSwipeTinderListener implements View.OnTouchListener {
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
        float b;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public boolean onTouch(final View v, final MotionEvent event) {
            //получаем размер экрана чтобы различать в какую сторону скинули
            DisplayMetrics metrics = new DisplayMetrics();
            //padding = (ViewGroup.MarginLayoutParams)  v.getLayoutParams();
            v.getDisplay().getMetrics(metrics);
            int width = metrics.widthPixels;
            //размер границы картинки от левого края
            final int paddingX = (width - v.getWidth()) / 2;
            //формулы для определения в какую сторону была скинута картинка
            leftCheck = (defX < (width / 2 - width + 30));
            rightCheck = (defX > (width / 2 - 30));
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
                    ObjectAnimator b = new ObjectAnimator();
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

        public boolean onLeftCheck() {
            return false;
        }

        public boolean onRightCheck() {
            return false;
        }
    }
}


