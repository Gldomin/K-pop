package com.examy.k_pop;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.examy.k_pop.Artist;
import com.examy.k_pop.StartApplication.Importer;

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
    int score ;
    int fastscore=0;
    private boolean longnazh = false;
    private SharedPreferences spBands;

    //Создаем лист для кнопок
    private List<Button> buttons;

    //Массив id'шников, к которым будет обращаться программа для инициализации кнопок
    private static final int[] BUTTON_IDS= {
    R.id.litW, R.id.litE,  R.id.litR, R.id.litT, R.id.litY, R.id.litU, R.id.litI, R.id.litO, R.id.litP,
    R.id.litA, R.id.litS, R.id.litD, R.id.litF, R.id.litG, R.id.litH, R.id.litJ, R.id.litK, R.id.litL,
    R.id.litZ, R.id.litX, R.id.litC, R.id.litV, R.id.litB, R.id.litN, R.id.litM, R.id.litEnt, R.id.litDel,
    R.id.space, R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5, R.id.num6, R.id.num7,
    R.id.num8, R.id.num9, R.id.podsk
    };

    //сохранение результата на переключении активити и выключении проги
    @Override
    protected void onPause(){
        super.onPause();

        SharedPreferences.Editor editor = spBands.edit();
        editor.putInt("userScoreGuessBand", score);
        editor.apply();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_bands);
        final TextView scoreText = findViewById(R.id.scoreBands);
        final TextView fastscoreText = findViewById(R.id.fastscoreBands);
        final TextView info = findViewById(R.id.info);


        fastscoreText.setText("Ваш счет: "+fastscore);



        spBands = getSharedPreferences("UserScore", Context.MODE_PRIVATE);

        if (spBands.contains("userScoreGuessBand")){
            score = spBands.getInt("userScoreGuessBand",-1);
            scoreText.setText("Ваш рекорд: "+score);
        }
        else{ score = 0;}

            //Выбор первого артиста
             artist= artists.get(0).getName();
             band = artists.get(0).getGroup();
             folder = artists.get(0).getFolder();

             //Загрузка первого фото
            final ImageView groupPhoto = findViewById(R.id.groupPhoto);
            Glide.with(this).load(Uri.parse("file:///android_asset/Groups/"+folder))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transition(withCrossFade())
                    .into(groupPhoto);


            buttons = new ArrayList<Button>();
            final EditText grName = findViewById(R.id.groupName);

            //делаем недоступным EditText
            grName.setLongClickable(false);
            grName.setFocusable(false);

            //Подсказка названия группы на время разработки
            //надо будет удалить на релизе
            info.setText(band);

            //устанавливаем слушатель на основные кнопки
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
                                     fastscore++;


                                     fastscoreText.setText("Ваш счет "+fastscore);
                                        if(fastscore>score) score=fastscore;
                                    scoreText.setText("Ваш рекорд: "+score);
                                    artist= artists.get(i).getName();
                                    band = artists.get(i).getGroup();
                                    folder = artists.get(i).getFolder();

                                    change();
                                    info.setText(band);
                                }
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

                            case R.id.podsk:
                                String textGroupHint = grName.getText().toString();
                                char[] textHint = textGroupHint.toCharArray(); // Преобразуем строку str в массив символов (char)
                                for ( int j:textHint) { String textHintTwo = ""+textHint[j];
                                    textHintTwo=textHintTwo.toLowerCase();
                                    textHint[j] =textHintTwo.charAt(0);

                                }

                                            for(Button b : buttons){
                                                for(char c : textHint){
                                                    if(b.getText().charAt(0) == c){
                                                        b.setBackgroundColor(Color.RED);
                                                        break;
                                                    }
                                                }
                                            }
                                break;

                            default:
                                grName.append(""+((Button)view).getText().charAt(0));
                        }

                }

            };

            //Инициализация кнопок, добавление дополнительных символов
        for(int id : BUTTON_IDS) {
            Button button = (Button)findViewById(id);
            button.setOnClickListener(clkGr);

            //устанавливаем слушатель долгого нажатия на специальные символы
            button.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    switch (v.getId()) {
                        case R.id.num1:
                        case R.id.num2:
                        case R.id.num3:
                        case R.id.num4:
                        case R.id.num5:
                        case R.id.num6:
                        case R.id.num7:
                        case R.id.num8:
                        case R.id.num9:
                        case R.id.num0:
                            grName.append(""+((Button)v).getText().charAt(2));
                            break;
                    }
                    return true;
                }
            });
            buttons.add(button);
        }
        


    }

        //метод смены фото айдола
    private void change() {

        final ImageView groupPhoto = findViewById(R.id.groupPhoto);
        Glide.with(this).load(Uri.parse("file:///android_asset/Groups/"+folder))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(groupPhoto);
    }



}
