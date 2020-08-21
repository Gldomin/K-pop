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

import com.exam.k_pop.StartApplication.Importer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GuessBands extends AppCompatActivity {



    private View button;
    ArrayList<Artist> artists = Importer.getRandomArtists();
    String artist ;
    String band ;
    String folder ;
    int i = 0;
    int score = 0;

    private List<Button> buttons;
    private static final int[] BUTTON_IDS= {
    R.id.litW, R.id.litE,  R.id.litR, R.id.litT, R.id.litY, R.id.litU, R.id.litI, R.id.litO, R.id.litP,
    R.id.litA, R.id.litS, R.id.litD, R.id.litF, R.id.litG, R.id.litH, R.id.litJ, R.id.litK, R.id.litL,
    R.id.litZ, R.id.litX, R.id.litC, R.id.litV, R.id.litB, R.id.litN, R.id.litM, R.id.litEnt, R.id.litDel,
    R.id.space, R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5, R.id.num6, R.id.num7,
    R.id.num8, R.id.num9,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_bands);
        final TextView scoreText = findViewById(R.id.scoreBands);


             artist= artists.get(0).getName();
             band = artists.get(0).getGroup();
             folder = artists.get(0).getFolder();

            final ImageView groupPhoto = findViewById(R.id.groupPhoto);
            Glide.with(this).load(Uri.parse("file:///android_asset/Groups/"+folder))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transition(withCrossFade())
                    .into(groupPhoto);


            buttons = new ArrayList<Button>();




            final EditText grName = findViewById(R.id.groupName);
            grName.setLongClickable(false);
            grName.setFocusable(false);
            grName.setText(band);

            OnClickListener clkGr = new OnClickListener() {

                @Override
                public void onClick(View view) {


                        switch (view.getId()) {
                            case R.id.litEnt:

                                String textAnsw = grName.getText().toString();
                                String answ = artists.get(i).getGroup();
                                answ=answ.toUpperCase();


                                if (textAnsw.equals(answ)) {
                                    grName.setText("");
                                     i++;
                                     score++;

                                     scoreText.setText("Ваш результат: "+score);

                                    artist= artists.get(i).getName();
                                    band = artists.get(i).getGroup();
                                    folder = artists.get(i).getFolder();

                                    change();
                                    grName.setText(band);
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
                            case R.id.num7:
                                grName.append("7");
                                break;
                            case R.id.space:
                                grName.append(" ");
                                break;
                            case R.id.litDel:

                                Editable textGro = grName.getText();
                                if (textGro.length() > 0) {
                                    textGro.delete(textGro.length() - 1, textGro.length());
                                    grName.setText(textGro);
                                }
                                break;

                        }

                }

            };

        for(int id : BUTTON_IDS) {
            Button button = (Button)findViewById(id);
            button.setOnClickListener(clkGr);
            buttons.add(button);
        }
        


    }

    private void change() {

        final ImageView groupPhoto = findViewById(R.id.groupPhoto);
        Glide.with(this).load(Uri.parse("file:///android_asset/Groups/"+folder))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(groupPhoto);
    }



}
