package com.star.k_pop.lib;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.star.k_pop.R;

import java.util.ArrayList;
import java.util.List;

public class HeathBar {
    ArrayList<ImageView> heathBarImageViews;
    Context context;
    private int hp;
    private int maxHp;

    public HeathBar(ArrayList<ImageView> heathBarImageViews, int hpNow) {
        //List <Integer> heathBarImageViewsID;
        //for (int i=0; i<heathBarImageViews.size();i++)
        this.heathBarImageViews = heathBarImageViews;
        this.context = context;
        hp = hpNow;
        maxHp = heathBarImageViews.size();
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
        Log.v("test", "Кнопка работает, hp=" + hp);
        for (int i = 0; i < heathBarImageViews.size(); i++) { //обратный for для тотбражения хп, путем замены картинок начиная с последней
            if (i >= hp)
                heathBarImageViews.get(i).setImageResource(R.drawable.heart_colorless);
            else
                heathBarImageViews.get(i).setImageResource(R.drawable.heart_color);
        }
        //heathBarImageViews.get(hp).setImageResource(R.drawable.hide);
    }
}
