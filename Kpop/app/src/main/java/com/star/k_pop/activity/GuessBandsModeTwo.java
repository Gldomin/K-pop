package com.star.k_pop.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.star.k_pop.R;
import com.star.k_pop.StartApplication.Importer;
import com.star.k_pop.ad.InterstitialCustom;
import com.star.k_pop.ad.InterstitialCustomYandex;
import com.star.k_pop.ad.RewardedCustom;
import com.star.k_pop.ad.RewardedCustomYandex;
import com.star.k_pop.helper.Storage;
import com.star.k_pop.helper.Theme;
import com.star.k_pop.lib.HeathBar;
import com.star.k_pop.lib.SomeMethods;
import com.star.k_pop.lib.SoundPlayer;
import com.star.k_pop.model.Bands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import io.appmetrica.analytics.AppMetrica;

public class GuessBandsModeTwo extends AppCompatActivity {

    private Theme theme;
    private HeathBar heathBarTest;
    private final SoundPlayer soundPlayer = new SoundPlayer(this); //это объект для воспроизведения звуков
    private boolean sound = false; //включен ли звук
    private int pingClickID;
    private int longSwitchID;
    private int grace;
    private int record;
    private int scoreNow = -1;
    private int count = 0;
    private int countLetter;
    private String nameGroup;
    private boolean countClick = false;
    private boolean onRewarded = true;
    private boolean showReward = false;
    private boolean onRewardedHint = true;
    private boolean hintUsed = false;
    private int hintCount = 3;
    private int[] ref;
    private List<Button> buttons;
    private List<Button> buttonsEnd;
    private ArrayList<Bands> bands;

    private static final int[] BUTTON_IDS = {
            R.id.lit1, R.id.lit2, R.id.lit3, R.id.lit4, R.id.lit5, R.id.lit6, R.id.lit7, R.id.lit8, R.id.lit9,
            R.id.lit10, R.id.lit11, R.id.lit12, R.id.lit13, R.id.lit14, R.id.lit15, R.id.lit16, R.id.lit17, R.id.lit18,
            R.id.lit19, R.id.lit20
    };

    private static final int[] BUTTON_IDS_END = {
            R.id.end1, R.id.end2, R.id.end3, R.id.end4, R.id.end5,
            R.id.end6, R.id.end7, R.id.end8, R.id.end9, R.id.end10,
            R.id.end11, R.id.end12, R.id.end13, R.id.end14, R.id.end15,
            R.id.end16, R.id.end17
    };

    private SharedPreferences spBands;

    RewardedCustom rewardedCustom; //Класс для работы с рекламой

    private ImageView groupPhoto;
    private Button slideButton;
    private TextView recordText; //рекорд
    private TextView scoreNowText; //текущий счет
    private TextView counterHint;

    private InterstitialCustom mInterstitialAd;

    private int countAd = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme = new Theme(this);
        theme.setThemeSecond();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_bands_mode_two);

        pingClickID = soundPlayer.load(R.raw.ping_click); //id загруженного потока
        longSwitchID = soundPlayer.load(R.raw.long_switch);
        grace = soundPlayer.load(R.raw.bells);
        Storage storage = new Storage(this, "settings"); //хранилище для извлечения
        sound = storage.getBoolean("soundMode"); //настроек звука

        bands = Importer.getRandomBands();
        if (bands.size() == 0) {
            finish();
        }


        rewardedCustom = new RewardedCustomYandex(this, getResources().getString(R.string.yandex_id_reward)) ;
        mInterstitialAd = new InterstitialCustomYandex(this, getResources().getString(R.string.yandex_id_interstitial_game));


        groupPhoto = findViewById(R.id.groupPhoto);
        ImageButton hintButton = findViewById(R.id.podsk);
        slideButton = findViewById(R.id.button);
        recordText = findViewById(R.id.scoreBands);
        scoreNowText = findViewById(R.id.fastscoreBands);
        recordText.setTextColor(theme.getTextColor());
        scoreNowText.setTextColor(theme.getTextColor());
        hintButton.setBackgroundResource(theme.getBackgroundButton());
        hintButton.setImageResource(theme.getHintDrawable());

        ImageButton about = findViewById(R.id.guessBandAbautButton);
        about.setBackgroundResource(theme.getBackgroundButton());
        about.setOnClickListener(view -> {
            Intent image = new Intent();
            image.setClass(GuessBandsModeTwo.this, BasicNotice.class);
            image.putExtra("text", R.string.guessBandGameModeTwoAbout);
            image.putExtra("title", R.string.gameModeAbout);
            startActivity(image);
        });

        spBands = getSharedPreferences("UserScore", Context.MODE_PRIVATE);

        if (spBands.contains("userScoreGuessBandModeTwo")) {
            record = spBands.getInt("userScoreGuessBandModeTwo", -1);
        } else {
            record = -1;
        }

        counterHint = findViewById(R.id.counter_hints_bands);
        counterHint.setText(String.format(Locale.getDefault(), "%d", hintCount));
        recordText.setText(getResources().getString(R.string.record_text, record));
        scoreNowText.setText(getResources().getString(R.string.score_text, scoreNow));

        slideButton.setBackgroundResource(theme.getBackgroundButton());
        slideButton.setVisibility(View.INVISIBLE);

        buttons = new ArrayList<>();
        buttonsEnd = new ArrayList<>();

        for (int id : BUTTON_IDS) {
            Button button = findViewById(id);
            button.setOnClickListener(new OnClickListenerCustom(buttonsEnd, "", "_"));
            button.setBackgroundResource(theme.getBackgroundButton());
            buttons.add(button);
        }

        for (int id : BUTTON_IDS_END) {
            Button button = findViewById(id);
            button.setOnClickListener(new OnClickListenerCustom(buttons, "_", ""));
            button.setBackgroundResource(R.drawable.roundedimageview);
            button.setTextColor(theme.getTextColor());
            buttonsEnd.add(button);
        }

        hintButton.setOnClickListener(view -> {
            if (!hintUsed) {
                if (hintCount > 1) {
                    hintCount--;
                    counterHint.setText(String.format(Locale.getDefault(), "%d", hintCount));
                    for (int i = 0; i < ref.length; i++) {
                        if (i < countLetter && nameGroup.charAt(i) != ' ') {
                            buttons.get(ref[i]).setText(String.format("%s", nameGroup.charAt(i)));
                            buttons.get(ref[i]).setVisibility(View.VISIBLE);
                        } else {
                            buttons.get(ref[i]).setText("");
                            buttons.get(ref[i]).setVisibility(View.INVISIBLE);
                        }
                    }
                    for (Button b : buttonsEnd) {
                        if (b.getText() != " ") {
                            b.setText("_");
                            b.setBackgroundResource(R.drawable.roundedimageview);
                            b.setTextColor(theme.getTextColor());
                        }
                    }
                    hintUsed = true;
                } else if (hintCount == 1) {
                    onRewardHint();
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
        scoreNowText.setText(getResources().getString(R.string.score_text, scoreNow));
        if (scoreNow > record) {
            record = scoreNow;
            recordText.setText(getResources().getString(R.string.record_text, record));
        }
        if (count >= bands.size() || count < 0) {
            bands = Importer.getRandomBands();
            count = 0;
        }
        for (Button b : buttonsEnd) {
            b.setText(" ");
            b.setVisibility(View.GONE);
            b.setTextColor(theme.getTextColor());
            b.setBackgroundResource(R.drawable.roundedimageview);
        }
        nameGroup = bands.get(count).getNameCorrect();
        countLetter = nameGroup.length();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width;
        if (countLetter>10){
            width = size.x / countLetter;
        }else{
            width = size.x / 10;
        }
        for (int i = 0; i < countLetter; i++) {
            if (nameGroup.charAt(i) != ' ') {
                buttonsEnd.get(i).setVisibility(View.VISIBLE);
                buttonsEnd.get(i).setText("_");
            }
            buttonsEnd.get(i).setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        slideButton.setLayoutParams(new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
        ref = createRandomButton();
        String nameTwo;
        do {
            nameTwo = bands.get(new Random().nextInt(bands.size())).getNameCorrect();
        } while (nameTwo.equals(nameGroup));
        int countNameTwoChar = 0;
        for (int i = 0; i < ref.length; i++) {
            if (i < countLetter && nameGroup.charAt(i) != ' ') {
                buttons.get(ref[i]).setText(String.format("%s", nameGroup.charAt(i)));
            } else {
                if (countNameTwoChar < nameTwo.length() && nameTwo.charAt(countNameTwoChar) != ' ') {
                    buttons.get(ref[i]).setText(String.format("%s", nameTwo.charAt(countNameTwoChar)));
                    countNameTwoChar++;
                } else {
                    if (new Random().nextInt(25) > 0) {
                        buttons.get(ref[i]).setText(String.format("%s", (char) ('a' + new Random().nextInt(26))));
                    } else {
                        buttons.get(ref[i]).setText(String.format("%s", (char) ('0' + new Random().nextInt(10))));
                    }
                }
            }
            buttons.get(ref[i]).setVisibility(View.VISIBLE);
        }
        Glide.with(this).load(Uri.parse(bands.get(count).getFolderRandom()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(groupPhoto);
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
        if (bands.get(count).checkGroup(win.toString())) {
            if ((scoreNow+1) % 35 == 0) {
                heathBarTest.restore();
            }
            if ((scoreNow+1) % 70 == 0) {
                hintCount += 1;
            }

            boolean achievemented = false;
            if (scoreNow + 1 == 10) { //ачивка за 10
                if (SomeMethods.achievementGetted(GuessBandsModeTwo.this, R.string.achGuessBandsBeginner, R.drawable.guess_band_10, "achGuessBandsModeTwoBeginner")) //ачивочка
                {
                    achievemented = true;
                }
            }
            if (scoreNow + 1 == 50) { //ачивка за 50
                if (SomeMethods.achievementGetted(GuessBandsModeTwo.this, R.string.achGuessBandsNormal, R.drawable.guess_band_50, "achGuessBandsModeTwoNormal")) //ачивочка
                {
                    achievemented = true;
                }
            }
            if (scoreNow + 1 == 150) { //ачивка за 150
                if (SomeMethods.achievementGetted(GuessBandsModeTwo.this, R.string.achGuessBandsExpert, R.drawable.guess_band_150, "achGuessBandsModeTwoExpert")) //ачивочка
                {
                    achievemented = true;
                }
            }
            if (sound) {
                if (achievemented) {
                    soundPlayer.playSoundStream(grace);//звук правильного ответа
                } else {
                    soundPlayer.playSoundStream(longSwitchID);//звук правильного ответа
                }
            }

            change();
        } else {
            if (sound)
                soundPlayer.playSoundStream(pingClickID);//звук неправильного ответа
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
            buttons.get(positionButton).setVisibility(View.VISIBLE);
            if (buttons == buttonsEnd) {
                buttons.get(positionButton).setBackgroundResource(theme.getBackgroundButton());
                buttons.get(positionButton).setTextColor(Color.BLACK);
            }
            boolean check = true;
            for (Button b : buttonsEnd) {
                if (b.getText().equals("_")) {
                    check = false;
                }
            }
            if (check) {
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
            if (textEnd.equals("_")) {
                view.setVisibility(View.INVISIBLE);
            }
            if (textEnd.equals("")) {
                view.setBackgroundResource(R.drawable.roundedimageview);
                ((Button) view).setTextColor(theme.getTextColor());
            }

            slideButton.animate().setDuration(400)
                    .x(positionEnd[0])
                    .y(positionEnd[1])
                    .setListener(new AnimatorCustom(buttonsClick, positionButton, str));
        }
    }

    private void onRewardHint() {
        if (rewardedCustom.onLoaded() && onRewardedHint) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
            builder.setTitle(getResources().getString(R.string.endHintCongratulate))
                    .setMessage(String.format("%s", getResources().getString(R.string.endHintReward)))
                    .setCancelable(false)
                    .setNegativeButton(getResources().getString(R.string.endHintNo),
                            (dialogInterface, i) -> {
                            })
                    .setPositiveButton(getResources().getString(R.string.endHintYes), (dialogInterface, i) ->
                            rewardedCustom.show(GuessBandsModeTwo.this, new RewardedCustom.RewardedInterface() {
                                @Override
                                public void onRewarded() {
                                    showReward = true;
                                }

                                @Override
                                public void onDismissed() {
                                    if (showReward) {
                                        onRewardedHint = false;
                                        for (int i = 0; i < ref.length; i++) {
                                            if (i < countLetter && nameGroup.charAt(i) != ' ') {
                                                buttons.get(ref[i]).setText(String.format("%s", nameGroup.charAt(i)));
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
                                        onRewardedHint = true;
                                    }
                                    showReward = false;
                                }
                            }));
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void startLosingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, theme.getAlertDialogStyle());
        builder.setTitle(getResources().getString(R.string.endGameCongratulate))
                .setMessage(String.format("%s.\n%s! %s", getResources().getString(R.string.last_artist_text, bands.get(count).getName()),getResources().getString(R.string.score_text, scoreNow), getResources().getString(R.string.endGameNewGame)))
                .setCancelable(false)
                .setNegativeButton(getResources().getString(R.string.endGameNo), (dialogInterface, i) -> finish())
                .setPositiveButton(getResources().getString(R.string.endGameYes), (dialogInterface, i) -> {
                    heathBarTest.setHp(3);
                    scoreNow = -1;
                    hintCount = 4;
                    counterHint.setText(String.format(Locale.getDefault(), "%d", hintCount));
                    Storage storage = new Storage(this, "appStatus");
                    if (!storage.getBoolean("achTripleExpert")) {
                        if (countAd <= 0 && onRewarded) {
                            countAd = 5;
                            mInterstitialAd.show();
                        } else {
                            countAd--;
                        }
                    }else{
                        AppMetrica.reportEvent("ads 2.0", "{\"interstitial\":\"guessBands\"}");
                    }
                    onRewarded = true;
                    onRewardedHint = true;
                    change();
                });
        if (rewardedCustom.onLoaded() && onRewarded) {
            builder.setMessage(String.format("%s.\n%s! %s\n%s", getResources().getString(R.string.last_artist_text, bands.get(count).getName()),
                            getResources().getString(R.string.score_text, scoreNow),
                            getResources().getString(R.string.endGameNewGame), getResources().getString(R.string.endGameReward)))
                    .setNeutralButton(getResources().getString(R.string.endGameRewardShow), (dialogInterface, i) ->
                            rewardedCustom.show(GuessBandsModeTwo.this, new RewardedCustom.RewardedInterface() {
                                @Override
                                public void onRewarded() {
                                    showReward = true;
                                }

                                @Override
                                public void onDismissed() {
                                    if (showReward) {
                                        onRewarded = false;
                                        heathBarTest.restore();
                                    } else {
                                        heathBarTest.setHp(3);
                                        scoreNow = -1;
                                        hintCount = 4;
                                        counterHint.setText(String.format(Locale.getDefault(), "%d", hintCount));
                                        onRewarded = true;
                                        onRewardedHint = true;
                                        change();
                                    }
                                    showReward = false;
                                }
                            }));
        }
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}