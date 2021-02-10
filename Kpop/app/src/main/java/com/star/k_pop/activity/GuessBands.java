package com.star.k_pop.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.star.k_pop.model.Artist;
import com.star.k_pop.helper.OptionsSet;
import com.star.k_pop.R;
import com.star.k_pop.StartApplication.Importer;
import com.star.k_pop.helper.Storage;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GuessBands extends AppCompatActivity {


    private View button;
    ArrayList<Artist> artists = Importer.getRandomArtists();
    String artist;
    String band;
    String folder;
    String buttonStyleChange = "stylebutton";


    //private int currentApiVersion;

    int i = 0;
    int score;
    int fastscore = 0;
    private boolean longnazh = false;
    private SharedPreferences spBands;
    SharedPreferences sp;
    OptionsSet tempSettingsSet = new OptionsSet(false, false); //переменная для считывания состояния свиича на darkmod

    //Создаем лист для кнопок
    private List<Button> buttons;

    //Массив id'шников, к которым будет обращаться программа для инициализации кнопок
    private static final int[] BUTTON_IDS = {
            R.id.litQ, R.id.litW, R.id.litE, R.id.litR, R.id.litT, R.id.litY, R.id.litU, R.id.litI, R.id.litO, R.id.litP,
            R.id.litA, R.id.litS, R.id.litD, R.id.litF, R.id.litG, R.id.litH, R.id.litJ, R.id.litK, R.id.litL,
            R.id.litZ, R.id.litX, R.id.litC, R.id.litV, R.id.litB, R.id.litN, R.id.litM, R.id.litEnt, R.id.litDel,
            R.id.space, R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5, R.id.num6, R.id.num7,
            R.id.num8, R.id.num9, R.id.podsk
    };

    //сохранение результата на переключении активити и выключении проги
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = spBands.edit();
        editor.putInt("userScoreGuessBand", score);
        editor.apply();
    }

    @SuppressLint("ResourceAsColor")

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sp = getSharedPreferences("settings", Context.MODE_PRIVATE);

        String nameOfStorage = "settings";
        Storage storage = new Storage(this);

        Integer counter = 0;
        Storage tempStorage = new Storage(this);
        String nameOfStorage3 = "settings";
        String nameOfValue = "darkModeCounter";

        tempSettingsSet.darkMode = storage.getBoolean(nameOfStorage, "darkMode"); //считываем состояние
        //теперь выбираем тему в зависимости от положения свича
        if (tempSettingsSet.darkMode==true) {
            setTheme(R.style.AppTheme2);
        }
        else setTheme(R.style.AppThemeLight);
        super.onCreate(savedInstanceState);
/*

        currentApiVersion = android.os.Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT)
        {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }






       */

        setContentView(R.layout.activity_guess_bands);

        final TextView scoreText = findViewById(R.id.scoreBands); //рекорд
        final TextView fastscoreText = findViewById(R.id.fastscoreBands); //текущий счет
        final TextView info = findViewById(R.id.info);

        fastscoreText.setText("Ваш счет: " + fastscore);


        spBands = getSharedPreferences("UserScore", Context.MODE_PRIVATE);

        if (spBands.contains("userScoreGuessBand")) {
            score = spBands.getInt("userScoreGuessBand", -1);
            scoreText.setText("Ваш рекорд: " + score);
        } else {
            score = 0;
        }

        //Выбор первого артиста
        artist = artists.get(0).getName();
        band = artists.get(0).getGroup();
        folder = artists.get(0).getFolder();

        //Загрузка первого фото
        final ImageView groupPhoto = findViewById(R.id.groupPhoto);
        Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + folder))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(groupPhoto);


        buttons = new ArrayList<Button>();
        final EditText grName = findViewById(R.id.groupName);
        if (tempSettingsSet.darkMode==true) grName.setTextColor(getResources().getColor(R.color.colorText));
        else grName.setTextColor(getResources().getColor(R.color.colorTextLight));



        // 4 строки снизу для отладки, удалить на релизе
        String textAnsw = grName.getText().toString();
        String answ = artists.get(i).getGroup();
        answ = answ.toUpperCase();
        grName.setText(answ);


        //делаем недоступным EditText
        grName.setLongClickable(false);
        grName.setFocusable(false);

        //Подсказка названия группы на время разработки
        //надо будет удалить на релизе

        info.setText(band+counter);


        //устанавливаем слушатель на основные кнопки
        OnClickListener clkGr = new OnClickListener() {

            @Override
            public void onClick(View view) {


                switch (view.getId()) {
                    case R.id.litEnt:

                        String textAnsw = grName.getText().toString();
                        String answ = artists.get(i).getGroup();
                        answ = answ.toUpperCase();


                        for (Button b : buttons) {
                            //выбор темы в зависимости от положения свича
                            if (tempSettingsSet.darkMode==true) b.setBackgroundResource(R.drawable.stylebutton_dark);
                            else b.setBackgroundResource(R.drawable.stylebutton);
                        }

                        if (textAnsw.equals(answ)) {
                            //на время отладки отключено, на релизе надо включить
                            //grName.setText("");
                            i++;
                            fastscore++;

                            if (i == artists.size()) //перетасовка списка для вечной игры
                            {
                                artists = Importer.getRandomArtists();

                               // Toast.makeText(GuessBands.this, "Перетасовка", Toast.LENGTH_LONG).show(); //отправка сообщения на экран
                                i = 0;
                            }

                            fastscoreText.setText("Ваш счет " + fastscore);
                            if (fastscore > score) score = fastscore;
                            scoreText.setText("Ваш рекорд: " + score);
                            artist = artists.get(i).getName();
                            band = artists.get(i).getGroup();
                            folder = artists.get(i).getFolder();

                            change();
                            info.setText(band);
                            //три нижние строчки - для отладки, автоматически ставит название группы в текстовое поле
                            //на момент релиза удалить.
                            answ = artists.get(i).getGroup();
                            answ = answ.toUpperCase();
                            grName.setText(answ);


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
                        String textGroupHint = artists.get(i).getGroup();
                        char[] textHint = textGroupHint.toCharArray(); // Преобразуем строку str в массив символов (char)
                        for (int j = 0; j < textHint.length; j++) {
                            String textHintTwo = "" + textHint[j];
                            textHintTwo = textHintTwo.toUpperCase();
                            textHint[j] = textHintTwo.charAt(0);


                        }
                        String entLit = "ENT";
                        String delLit = "DEL";

                        for (Button b : buttons) {
                            if ((b.getId() == R.id.litDel) || (b.getId() == R.id.litEnt) || (b.getId() == R.id.podsk))
                                continue;
                            for (char c : textHint) {
                                if (b.getText().charAt(0) == c) {
                                    b.setBackgroundResource(R.drawable.stylebutton_hint);
                                    break;
                                }
                            }
                        }
                        break;

                    default:
                        grName.append("" + ((Button) view).getText().charAt(0));
                }

            }

        };

        //Инициализация кнопок, добавление дополнительных символов
        for (int id : BUTTON_IDS) {
            Button button = (Button) findViewById(id);
            button.setOnClickListener(clkGr);
            button.setOnClickListener(clkGr);


            //устанавливаем слушатель долгого нажатия на специальные символы
            button.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    switch (v.getId()) {
                        case R.id.num1:
                        case R.id.num2:
                        case R.id.num3:
                        case R.id.num5:
                        case R.id.num6:
                        case R.id.num7:
                        case R.id.num8:
                        case R.id.num9:
                        case R.id.num0:
                            grName.append("" + ((Button) v).getText().charAt(2));
                            break;
                        case R.id.num4:
                            String word = "'";
                            grName.append(word);
                            break;
                    }
                    return true;
                }
            });
            buttons.add(button);
        }
        for (Button b : buttons) {
            if (tempSettingsSet.darkMode==true) b.setBackgroundResource(R.drawable.stylebutton_dark);
            else b.setBackgroundResource(R.drawable.stylebutton);
        }
    }

    //метод смены фото айдола
    private void change() {
        final ImageView groupPhoto = findViewById(R.id.groupPhoto);
        Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + folder))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(groupPhoto);
    }
/*
    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus)
        {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }*/

}
