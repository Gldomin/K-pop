package com.star.k_pop.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.star.k_pop.R;
import com.star.k_pop.StartApplication.Importer;
import com.star.k_pop.helper.Theme;
import com.star.k_pop.model.Artist;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GuessBandEasy extends AppCompatActivity {

    Theme theme;
    ImageView photo1, photo2, photo3, photo4;
    ArrayList<Artist> artists = Importer.getRandomArtists();
    int count = 0;
    int score;

    private List<Button> buttons;

    private static final int[] BUTTON_IDS = {
            R.id.pole1,R.id.pole2,R.id.pole3,R.id.pole4,R.id.pole5,R.id.pole6,R.id.pole7,R.id.pole8,R.id.pole9,R.id.pole10,R.id.pole11,R.id.pole12,
            R.id.key1,R.id.key2,R.id.key3,R.id.key4,R.id.key5,R.id.key6,R.id.key7,R.id.key8,R.id.key9,R.id.key10,R.id.key11,R.id.key11, R.id.hintGes2, R.id.about2
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theme = new Theme(this);
        theme.setThemeSecond();
        setContentView(R.layout.activity_guess_bands_easy);

        photo1 = findViewById(R.id.photo1);

        photo1.setBackground(getResources().getDrawable(theme.getBackgroundResource()));


        change();

        View.OnClickListener clkGr = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            switch (view.getId()) {
                case R.id.key1:

                    break;
            }
            }
        };




    }
    private void change() {
        if (count >= artists.size()) {
            artists = Importer.getRandomArtists();
            count = 0;
        }
        Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + artists.get(count).getFolder()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(photo1);

    }

}