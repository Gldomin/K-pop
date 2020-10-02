package com.star.k_pop;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
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
    ImageView imageBand;
    ImageView imBTmp;
    TextView oneBand;
    TextView secondBand;
    OnSwipeTouchListener l;
    TextView score;
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
        } else {
            imageBand.animate().x(imageBand.getPaddingLeft()).y(imageBand.getPaddingTop()).rotation(0).setDuration(0);

            imBTmp.setX(imageBand.getX());
            imBTmp.setY(imageBand.getY());
            imBTmp.setImageDrawable(imageBand.getDrawable());
            //imBTmp.setVisibility(View.VISIBLE);
            Log.i("defX", "onTouch:imbTx "+imBTmp.getX()+" imbTy "+imBTmp.getY()+" imagex "+imageBand.getX() + " imagey "+imageBand.getY()+" ");

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
        bandsCount = 0;
        artistCount = 0;
        imageBand.setTag(IMAGEVIEW_TAG);
        if (artists != null) artists.clear();
        choice = false;

        imageBand.setOnTouchListener(new OnSwipeTinderListener() {

            public boolean onRightCheck() {
                Log.i("Swipe", "onSwipeRight");

                changeArtist();
                return true;
            }
            public boolean onLeftCheck() {
                Log.i("Swipe", "onSwipeLeft");
                changeArtist();
                return true;
            }
        });
        changeBands();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        //получаем размер экрана чтобы различать в какую сторону скинули
        DisplayMetrics metrics = new DisplayMetrics();
        v.getDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        //размер границы картинки от левого края
        final int paddingX = (width - v.getWidth())/2;
        final float paddingY = v.getY();
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
                break;
            //обработка события проведение по экрану
            case MotionEvent.ACTION_MOVE:
                // движение картинки так как dx назначается в начале,то defx будет двигать только картинку от ее начальной позиции
                defX = dX+event.getRawX();
                defY = dY+event.getRawY();
                //droat и roat та же логика
                 v.animate().x( defX).y(defY).rotation(roatx+droatx).setDuration(0).start();
                Log.i("dX", "onTouch:getrawX "+defX+"dX"+roatx);
                break;
            // обработка события отпуск от экрана
            case  MotionEvent.ACTION_UP:
                Log.i("defX", "onTouch:right "+leftCheck+" left"+rightCheck);

                // если картинка не находится слева и справа
                if (!leftCheck&&!rightCheck)v.animate().x(paddingX).y(paddingY).rotation(0);

                if (leftCheck){
                    v.animate().x(defX).y(defY).rotation(-360).setDuration(600);
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            onLeftCheck();

                        }
                    },300);

                }
                if (rightCheck) {
                    v.animate().x(defX).rotation(360).setDuration(600);
                    onRightCheck();
                }
                break;
        }
        return true;
    }
    public boolean onLeftCheck(){return false;}
    public boolean onRightCheck(){return false;}
}
