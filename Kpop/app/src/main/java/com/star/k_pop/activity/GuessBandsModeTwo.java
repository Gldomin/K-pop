package com.star.k_pop.activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.star.k_pop.R;
import com.star.k_pop.StartApplication.Importer;
import com.star.k_pop.helper.Theme;
import com.star.k_pop.lib.HeathBar;
import com.star.k_pop.model.Bands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GuessBandsModeTwo extends AppCompatActivity {

    Theme theme;
    HeathBar heathBarTest;

    private int count = 0;
    private String nameGroup;

    private List<Button> buttons;
    private List<Button> buttonsEnd;
    private ArrayList<Bands> artists = Importer.getRandomBands();

    private static final int[] BUTTON_IDS = {
            R.id.lit1, R.id.lit2, R.id.lit3, R.id.lit4, R.id.lit5, R.id.lit6, R.id.lit7, R.id.lit8, R.id.lit9,
            R.id.lit10, R.id.lit11, R.id.lit12, R.id.lit13, R.id.lit14, R.id.lit15, R.id.lit16
    };

    private static final int[] BUTTON_IDS_END = {
            R.id.end1, R.id.end2, R.id.end3, R.id.end4, R.id.end5,
            R.id.end6, R.id.end7, R.id.end8, R.id.end9, R.id.end10
    };

    private ImageView groupPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme = new Theme(this);
        theme.setThemeSecond();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_bands_mode_two);

        groupPhoto = findViewById(R.id.groupPhoto);

        groupPhoto.setBackground(AppCompatResources.getDrawable(this, theme.getBackgroundResource()));

        View.OnClickListener clkGr = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.INVISIBLE);
            }
        };

        View.OnClickListener clkGrEnd = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.INVISIBLE);
            }
        };

        buttons = new ArrayList<>();
        for (int id : BUTTON_IDS) {
            Button button = findViewById(id);
            button.setOnClickListener(clkGr);
            button.setBackgroundResource(theme.getBackgroundResource());
            buttons.add(button);
        }

        buttonsEnd = new ArrayList<>();
        for (int id : BUTTON_IDS_END) {
            Button button = findViewById(id);
            button.setOnClickListener(clkGrEnd);
            button.setBackgroundResource(theme.getBackgroundResource());
            buttonsEnd.add(button);
        }

        createHeathBar();
        change();
    }

    private void createHeathBar() {
        ImageView imageView1 = findViewById(R.id.guessBandHeart1);
        ImageView imageView2 = findViewById(R.id.guessBandHeart2);
        ImageView imageView3 = findViewById(R.id.guessBandHeart3);
        ArrayList<ImageView> imageViewList = new ArrayList<>();
        imageViewList.add(imageView1);
        imageViewList.add(imageView2);
        imageViewList.add(imageView3);

        Animation lifeBrokeAnimation = AnimationUtils.loadAnimation(this, R.anim.heart_broke_animation);
        heathBarTest = new HeathBar(imageViewList, 3, lifeBrokeAnimation);
    }

    private void change() {
        count++;
        if (count >= artists.size() || count < 0) {
            artists = Importer.getRandomBands();
            count = 0;
        }
        for (Button b : buttonsEnd) {
            b.setVisibility(View.INVISIBLE);
        }
        nameGroup = artists.get(count).getNameCorrect();
        int countLetter = nameGroup.length();
        int startButtonNumber = 5 - countLetter / 2;
        for (int i = startButtonNumber; i < startButtonNumber + countLetter; i++) {
            if (nameGroup.charAt(i - startButtonNumber) != ' ')
                buttonsEnd.get(i).setVisibility(View.VISIBLE);
        }
        int[] ref = createRandomButton();
        for (int i = 0; i < ref.length; i++) {
            Log.i("TAGS", "" + ref[i]);
            if (i < countLetter && nameGroup.charAt(i) != ' ') {
                buttons.get(ref[i]).setText("" + nameGroup.charAt(i));
            } else {
                buttons.get(ref[i]).setText("" + (char) ('a' + new Random().nextInt(26)));
            }
        }
        Log.i("TAGS", artists.get(count).getName()); //чит-лог
        Glide.with(this).load(Uri.parse(artists.get(count).getFolderRandom()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(groupPhoto);
    }

    private int[] createRandomButton() {
        List<Integer> fill = new ArrayList<>();
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            fill.add(i);
        }
        Collections.shuffle(fill);
        return convertIntegers(fill);
    }

    public static int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = integers.get(i);
        }
        return ret;
    }
}