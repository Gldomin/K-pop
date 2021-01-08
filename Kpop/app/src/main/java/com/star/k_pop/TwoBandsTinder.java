package com.star.k_pop;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.star.k_pop.StartApplication.Importer;

import java.util.ArrayList;
import java.util.Collections;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class TwoBandsTinder extends AppCompatActivity {

    ArrayList<Bands> bands = Importer.getRandomBands(); //берем список всех групп
    ArrayList<Artist> artists = new ArrayList<>(); //лист для того чтобы переносить артистов из двух групп
    int artistCount;
    boolean choice;
    int bandsCount;
    float paddingY;
    float paddingX;
    ImageView imageBand;
    ImageView imBTmp;
    TextView oneBand;
    TextView secondBand;
    TextView artistName;
    boolean left;
    boolean right;
    OnSwipeTouchListener l;
    TextView score;
    ViewGroup.MarginLayoutParams padding;

    OptionsSet tempSettingsSet = new OptionsSet(false, false); //переменная для считывания состояния свиича на darkmod
    String buttonStyleChange = "stylebutton";

    private static final String IMAGEVIEW_TAG = "icon bitmap";
    private void startFinishSection() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
    }


    private void changeBands() {
        Log.i("Test2", "test");
        if (bandsCount >= bands.size()) return;
        if (bandsCount + 1 >= bands.size()) return;
        Log.i("Test2", "bands size" + bands.size());
        oneBand.setText(bands.get(bandsCount).getName());
        secondBand.setText(bands.get(bandsCount + 1).getName());
        artists.addAll(bands.get(bandsCount).getArtists());
        Log.i("Test2", "band artists count" + bands.get(bandsCount).getArtists().size());
        artists.addAll(bands.get(bandsCount + 1).getArtists());
        Log.i("Test2", "artist size" + artists.size());
        Collections.shuffle(artists);
        changeArtist();
        bandsCount = bandsCount + 2;
    }

    private void changeArtist() {
        if (artistCount >= artists.size()) {
            changeBands();
        } else {Log.i("defX", "onTouch:imbTx "+imBTmp.getX()+" imbTy "+imBTmp.getY()+" imagex "+imageBand.getX() + " imagey "+padding.topMargin+" ");
            imageBand.animate().x(padding.leftMargin).y(padding.topMargin).rotation(0).setDuration(0);
            artistName.setText(artists.get(artistCount).getName());
                imBTmp.setVisibility(View.VISIBLE);
                imBTmp.setY(imageBand.getY());
                imBTmp.setX(imageBand.getX());
                imBTmp.setImageDrawable(imageBand.getDrawable());
                imBTmp.setRotation(0);
                imBTmp.setScaleX(1);
                imBTmp.setScaleY(1);
            if(left){
                imBTmp.animate().scaleX(0.3f).scaleY(0.3f).rotation(30).setDuration(400).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        imBTmp.setVisibility(View.GONE);
                    }
                });
            }
            if(right){
                imBTmp.animate().scaleX(0.3f).scaleY(0.3f).rotation(-30).setDuration(400).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        imBTmp.setVisibility(View.GONE);
                    }
                });
            }
            //imBTmp.setVisibility(View.VISIBLE);
            Log.i("defX", "onTouch:imbTx "+imBTmp.animate().getDuration()+" imbTy "+imBTmp.getY()+" imagex "+imageBand.getX() + " imagey "+paddingY+" ");

            Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + artists.get(artistCount).getFolder()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transition(withCrossFade())
                    .into(imageBand);
            artistCount++;
            score.setText("" + artistCount);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void guessTwoBands() {
        imageBand = findViewById(R.id.imageBand);
        imBTmp =    findViewById(R.id.imgBTmp);
        oneBand = findViewById(R.id.oneBand);
        secondBand = findViewById(R.id.secondBand);
        score = findViewById(R.id.RecordScore);
        artistName = findViewById(R.id.artistName);
        padding =(ViewGroup.MarginLayoutParams) imageBand.getLayoutParams();
        bandsCount = 0;
        artistCount = 0;
        imageBand.setTag(IMAGEVIEW_TAG);
        if (artists != null) artists.clear();
        choice = false;

        imageBand.setOnTouchListener(new OnSwipeTinderListener() {

            public boolean onRightCheck() {
                right = true;
                left = false;
                Log.i("Swipe", "onSwipeRight");
                changeArtist();
                return true;
            }
            public boolean onLeftCheck() {
                right = false;
                left = true;
                Log.i("Swipe", "onSwipeLeft");
                changeArtist();
                return true;
            }
        });
        changeBands();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String nameOfStorage2 = "settings";
        Storage storage2 = new Storage(this);
        tempSettingsSet.darkMode = storage2.getBoolean(nameOfStorage2, "darkMode"); //считываем состояние
        //теперь выбираем тему в зависимости от положения свича
        if (tempSettingsSet.darkMode==true) setTheme(R.style.AppTheme2);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_bands_tinder);
        guessTwoBands();
    }
}

// класс listener для моего обьекта
class OnSwipeTinderListener implements View.OnTouchListener {
    //переменные для положения x
    float dX; float defX;
    float dY; float defY;
    //переменные для поворота
    float roatx; float droatx;
    //переменные для проверки финального условия
    boolean leftCheck; boolean rightCheck;
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
        final int paddingX = (width - v.getWidth())/2;
        //формулы для определения в какую сторону была скинута картинка
        leftCheck = (defX < (width/2 - width +30));
        rightCheck = (defX > (width/2 - 30));
        //формула для для вычисление поворота
        roatx = (event.getRawX()/width)*45-17.5f;
        droatx = dX/width*45+17.5f;


        switch (event.getAction()) {
            //обработка события нажатия на экран
            case MotionEvent.ACTION_DOWN:

                Log.i("deX", "onTouch:getrawX "+defX+"dX"+dX+ "vgetx "+v.getX());
                //задаем значение dx равное обратной позиции нажатия на экран, чтобы картинка не сьехала
                dY = v.getY() - event.getRawY();
                dX = v.getX() - event.getRawX();
                paddingYx = v.getY();
                break;

            //обработка события проведение по экрану
            case MotionEvent.ACTION_MOVE:
                // движение картинки так как dx назначается в начале,то defx будет двигать только картинку от ее начальной позиции
                defX = dX+event.getRawX();
                defY = dY+event.getRawY();
                //droat и roat та же логика
                ObjectAnimator b = new ObjectAnimator();
                v.animate().setDuration(0);
                v.animate().x( defX).y(defY).rotation(roatx+droatx).start();
                Log.i("dX", "onTouch:getrawX "+defX+"dX"+roatx);
                break;
            // обработка события отпуск от экрана
            case  MotionEvent.ACTION_UP:
                Log.i("defX", "onTouch:right "+leftCheck+" left"+rightCheck+" padding "+paddingYx);
                // если картинка не находится слева и справа
                if (!leftCheck&&!rightCheck){v.animate().setDuration(400).x(paddingX).y(paddingYx).rotation(0);}//v.animate().x(paddingX).y(paddingY).rotation(0);

                if (leftCheck){v.animate().y(paddingYx);
                            onLeftCheck();
                }
                if (rightCheck) {v.animate().y(paddingYx);

                    onRightCheck();
                }
                break;
        }
        return true;
    }
    public boolean onLeftCheck(){return false;}
    public boolean onRightCheck(){return false;}
}
