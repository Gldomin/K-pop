package com.star.k_pop.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
import com.star.k_pop.model.Bands;
import com.yandex.metrica.YandexMetrica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GuessBandsModeTwo extends AppCompatActivity {

    private final String TAG = "Mode Two";

    Theme theme;
    HeathBar heathBarTest;

    private int record;
    private int scoreNow = -1;
    private int count = 0;
    private int startButtonNumber;
    private int countLetter;
    private String nameGroup;
    private boolean countClick = false;

    private boolean onRewarded = true;
    private boolean showReward = false;

    private boolean onRewardedHint = true;
    private boolean hintUsed = false;
    private int hintCount = 4;

    private int[] ref;
    private List<Button> buttons;
    private List<Button> buttonsEnd;
    private ArrayList<Bands> artists = Importer.getRandomBands();

    private static final int[] BUTTON_IDS = {
            R.id.lit1, R.id.lit2, R.id.lit3, R.id.lit4, R.id.lit5, R.id.lit6, R.id.lit7, R.id.lit8, R.id.lit9,
            R.id.lit10, R.id.lit11, R.id.lit12, R.id.lit13, R.id.lit14, R.id.lit15, R.id.lit16
    };

    private static final int[] BUTTON_IDS_END = {
            R.id.end1, R.id.end2, R.id.end3, R.id.end4, R.id.end5,
            R.id.end6, R.id.end7, R.id.end8, R.id.end9, R.id.end10
    };

    private SharedPreferences spBands;
    Rewarded rewarded;

    private ImageView groupPhoto;
    private Button slideButton;
    private ImageButton hintButton;
    private TextView recordText; //рекорд
    private TextView scoreNowText; //текущий счет
    private TextView counterHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme = new Theme(this);
        theme.setThemeSecond();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_bands_mode_two);

        rewarded = new Rewarded(this, R.string.admob_id_reward_bands);

        groupPhoto = findViewById(R.id.groupPhoto);
        hintButton = findViewById(R.id.podsk);
        slideButton = findViewById(R.id.button);
        recordText = findViewById(R.id.scoreBands);
        scoreNowText = findViewById(R.id.fastscoreBands);

        groupPhoto.setBackground(AppCompatResources.getDrawable(this, theme.getBackgroundResource()));
        hintButton.setBackgroundResource(theme.getBackgroundResource());
        if (theme.isDarkMode()) {
            hintButton.setImageResource(R.drawable.hint2);
        } else {
            hintButton.setImageResource(R.drawable.hint);
        }

        spBands = getSharedPreferences("UserScore", Context.MODE_PRIVATE);

        if (spBands.contains("userScoreGuessBandModeTwo")) {
            record = spBands.getInt("userScoreGuessBandModeTwo", -1);
        } else {
            record = -1;
        }

        counterHint = findViewById(R.id.counter_Hints);
        counterHint.setText(String.format("%d", hintCount));
        recordText.setText(String.format("%s %d",
                getResources().getString(R.string.record_text), record));
        scoreNowText.setText(String.format("%s %d",
                getResources().getString(R.string.score_text), scoreNow));

        slideButton.setBackgroundResource(R.drawable.roundedimageview);
        slideButton.setVisibility(View.INVISIBLE);

        buttons = new ArrayList<>();
        buttonsEnd = new ArrayList<>();

        for (int id : BUTTON_IDS) {
            Button button = findViewById(id);
            button.setOnClickListener(new OnClickListenerCustom(buttonsEnd, "", "_"));
            button.setBackgroundResource(theme.getBackgroundResource());
            buttons.add(button);
        }

        for (int id : BUTTON_IDS_END) {
            Button button = findViewById(id);
            button.setOnClickListener(new OnClickListenerCustom(buttons, "_", ""));
            button.setBackgroundResource(theme.getBackgroundResource());
            buttonsEnd.add(button);
        }

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hintUsed) {
                    if (hintCount > 1) {
                        hintCount--;
                        counterHint.setText(String.format("%d", hintCount));
                        for (int i = 0; i < ref.length; i++) {
                            if (i < countLetter && nameGroup.charAt(i) != ' ') {
                                buttons.get(ref[i]).setText("" + nameGroup.charAt(i));
                            } else {
                                buttons.get(ref[i]).setVisibility(View.INVISIBLE);
                            }
                        }
                        for (Button b : buttonsEnd) {
                            if (b.getText() != " ") {
                                b.setText("_");
                            }
                        }
                        hintUsed = true;
                    } else if (hintCount == 1) {
                        onRewardHint();
                    }
                }
            }
        });

        createHeathBar();
        change();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = spBands.edit();
        editor.putInt("userScoreGuessBandModeTwo", record);
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

        Animation lifeBrokeAnimation = AnimationUtils.loadAnimation(this, R.anim.heart_broke_animation);
        heathBarTest = new HeathBar(imageViewList, 3, lifeBrokeAnimation);
    }

    private void change() {
        count++;
        scoreNow++;
        hintUsed = false;
        scoreNowText.setText(String.format("%s %d",
                getResources().getString(R.string.score_text), scoreNow));
        if (scoreNow > record) {
            record = scoreNow;
            recordText.setText(String.format("%s %d",
                    getResources().getString(R.string.record_text), record));
        }
        if (count >= artists.size() || count < 0) {
            artists = Importer.getRandomBands();
            count = 0;
        }
        for (Button b : buttonsEnd) {
            b.setText(" ");
            b.setVisibility(View.INVISIBLE);
        }
        nameGroup = artists.get(count).getNameCorrect();
        countLetter = nameGroup.length();
        startButtonNumber = 5 - countLetter / 2;
        for (int i = startButtonNumber; i < startButtonNumber + countLetter; i++) {
            if (nameGroup.charAt(i - startButtonNumber) != ' ') {
                buttonsEnd.get(i).setVisibility(View.VISIBLE);
                buttonsEnd.get(i).setText("_");
            }
        }
        ref = createRandomButton();
        for (int i = 0; i < ref.length; i++) {
            if (i < countLetter && nameGroup.charAt(i) != ' ') {
                buttons.get(ref[i]).setText("" + nameGroup.charAt(i));
            } else {
                buttons.get(ref[i]).setText("" + (char) ('a' + new Random().nextInt(26)));
            }
            buttons.get(ref[i]).setVisibility(View.VISIBLE);
        }
        Glide.with(this).load(Uri.parse(artists.get(count).getFolderRandom()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(groupPhoto);

        Log.i("TAGS", artists.get(count).getName()); //чит-лог
    }

    private int[] createRandomButton() {
        List<Integer> fill = new ArrayList<>();
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            fill.add(i);
        }
        Collections.shuffle(fill);
        return convertIntegers(fill);
    }

    public static int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = integers.get(i);
        }
        return ret;
    }

    private void checkWin() {
        StringBuilder win = new StringBuilder();
        for (Button b : buttonsEnd) {
            if (!b.getText().toString().equals(" "))
                win.append(b.getText().toString());
        }
        if (artists.get(count).checkGroup(win.toString())) {
            change();
        } else {
            heathBarTest.blow();
            if (heathBarTest.getHp() == 0) {
                startLosingDialog();
            }
        }
    }

    class AnimatorCustom extends AnimatorListenerAdapter {

        private final int positionButton;
        private final String str;
        private final List<Button> buttons;

        public AnimatorCustom(List<Button> buttons, int positionButton, String str) {
            this.positionButton = positionButton;
            this.str = str;
            this.buttons = buttons;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            slideButton.setVisibility(View.INVISIBLE);
            buttons.get(positionButton).setText(str);
            countClick = false;

            if (positionButton - startButtonNumber == countLetter - 1 && buttons == buttonsEnd) {
                checkWin();
            }
        }
    }

    class OnClickListenerCustom implements View.OnClickListener {

        String textStart;
        String textEnd;
        List<Button> buttonsClick;

        public OnClickListenerCustom(List<Button> buttonsClick, String textStart, String textEnd) {
            this.textStart = textStart;
            this.textEnd = textEnd;
            this.buttonsClick = buttonsClick;
        }

        @Override
        public void onClick(final View view) {
            if (((Button) view).getText().equals(textStart) || countClick) {
                return;
            }
            countClick = true;
            int positionButton = -1;
            for (int i = 0; i < buttonsClick.size(); i++) {
                if (buttonsClick.get(i).getText().toString().equals(textEnd)) {
                    positionButton = i;
                    break;
                }
            }
            if (positionButton == -1) {
                countClick = false;
                return;
            }

            int[] positionStart = new int[2];
            view.getLocationInWindow(positionStart);

            int[] positionEnd = new int[2];
            buttonsClick.get(positionButton).getLocationInWindow(positionEnd);

            String str = ((Button) view).getText().toString();

            slideButton.setX(positionStart[0]);
            slideButton.setY(positionStart[1]);
            slideButton.setWidth(view.getWidth());
            slideButton.setHeight(view.getHeight());
            slideButton.setText(((Button) view).getText());
            slideButton.setVisibility(View.VISIBLE);
            ((Button) view).setText(textStart);

            slideButton.animate().setDuration(400)
                    .x(positionEnd[0])
                    .y(positionEnd[1])
                    .setListener(new AnimatorCustom(buttonsClick, positionButton, str));
        }
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
                            rewarded.show(GuessBandsModeTwo.this, new OnUserEarnedRewardListener() {
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
                                        for (int i = 0; i < ref.length; i++) {
                                            if (i < countLetter && nameGroup.charAt(i) != ' ') {
                                                buttons.get(ref[i]).setText("" + nameGroup.charAt(i));
                                            } else {
                                                buttons.get(ref[i]).setVisibility(View.INVISIBLE);
                                            }
                                        }
                                        for (Button b : buttonsEnd) {
                                            if (b.getText() != " ") {
                                                b.setText("_");
                                            }
                                        }
                                        hintCount--;
                                        counterHint.setText(String.format(new Locale("ru"), "%d", hintCount));
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

    private void startLosingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
        builder.setTitle(getResources().getString(R.string.endGameCongratulate))
                .setMessage(String.format("%s %d! %s", getResources().getString(R.string.score_text), scoreNow, getResources().getString(R.string.endGameNewGame)))
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
                        heathBarTest.setHp(3);
                        scoreNow = -1;
                        hintCount = 4;
                        counterHint.setText(String.format("%d", hintCount));
                        onRewarded = true;
                        onRewardedHint = true;
                        change();
                    }
                });
        if (rewarded.onLoaded() && onRewarded) {
            builder.setMessage(String.format("%s %d! %s %s", getResources().getString(R.string.score_text),
                    scoreNow, getResources().getString(R.string.endGameNewGame), getResources().getString(R.string.endGameReward)))
                    .setNeutralButton(getResources().getString(R.string.endGameRewardShow), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            YandexMetrica.reportEvent("GuessBands - Игра окончена, реклама");
                            rewarded.show(GuessBandsModeTwo.this, new OnUserEarnedRewardListener() {
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
                                        scoreNow = -1;
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
}