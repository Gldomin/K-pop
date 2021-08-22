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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

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
import com.star.k_pop.lib.SoundPlayer;
import com.star.k_pop.model.Bands;
import com.yandex.metrica.YandexMetrica;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GuessBands extends AppCompatActivity {

    ArrayList<Bands> artists = Importer.getRandomBands();
    HeathBar heathBarTest; // объявление хп
    SoundPlayer soundPlayer = new SoundPlayer(this); //это объект для воспроизведения звуков

    int count = 0;
    int score;
    int fastScore = 0;
    private SharedPreferences spBands;
    SharedPreferences sp;
    Theme theme;

    boolean onRewardedHint = true;
    int hintCount = 4;
    boolean hintShow = false;

    boolean hintUsed = false; // ограничение на одну подсказку на группу  (что бы несколько раз не протыкивали)
    boolean onRewarded = true;      // Просмотр рекламы 1 раз
    boolean showReward = false;     // Просмотрена реклама до конца или нет
    boolean endGame = false;

    Rewarded rewarded;

    TextView scoreText; //рекорд
    TextView fastScoreText; //текущий счет
    TextView counterHint;
    ImageView groupPhoto;
    EditText grName;
    ImageButton hintButton;

    //Создаем лист для кнопок
    private List<Button> buttons;

    //Массив id шников, к которым будет обращаться программа для инициализации кнопок
    private static final int[] BUTTON_IDS = {
            R.id.litQ, R.id.litW, R.id.litE, R.id.litR, R.id.litT, R.id.litY, R.id.litU, R.id.litI, R.id.litO, R.id.litP,
            R.id.litA, R.id.litS, R.id.litD, R.id.litF, R.id.litG, R.id.litH, R.id.litJ, R.id.litK, R.id.litL,
            R.id.litZ, R.id.litX, R.id.litC, R.id.litV, R.id.litB, R.id.litN, R.id.litM, R.id.litEnt, R.id.litDel,
            R.id.space, R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5, R.id.num6, R.id.num7,
            R.id.num8, R.id.num9
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

        Animation lifeBrokeAnimation = AnimationUtils.loadAnimation(this,R.anim.heart_broke_animation);
        heathBarTest = new HeathBar(imageViewList, 3, lifeBrokeAnimation);
    }


    @SuppressLint({"ResourceAsColor", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme = new Theme(this);
        rewarded = new Rewarded(this, R.string.admob_id_reward_bands);
        theme.setThemeSecond();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_bands);

        final int keyClickID=soundPlayer.load(R.raw.key_click);

        ImageButton about = findViewById(R.id.guessBandAbautButton);
        about.setBackgroundResource(theme.getBackgroundButton());
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

        hintButton = findViewById(R.id.podsk);
        counterHint = findViewById(R.id.counter_Hints);
        counterHint.setText(String.format("%d", hintCount));
        if (theme.isDarkMode()) {
            hintButton.setImageResource(R.drawable.hint2);
        } else {
            hintButton.setImageResource(R.drawable.hint);
        }

        hintButton.setBackgroundResource(theme.getBackgroundButton());

        sp = getSharedPreferences("settings", Context.MODE_PRIVATE);

        createHeathBar();

        scoreText = findViewById(R.id.scoreBands);                  //рекорд
        fastScoreText = findViewById(R.id.fastscoreBands);          //текущий счет
        groupPhoto = findViewById(R.id.groupPhoto);
        scoreText.setTextColor(theme.getTextColor());
        fastScoreText.setTextColor(theme.getTextColor());

        groupPhoto.setBackground(AppCompatResources.getDrawable(this, theme.getBackgroundButton()));

        spBands = getSharedPreferences("UserScore", Context.MODE_PRIVATE);

        if (spBands.contains("userScoreGuessBand")) {
            score = spBands.getInt("userScoreGuessBand", -1);
        } else {
            score = 0;
        }
        scoreText.setText(String.format("%s %d",
                getResources().getString(R.string.record_text), score));
        fastScoreText.setText(String.format("%s %d",
                getResources().getString(R.string.score_text), fastScore));

        buttons = new ArrayList<>();
        grName = findViewById(R.id.groupName);
        grName.setTextColor(theme.getButtonTextColor());
        grName.setLongClickable(false);
        grName.setFocusable(false);

        //устанавливаем слушатель на основные кнопки
        OnClickListener clkGr = new OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("sound","-> key_click"); //чит-лог

                soundPlayer.playSoundStream(keyClickID); //звук кнопок
                int id = view.getId();
                if (id == R.id.litEnt) {
                    String textAnswer = grName.getText().toString();
                    if (artists.get(count).checkGroup(textAnswer)) {
                        YandexMetrica.reportEvent("GuessBands - Правильный ответ: " + textAnswer);
                        fastScore++;
                        count++;
                        hintUsed = false; //подсказку можно исопльзовать вновь
                        change();
                        if (fastScore % 10 == 0) {
                            YandexMetrica.reportEvent("GuessBands - Доп хп");
                            heathBarTest.restore();
                        }
                        if (fastScore % 15 == 0) {
                            YandexMetrica.reportEvent("GuessBands - Доп подсказка");
                            hintCount += 2;
                        }
                        //три нижние строчки - для отладки, автоматически ставит название группы в текстовое поле
                        //на момент релиза удалить.
                        if (fastScore == 15) { //ачивка за 15 - achGuessBandsNormal. Условие ачивки
                            YandexMetrica.reportEvent("GuessBands - Ачивка 15 угаданных групп");
                            SomeMethods.achievementGetted(GuessBands.this, R.string.achGuessBandsNormal, R.drawable.normalgb, "achGuessBandsNormal"); //ачивочка
                        }
                        if (fastScore == 50) { //ачивка за 50
                            YandexMetrica.reportEvent("GuessBands - Ачивка 50 угаданных групп");
                            SomeMethods.achievementGetted(GuessBands.this, R.string.achGuessBandsExpert, R.drawable.expertgb, "achGuessBandsExpert"); //ачивочка
                        }
                    } else {
                        YandexMetrica.reportEvent("GuessBands - Неправильный ответ: " + textAnswer);
                        heathBarTest.blow();
                        if (heathBarTest.getHp() == 0) {
                            startLosingDialog();
                        }
                    }
                } else if (id == R.id.space) {
                    grName.append(" ");
                } else if (id == R.id.litDel) {
                    Editable textGro = grName.getText();
                    if (textGro.length() > 0) {
                        textGro.delete(textGro.length() - 1, textGro.length());
                        grName.setText(textGro);
                    }
                } else if (id == R.id.podsk) {
                    if (!hintUsed) {
                        YandexMetrica.reportEvent("GuessBands - Подсказка");
                        if (hintCount > 1) {
                            hintShow = true;
                            hintCount--;
                            counterHint.setText(String.format("%d", hintCount));
                            String textGroupHint = artists.get(count).getName();
                            char[] textHint = textGroupHint.toCharArray(); // Преобразуем строку str в массив символов (char)
                            for (int j = 0; j < textHint.length; j++) {
                                String textHintTwo = "" + textHint[j];
                                textHintTwo = textHintTwo.toUpperCase();
                                textHint[j] = textHintTwo.charAt(0);
                                grName.setText("");
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
                        hintUsed = true; //подсказка использована
                    }
                } else {
                    grName.append("" + ((Button) view).getText().charAt(0));
                }
            }

        };

        hintButton.setOnClickListener(clkGr);
        //Инициализация кнопок, добавление дополнительных символов
        for (int id : BUTTON_IDS) {
            Button button = findViewById(id);
            button.setOnClickListener(clkGr);
            //устанавливаем слушатель долгого нажатия на специальные символы
            button.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int vId = v.getId();
                    if (vId == R.id.num1 || vId == R.id.num2 || vId == R.id.num3
                            || vId == R.id.num5 || vId == R.id.num6 || vId == R.id.num7
                            || vId == R.id.num8 || vId == R.id.num9 || vId == R.id.num0) {
                        grName.append("" + ((Button) v).getText().charAt(2));
                    } else if (vId == R.id.num4) {
                        String word = "'";
                        grName.append(word);
                    }
                    return true;
                }
            });
            buttons.add(button);
        }
        for (Button b : buttons) {
            b.setBackgroundResource(theme.getBackgroundButton());
        }
        change();
    }

    //метод смены фото айдола
    @SuppressLint("DefaultLocale")
    private void change() {
        if (count >= artists.size()) {
            artists = Importer.getRandomBands();
            count = 0;
        }
        for (Button b : buttons) {
            b.setBackgroundResource(theme.getBackgroundButton());
        }
        hintShow = false;
        grName.setText("");

        Log.i("answer=",artists.get(count).getName()); //чит-лог

        fastScoreText.setText(String.format("%s %d",
                getResources().getString(R.string.score_text), fastScore));
        if (fastScore > score) score = fastScore;
        scoreText.setText(String.format("%s %d",
                getResources().getString(R.string.record_text), score));
        Glide.with(this).load(Uri.parse(artists.get(count).getFolderRandom()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(groupPhoto);
    }

    @SuppressLint("DefaultLocale")
    private void startLosingDialog() {
        endGame = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
        builder.setTitle(getResources().getString(R.string.endGameCongratulate))
                .setMessage(String.format("%s %d! %s", getResources().getString(R.string.score_text), fastScore, getResources().getString(R.string.endGameNewGame)))
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
                        fastScore = 0;
                        count++;
                        if (count >= artists.size() - 1) {
                            artists = Importer.getRandomBands();
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
                    fastScore, getResources().getString(R.string.endGameNewGame), getResources().getString(R.string.endGameReward)))
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
                                        fastScore = 0;
                                        count++;
                                        if (count >= artists.size() - 1) {
                                            artists = Importer.getRandomBands();
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
                                        String textGroupHint = artists.get(count).getName();
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
                                        counterHint.setText(String.format(new Locale("ru"),"%d", hintCount));
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
