package com.star.k_pop;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.star.k_pop.lib.SomeMethods;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class Achievements extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);


        final ImageView ach1 = findViewById(R.id.ach1);
        final ImageView ach2 = findViewById(R.id.ach2);
        final ImageView ach3 = findViewById(R.id.ach3);
        final ImageView ach4 = findViewById(R.id.ach4);

        SomeMethods.showToast(this, "Достижение открыто!", R.drawable.achievement);

        Button tempButton = findViewById(R.id.button2); //временная штука, потом будем считывать из Хранилища состояния ачивок
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SomeMethods.showToast(Achievements.this, "Достижение открыто - КУБЫ!", R.drawable.cubes);

             /*  ImageView imageView = new ImageView(Achievements.this);
                ConstraintLayout constraintLayout = findViewById(R.id.constraint);
                constraintLayout.addView(imageView);
                Glide.with(Achievements.this).load(getResources().getDrawable(R.drawable.hello))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transition(withCrossFade())
                        .into(imageView);
                Achievements.Alert alert = new Achievements.Alert();
                alert.execute();*/
                //SomeMethods.showAlertDeialog(Achievements.this,"title?","1 or 2","1","2");
                //потом надо будет вместо кнопки сделать функцию обновления ачивок. Если в хранилище будет ачивка True -> открыть картинку. КОнечно же надо будет сначала ачивки сохранить в Хранилище....
                ach1.setImageResource(R.drawable.heart);
                ach2.setImageResource(R.drawable.energy);
                ach3.setImageResource(R.drawable.cubes);
                ach4.setImageResource(R.drawable.star);
            }
        });
    }
}