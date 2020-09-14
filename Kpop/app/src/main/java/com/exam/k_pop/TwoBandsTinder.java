package com.exam.k_pop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.exam.k_pop.StartApplication.Importer;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class TwoBandsTinder extends AppCompatActivity {

    ArrayList<Bands> bands = Importer.getRandomBands();
    ArrayList<Artist> artists = new ArrayList<>();
    int artistCount;
    boolean choice;
    int bandsCount;
    ImageView imageBand;
    TextView oneBand;
    TextView secondBand;
    OnSwipeTouchListener l;
    TextView score;
    private static final String IMAGEVIEW_TAG = "icon bitmap";
    private void startFinishSection() {

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
        oneBand = findViewById(R.id.oneBand);
        secondBand = findViewById(R.id.secondBand);
        score = findViewById(R.id.RecordScore);
        bandsCount = 0;
        artistCount = 0;
        imageBand.setTag(IMAGEVIEW_TAG);
        if (artists != null) artists.clear();
        choice = false;

        imageBand.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item(""+v.getTag());
                ClipData dragData = new ClipData(
                        ""+v.getTag(),
                        new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN },
                        item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);

                // Starts the drag

                v.startDrag(dragData,  // the data to be dragged
                        myShadow,  // the drag shadow builder
                        null,      // no need to use local data
                        0          // flags (not currently used, set to 0)
                );
                return false;
            }

        });
        imageBand.setOnTouchListener(new OnSwipeTinderListener() {

            public boolean onSwipeRight() {
                Log.i("Swipe", "onSwipeRight");

                //changeArtist();
                return true;
            }
            public boolean onSwipeLeft() {
                Log.i("Swipe", "onSwipeLeft");
                //changeArtist();
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
    float dX; float defX;
    @Override
    public boolean onTouch(final View v, final MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                defX = v.getX();
                Log.i("deX", "onTouch:getrawX "+event.getRawX()+"dX"+dX+ "vgetx "+v.getX());
                dX = v.getX() - event.getRawX();
                // dY = v.getY() - event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:

                v.animate()
                        .x(event.getRawX() + dX).setDuration(0).start();
                Log.i("dX", "onTouch:getrawX "+event.getRawX()+"dX"+dX+v.getX());
//                        .y(event.getRawY() + dY)
//                        .setDuration(0)
//                        .start();
                break;
            case  MotionEvent.ACTION_UP:
                Log.i("defX", "onTouch:DefX "+defX);
                if (v.getX()<20) v.animate().x(defX);
                if (v.getX()>v.getLeft()-20) v.animate().x(defX);
                break;
        }
        return true;
    }
}
