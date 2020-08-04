package com.exam.k_pop;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GuesGroup extends AppCompatActivity {




    private View button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_group);
        final ImageView groupPhoto = findViewById(R.id.groupPhoto);
        Glide.with(this).load(Uri.parse("file:///android_asset/Groups/BTS/Jimin/Jimin.png"))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(groupPhoto);



        Button btnQ = findViewById(R.id.litQ);
        Button btnW = findViewById(R.id.litW);
        Button btnE = findViewById(R.id.litE);
        Button btnR = findViewById(R.id.litR);
        Button btnT = findViewById(R.id.litT);
        Button btnY = findViewById(R.id.litY);
        Button btnU = findViewById(R.id.litU);
        Button btnI = findViewById(R.id.litI);
        Button btnO = findViewById(R.id.litO);
        Button btnP = findViewById(R.id.litP);
        Button btnA = findViewById(R.id.litA);
        Button btnS = findViewById(R.id.litS);
        Button btnD = findViewById(R.id.litD);
        Button btnF = findViewById(R.id.litF);
        Button btnG = findViewById(R.id.litG);
        Button btnH = findViewById(R.id.litH);
        Button btnJ = findViewById(R.id.litJ);
        Button btnK = findViewById(R.id.litK);
        Button btnL = findViewById(R.id.litL);
        Button btnZ = findViewById(R.id.litZ);
        Button btnX = findViewById(R.id.litX);
        Button btnC = findViewById(R.id.litC);
        Button btnV = findViewById(R.id.litV);
        Button btnB = findViewById(R.id.litB);
        Button btnN = findViewById(R.id.litN);
        Button btnM = findViewById(R.id.litM);
        Button btnEnt = findViewById(R.id.litEnt);

        final EditText grName= findViewById(R.id.groupName);

        OnClickListener clkGr =new OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.litEnt:
                init();
                 grName.setText("Ent", TextView.BufferType.EDITABLE);
                break;
                case R.id.litQ:
                init();
                break;
            }
        }

    };
        btnQ.setOnClickListener(clkGr);
        btnEnt.setOnClickListener(clkGr);
    }

    private void init() {

        final ImageView groupPhoto = findViewById(R.id.groupPhoto);
        Glide.with(this).load(Uri.parse("file:///android_asset/Groups/BTS/RM/RM.png"))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(groupPhoto);
    }

}
