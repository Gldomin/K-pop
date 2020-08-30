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
    private void startFinishSection()
    {

    }
    private void guessTwoBands()
    {
        ImageView imageBand = findViewById(R.id.imageBand);
        TextView oneBand = findViewById(R.id.oneBand);
        TextView secondBand = findViewById(R.id.secondBand);

        if (artists!=null) artists.clear();
        int bandsCount = bands.size();
        for ( int i=0; i<bandsCount; i=i+1)
        {
            if(bands.get(i)==null) {
                startFinishSection();
                break;
            }
            Log.i("TAG"," "+i+" bands "+artists.size());
            if(bands!=null)artists.addAll(bands.get(i).getArtists());
            if(i+1>=bandsCount) {
                startFinishSection();
                break;
            }
            if(bands!=null)artists.addAll(bands.get(i+1).getArtists());
            oneBand.setText(bands.get(i).getName());
            secondBand.setText(bands.get(i+1).getName());
            Collections.shuffle(artists);
            for(Artist art : artists)
            {
                Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + art.getNamesImages()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transition(withCrossFade())
                        .into(imageBand);
                imageBand.setOnClickListener(new View.OnClickListener() { //включение/выключение читов при нажатии на фотку
                    @Override
                    public void onClick(View view) {
                    }
                });
            }
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_bands_tinder);
        guessTwoBands();
    }
}