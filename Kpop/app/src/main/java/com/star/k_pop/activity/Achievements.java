package com.star.k_pop.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.star.k_pop.R;
import com.star.k_pop.helper.Storage;
import com.star.k_pop.helper.Theme;

public class Achievements extends AppCompatActivity {

    Theme theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        theme = new Theme(this);
        theme.setThemeSecond();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        final ImageView ach1 = findViewById(R.id.ach1);
        final ImageView ach2 = findViewById(R.id.ach2);
        final ImageView ach3 = findViewById(R.id.ach3);
        final ImageView ach4 = findViewById(R.id.ach4);
        final ImageView ach5 = findViewById(R.id.ach5);
        final ImageView ach6 = findViewById(R.id.ach6);
        final ImageView ach7 = findViewById(R.id.ach7);
        final ImageView ach8 = findViewById(R.id.ach8);
        final ImageView ach9 = findViewById(R.id.ach9);
        final ImageView ach10 = findViewById(R.id.ach10);
        final ImageView ach11 = findViewById(R.id.ach11);
        final TextView ach1BigText = findViewById(R.id.ach1BigText);
        final TextView ach2BigText = findViewById(R.id.ach2BigText);
        final TextView ach3BigText = findViewById(R.id.ach3BigText);
        final TextView ach4BigText = findViewById(R.id.ach4BigText);
        final TextView ach5BigText = findViewById(R.id.ach5BigText);
        final TextView ach6BigText = findViewById(R.id.ach6BigText);
        final TextView ach7BigText = findViewById(R.id.ach7BigText);
        final TextView ach8BigText = findViewById(R.id.ach8BigText);
        final TextView ach9BigText = findViewById(R.id.ach9BigText);
        final TextView ach10BigText = findViewById(R.id.ach10BigText);
        final TextView ach11BigText = findViewById(R.id.ach11BigText);
        final TextView ach1SmallText = findViewById(R.id.ach1SmallText);
        final TextView ach2SmallText = findViewById(R.id.ach2SmallText);
        final TextView ach3SmallText = findViewById(R.id.ach3SmallText);
        final TextView ach4SmallText = findViewById(R.id.ach4SmallText);
        final TextView ach5SmallText = findViewById(R.id.ach5SmallText);
        final TextView ach6SmallText = findViewById(R.id.ach6SmallText);
        final TextView ach7SmallText = findViewById(R.id.ach7SmallText);
        final TextView ach8SmallText = findViewById(R.id.ach8SmallText);
        final TextView ach9SmallText = findViewById(R.id.ach9SmallText);
        final TextView ach10SmallText = findViewById(R.id.ach10SmallText);
        final TextView ach11SmallText = findViewById(R.id.ach11SmallText);
        ach1BigText.setTextColor(theme.getTextColor());
        ach2BigText.setTextColor(theme.getTextColor());
        ach3BigText.setTextColor(theme.getTextColor());
        ach4BigText.setTextColor(theme.getTextColor());
        ach5BigText.setTextColor(theme.getTextColor());
        ach6BigText.setTextColor(theme.getTextColor());
        ach7BigText.setTextColor(theme.getTextColor());
        ach8BigText.setTextColor(theme.getTextColor());
        ach9BigText.setTextColor(theme.getTextColor());
        ach10BigText.setTextColor(theme.getTextColor());
        ach11BigText.setTextColor(theme.getTextColor());
        ach1SmallText.setTextColor(theme.getTextColor());
        ach2SmallText.setTextColor(theme.getTextColor());
        ach3SmallText.setTextColor(theme.getTextColor());
        ach4SmallText.setTextColor(theme.getTextColor());
        ach5SmallText.setTextColor(theme.getTextColor());
        ach6SmallText.setTextColor(theme.getTextColor());
        ach7SmallText.setTextColor(theme.getTextColor());
        ach8SmallText.setTextColor(theme.getTextColor());
        ach9SmallText.setTextColor(theme.getTextColor());
        ach10SmallText.setTextColor(theme.getTextColor());
        ach11SmallText.setTextColor(theme.getTextColor());

        Storage storage = new Storage(this, "appStatus");

        if (storage.getBoolean("achGuessStarBeginner"))
            ach1.setImageResource(R.drawable.guess_star10);
        if (storage.getBoolean("achGuessStarNormal")) //почему-то R.drawable.achievement слишком большая
            ach1.setImageResource(R.drawable.guess_star50);
        if (storage.getBoolean("achGuessStarExpert"))
            ach2.setImageResource(R.drawable.guess_star150);
        if (storage.getBoolean("achGuessBandsModeTwoBeginner"))
            ach3.setImageResource(R.drawable.guess_band5);
        if (storage.getBoolean("achGuessBandsModeTwoNormal"))
            ach3.setImageResource(R.drawable.guess_band25);
        if (storage.getBoolean("achGuessBandsModeTwoExpert"))
            ach4.setImageResource(R.drawable.guess_band75);
        if (storage.getBoolean("achSwipeTwoBandsBeginner"))
            ach5.setImageResource(R.drawable.devide_bands5);
        if (storage.getBoolean("achSwipeTwoBandsNormal"))
            ach5.setImageResource(R.drawable.devide_bands25);
        if (storage.getBoolean("achSwipeTwoBandsExpert"))
            ach6.setImageResource(R.drawable.devide_bands75);
        if (storage.getBoolean("achTripleExpert"))
            ach10.setImageResource(R.drawable.kpoplove);
        if (storage.getBoolean("achRoyal"))
            ach11.setImageResource(R.drawable.adept);

        //SomeMethods.showToast(this, "Достижение открыто!", R.drawable.achievement);

    }
}