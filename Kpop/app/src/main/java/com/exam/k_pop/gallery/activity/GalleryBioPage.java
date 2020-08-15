package com.exam.k_pop.gallery.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.exam.k_pop.OnSwipeTouchListener;
import com.exam.k_pop.R;

public class GalleryBioPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_bio_page);

        ImageView tempImg = findViewById(R.id.tempImg);
        tempImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tempImg.setOnTouchListener(new OnSwipeTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onSwipeDown() {
                finish();
                overridePendingTransition(R.anim.alpha_on, R.anim.bottom_on);
                return true;
            }
        });


    }
}