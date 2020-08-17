package com.exam.k_pop;

import android.app.Activity;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
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

import java.io.IOException;
import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GuesGroup extends AppCompatActivity {



    ArrayList<Artist> artists = new ArrayList<>();
    private View button;

    private void createArray() {
        int i = 0;
        artists = new ArrayList<>();
        AssetManager assetManager = getAssets();
        try {
            String[] groupName = assetManager.list("Groups");
            for (String s : groupName) {
                try {
                    String[] nameArtist = assetManager.list("Groups/" + s);
                    for (String folder : nameArtist) {
                        //Создание объекта
                        Log.i(s, "createArtist:" + i);
                        i++;
                        artists.add(new Artist(s, folder, assetManager.list("Groups/" + s + "/" + folder)));
                    }
                } catch (IOException ignored) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        Button btnDel = findViewById(R.id.litDel);

        final EditText grName= findViewById(R.id.groupName);

        OnClickListener clkGr =new OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.litEnt:

                    String textAnsw = grName.getText().toString();
                    if (textAnsw.equals("BTS")){
                        grName.setText("");
                        init();
                    }
                break;
                case R.id.litQ:

                grName.getText();

                grName.append("Q");

                break;
                case R.id.litW:
                    grName.append("W");

                    break;
                case R.id.litE:
                    grName.append("E");
                    break;
                case R.id.litR:
                    grName.append("R");
                    break;
                case R.id.litT:
                    grName.append("T");
                    break;
                case R.id.litY:
                    grName.append("Y");
                    break;
                case R.id.litU:
                    grName.append("U");
                    break;
                case R.id.litI:
                    grName.append("I");
                    break;
                case R.id.litO:
                    grName.append("O");
                    break;
                case R.id.litP:
                    grName.append("P");
                    break;
                case R.id.litA:
                    grName.append("A");
                    break;
                case R.id.litS:
                    grName.append("S");
                    break;
                case R.id.litD:
                    grName.append("D");
                    break;
                case R.id.litF:
                    grName.append("F");
                    break;
                case R.id.litG:
                    grName.append("G");
                    break;
                case R.id.litH:
                    grName.append("H");
                    break;
                case R.id.litJ:
                    grName.append("J");
                    break;
                case R.id.litK:
                    grName.append("K");
                    break;
                case R.id.litL:
                    grName.append("L");
                    break;
                case R.id.litZ:
                    grName.append("Z");
                    break;
                case R.id.litX:
                    grName.append("X");
                    break;
                case R.id.litC:
                    grName.append("C");
                    break;
                case R.id.litV:
                    grName.append("V");
                    break;
                case R.id.litB:
                    grName.append("B");
                    break;
                case R.id.litN:
                    grName.append("N");
                    break;
                case R.id.litM:
                    grName.append("M");
                    break;
                case R.id.litDel:

                    Editable textGro = grName.getText();
                    if (textGro.length()>0){
                        textGro.delete(textGro.length()-1,textGro.length());
                        grName.setText(textGro);
                    }
                    break;

            }
        }

    };
        btnQ.setOnClickListener(clkGr);
        btnEnt.setOnClickListener(clkGr);
        btnW.setOnClickListener(clkGr);
        btnE.setOnClickListener(clkGr);
        btnR.setOnClickListener(clkGr);
        btnT.setOnClickListener(clkGr);
        btnY.setOnClickListener(clkGr);
        btnU.setOnClickListener(clkGr);
        btnI.setOnClickListener(clkGr);
        btnO.setOnClickListener(clkGr);
        btnP.setOnClickListener(clkGr);
        btnA.setOnClickListener(clkGr);
        btnS.setOnClickListener(clkGr);
        btnD.setOnClickListener(clkGr);
        btnF.setOnClickListener(clkGr);
        btnG.setOnClickListener(clkGr);
        btnH.setOnClickListener(clkGr);
        btnJ.setOnClickListener(clkGr);
        btnK.setOnClickListener(clkGr);
        btnL.setOnClickListener(clkGr);
        btnZ.setOnClickListener(clkGr);
        btnX.setOnClickListener(clkGr);
        btnC.setOnClickListener(clkGr);
        btnV.setOnClickListener(clkGr);
        btnB.setOnClickListener(clkGr);
        btnN.setOnClickListener(clkGr);
        btnM.setOnClickListener(clkGr);
        btnDel.setOnClickListener(clkGr);


    }

    private void init() {

        final ImageView groupPhoto = findViewById(R.id.groupPhoto);
        Glide.with(this).load(Uri.parse("file:///android_asset/Groups/BTS/RM/RM.png"))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(groupPhoto);
    }




}
