package com.exam.k_pop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
            @Override
            public boolean onSwipeDown() {
                finish();
                return true;
            }
        });


    }
}