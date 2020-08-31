package com.exam.k_pop;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
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
    TextView score;

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

    private void guessTwoBands() {
        imageBand = findViewById(R.id.imageBand);
        oneBand = findViewById(R.id.oneBand);
        secondBand = findViewById(R.id.secondBand);
        score = findViewById(R.id.RecordScore);
        bandsCount = 0;
        artistCount = 0;
        if (artists != null) artists.clear();

        choice = false;
        imageBand.setOnClickListener(new View.OnClickListener() { //включение/выключение читов при нажатии на фотку
            @Override
            public void onClick(View view) {
                changeArtist();
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