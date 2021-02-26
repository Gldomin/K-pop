package com.star.k_pop.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.star.k_pop.R;
import com.star.k_pop.StartApplication.Importer;
import com.star.k_pop.helper.Rewarded;
import com.star.k_pop.helper.Theme;
import com.star.k_pop.lib.HeathBar;
import com.star.k_pop.lib.SomeMethods;
import com.star.k_pop.model.Artist;
import com.yandex.metrica.YandexMetrica;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GuessBands extends AppCompatActivity {

    ArrayList<Artist> artists = Importer.getRandomArtists();
    HeathBar heathBarTest; // объявление хп

    int count = 0;
    int score;
    int fastscore = 0;
    private SharedPreferences spBands;
    SharedPreferences sp;
    Theme theme;

    boolean onRewardedHint = true;
    int hintCount = 4;
    boolean hintShow = false;

    boolean onRewarded = true;      // Просмотр рекламы 1 раз
    boolean showReward = false;     // Просмотрена реклама до конца или нет
    boolean endGame = false;

    Rewarded rewarded;

    TextView scoreText; //рекорд
    TextView fastScoreText; //текущий счет
    TextView counterHint;
    ImageView groupPhoto;
    EditText grName;
    ImageButton podsk;

    //Создаем лист для кнопок
    private List<Button> buttons;

    //Массив id'шников, к которым будет обращаться программа для инициализации кнопок
    private static final int[] BUTTON_IDS = {
            R.id.litQ, R.id.litW, R.id.litE, R.id.litR, R.id.litT, R.id.litY, R.id.litU, R.id.litI, R.id.litO, R.id.litP,
            R.id.litA, R.id.litS, R.id.litD, R.id.litF, R.id.litG, R.id.litH, R.id.litJ, R.id.litK, R.id.litL,
            R.id.litZ, R.id.litX, R.id.litC, R.id.litV, R.id.litB, R.id.litN, R.id.litM, R.id.litEnt, R.id.litDel,
            R.id.space, R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5, R.id.num6, R.id.num7,
            R.id.num8, R.id.num9//, R.id.podsk
    };

    //сохранение результата на переключении активити и выключении проги
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = spBands.edit();
        editor.putInt("userScoreGuessBand", score);
        editor.apply();
    }

    private void createHeathBar() {
        ImageView imageView1 = findViewById(R.id.guessBandHeart1);
        ImageView imageView2 = findViewById(R.id.guessBandHeart2);
        ImageView imageView3 = findViewById(R.id.guessBandHeart3);
        ArrayList<ImageView> imageViewList = new ArrayList<>();
        imageViewList.add(imageView1);
        imageViewList.add(imageView2);
        imageViewList.add(imageView3);
        heathBarTest = new HeathBar(imageViewList, 3);
    }


    @SuppressLint({"ResourceAsColor", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme = new Theme(this);

        rewarded = new Rewarded(this);
        theme.setThemeSecond();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_bands);

        ImageButton about = findViewById(R.id.guessBandAbautButton);
        about.setBackgroundResource(theme.getBackgroundResource());
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image = new Intent();
                image.setClass(GuessBands.this, BasicNotice.class);
                image.putExtra("text", R.string.guessBandGameModeAbaut);
                image.putExtra("title", R.string.gameModeAbaut);
                startActivity(image);
            }
        });

        podsk = findViewById(R.id.podsk);
        counterHint = findViewById(R.id.counter_Hints);
        counterHint.setText(String.format("%d", hintCount));
        if (theme.isDarkMode()) {
            podsk.setImageResource(R.drawable.hint2);
        } else {
            podsk.setImageResource(R.drawable.hint);
        }


        sp = getSharedPreferences("settings", Context.MODE_PRIVATE);

        createHeathBar();

        scoreText = findViewById(R.id.scoreBands);                  //рекорд
        fastScoreText = findViewById(R.id.fastscoreBands);          //текущий счет
        groupPhoto = findViewById(R.id.groupPhoto);

        groupPhoto.setBackground(getResources().getDrawable(theme.getBackgroundResource()));

        spBands = getSharedPreferences("UserScore", Context.MODE_PRIVATE);

        if (spBands.contains("userScoreGuessBand")) {
            score = spBands.getInt("userScoreGuessBand", -1);
        } else {
            score = 0;
        }
        scoreText.setText(String.format("%s %d",
                getResources().getString(R.string.record_text), score));
        fastScoreText.setText(String.format("%s %d",
                getResources().getString(R.string.score_text), fastscore));

        buttons = new ArrayList<>();
        grName = findViewById(R.id.groupName);
        grName.setTextColor(theme.getTextColor());
        grName.setLongClickable(false);
        grName.setFocusable(false);

        //устанавливаем слушатель на основные кнопки
        OnClickListener clkGr = new OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.litEnt:
                        String textAnsw = grName.getText().toString();
                        if (artists.get(count).checkGroup(textAnsw)) {
                            YandexMetrica.reportEvent("GuessBands - Правильный ответ: " + textAnsw);
                            fastscore++;
                            count++;
                            change();
                            if (fastscore % 10 == 0) {
                                YandexMetrica.reportEvent("GuessBands - Доп хп");
                                heathBarTest.restore();
                            }
                            if (fastscore % 15 == 0) {
                                YandexMetrica.reportEvent("GuessBands - Доп подсказка");
                                hintCount += 2;
                            }
                            //три нижние строчки - для отладки, автоматически ставит название группы в текстовое поле
                            //на момент релиза удалить.
                            if (fastscore == 15) { //ачивка за 15 - achGuessBandsNormal. Условие ачивки
                                YandexMetrica.reportEvent("GuessBands - Ачивка 15 угаданных групп");
                                SomeMethods.achievementGetted(GuessBands.this, R.string.achGuessBandsNormal, R.drawable.normalgb, "achGuessBandsNormal"); //ачивочка
                            }
                            if (fastscore == 50) { //ачивка за 50
                                YandexMetrica.reportEvent("GuessBands - Ачивка 50 угаданных групп");
                                SomeMethods.achievementGetted(GuessBands.this, R.string.achGuessBandsExpert, R.drawable.expertgb, "achGuessBandsExpert"); //ачивочка
                            }
                        } else {
                            YandexMetrica.reportEvent("GuessBands - Неправильный ответ: " + textAnsw);
                            heathBarTest.blow();
                            if (heathBarTest.getHp() == 0) {
                                startLosingDialog();
                            }
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
                        YandexMetrica.reportEvent("GuessBands - Подсказка");
                        if (hintCount > 1) {
                            hintShow = true;
                            hintCount--;
                            counterHint.setText(String.format("%d", hintCount));
                            String textGroupHint = artists.get(count).getGroups();
                            char[] textHint = textGroupHint.toCharArray(); // Преобразуем строку str в массив символов (char)
                            for (int j = 0; j < textHint.length; j++) {
                                String textHintTwo = "" + textHint[j];
                                textHintTwo = textHintTwo.toUpperCase();
                                textHint[j] = textHintTwo.charAt(0);
                            }
                            for (Button b : buttons) {
                                if ((b.getId() == R.id.litDel) || (b.getId() == R.id.litEnt) || (b.getId() == R.id.podsk))
                                    continue;
                                for (char c : textHint) {
                                    if (b.getText().charAt(0) == c) {
                                        b.setBackgroundResource(theme.getBackgroundButton());
                                        break;
                                    }
                                }
                            }
                        } else if (hintCount == 1) {
                            onRewardHint();
                        }
                        break;
                    default:
                        grName.append("" + ((Button) view).getText().charAt(0));
                }
            }

        };

        podsk.setOnClickListener(clkGr);
        //Инициализация кнопок, добавление дополнительных символов
        for (int id : BUTTON_IDS) {
            Button button = findViewById(id);
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
            b.setBackgroundResource(theme.getBackgroundResource());
        }
        change();
    }

    //метод смены фото айдола
    @SuppressLint("DefaultLocale")
    private void change() {
        if (count >= artists.size()) {
            artists = Importer.getRandomArtists();
            count = 0;
        }
        for (Button b : buttons) {
            b.setBackgroundResource(theme.getBackgroundResource());
        }
        hintShow = false;
        grName.setText("");
        // TODO Удалить перед релизом
        /*String answ = artists.get(count).getGroups();
        answ = answ.toUpperCase();
        grName.setText(answ);*/
        // TODO Конец удаления
        fastScoreText.setText(String.format("%s %d",
                getResources().getString(R.string.score_text), fastscore));
        if (fastscore > score) score = fastscore;
        scoreText.setText(String.format("%s %d",
                getResources().getString(R.string.record_text), score));
        Glide.with(this).load(Uri.parse("file:///android_asset/Groups/" + artists.get(count).getFolder()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(groupPhoto);
    }

    @SuppressLint("DefaultLocale")
    private void startLosingDialog() {
        endGame = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
        builder.setTitle(getResources().getString(R.string.endGameCongratulate))
                .setMessage(String.format("%s %d! %s", getResources().getString(R.string.score_text), fastscore, getResources().getString(R.string.endGameNewGame)))
                .setCancelable(false)
                .setNegativeButton(getResources().getString(R.string.endGameNo), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        YandexMetrica.reportEvent("GuessStar - Игра окончена, нет");
                        finish();
                    }
                })
                .setPositiveButton(getResources().getString(R.string.endGameYes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        YandexMetrica.reportEvent("GuessStar - Игра окончена, да");
                        endGame = false;
                        heathBarTest.setHp(3);
                        fastscore = 0;
                        count++;
                        if (count >= artists.size() - 1) {
                            artists = Importer.getRandomArtists();
                            count = 0;
                        }
                        hintCount = 4;
                        counterHint.setText(String.format("%d", hintCount));
                        onRewarded = true;
                        onRewardedHint = true;
                        change();
                    }
                });
        if (rewarded.onLoaded() && onRewarded) {
            builder.setMessage(String.format("%s %d! %s %s", getResources().getString(R.string.score_text),
                    fastscore, getResources().getString(R.string.endGameNewGame), getResources().getString(R.string.endGameReward)))
                    .setNeutralButton(getResources().getString(R.string.endGameRewardShow), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            YandexMetrica.reportEvent("GuessBands - Игра окончена, реклама");
                            endGame = false;
                            rewarded.show(GuessBands.this, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    showReward = true;
                                    YandexMetrica.reportEvent("GuessBands - Реклама просмотрена");
                                }
                            }, new Rewarded.RewardDelay() {
                                @Override
                                public void onShowDismissed() {
                                    if (showReward) {
                                        onRewarded = false;
                                        heathBarTest.restore();
                                        YandexMetrica.reportEvent("GuessBands - добавлено хп");
                                    } else {
                                        YandexMetrica.reportEvent("GuessBands - Реклама не просмотрена");
                                        heathBarTest.setHp(3);
                                        fastscore = 0;
                                        count++;
                                        if (count >= artists.size() - 1) {
                                            artists = Importer.getRandomArtists();
                                            count = 0;
                                        }
                                        hintCount = 4;
                                        counterHint.setText(String.format("%d", hintCount));
                                        onRewarded = true;
                                        onRewardedHint = true;
                                        change();
                                    }
                                    showReward = false;
                                }
                            });
                        }
                    });
        }
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void onRewardHint() {
        if (rewarded.onLoaded() && onRewardedHint) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
            builder.setTitle(getResources().getString(R.string.endHintCongratulate))
                    .setMessage(String.format("%s", getResources().getString(R.string.endHintReward)))
                    .setCancelable(false)
                    .setNegativeButton(getResources().getString(R.string.endHintNo), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            YandexMetrica.reportEvent("GuessBands - последняя подсказка, нет");
                        }
                    })
                    .setPositiveButton(getResources().getString(R.string.endHintYes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            YandexMetrica.reportEvent("GuessBands - последняя подсказка, да");
                            rewarded.show(GuessBands.this, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    showReward = true;
                                    YandexMetrica.reportEvent("GuessBands - Реклама просмотрена, подсказка");
                                }
                            }, new Rewarded.RewardDelay() {
                                @Override
                                public void onShowDismissed() {
                                    if (showReward) {
                                        YandexMetrica.reportEvent("GuessBands - открыта подсказка за рекламу");
                                        onRewardedHint = false;
                                        String textGroupHint = artists.get(count).getGroups();
                                        char[] textHint = textGroupHint.toCharArray(); // Преобразуем строку str в массив символов (char)
                                        for (int j = 0; j < textHint.length; j++) {
                                            String textHintTwo = "" + textHint[j];
                                            textHintTwo = textHintTwo.toUpperCase();
                                            textHint[j] = textHintTwo.charAt(0);
                                        }
                                        for (Button b : buttons) {
                                            if ((b.getId() == R.id.litDel) || (b.getId() == R.id.litEnt) || (b.getId() == R.id.podsk))
                                                continue;
                                            for (char c : textHint) {
                                                if (b.getText().charAt(0) == c) {
                                                    b.setBackgroundResource(theme.getBackgroundButton());
                                                    break;
                                                }
                                            }
                                        }
                                        hintCount--;
                                        counterHint.setText(String.format("%d", hintCount));
                                    } else {
                                        YandexMetrica.reportEvent("GuessBands - Реклама не просмотрена");
                                        onRewardedHint = true;
                                    }
                                    showReward = false;
                                }
                            });
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
