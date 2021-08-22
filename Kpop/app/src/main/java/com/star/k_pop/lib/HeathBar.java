package com.star.k_pop.lib;

import android.view.animation.Animation;
import android.widget.ImageView;

import com.star.k_pop.R;

import java.util.ArrayList;

public class HeathBar {
    ArrayList<ImageView> heathBarImageViews;

    private int hp;
    private final int maxHp;
    Animation animation;

    public HeathBar(ArrayList<ImageView> heathBarImageViews, int hpNow, Animation animation) {
        this.heathBarImageViews = heathBarImageViews;
        hp = hpNow;
        maxHp = heathBarImageViews.size();
        this.animation = animation;
    }

    public void setHp(int hpNow) {
        hp = hpNow;
        refresh();

    }

    public int getHp() {
        return hp;
    }

    public void blow() { //минус 1 хп
        if (hp > 0) hp--;
        refresh();

    }

    public void restore() { //плюс 1 хп
        if (hp < maxHp) hp++;
        refresh();
    }

    public void refresh() // метод для обновления индикатора хп
    {

        for (int i = 0; i < heathBarImageViews.size(); i++) { //обратный for для тотбражения хп, путем замены картинок начиная с последней
            if (i >= hp) {
                heathBarImageViews.get(i).setImageResource(R.drawable.heart_colorless);
            } else {
                heathBarImageViews.get(i).setImageResource(R.drawable.heart_color);

                heathBarImageViews.get(i).startAnimation(animation);
            }

        }
        //heathBarImageViews.get(hp).setImageResource(R.drawable.hide);
    }
}
