package com.star.k_pop.ad;

import android.app.Activity;

public abstract class RewardedCustom {

    public abstract boolean onLoaded();

    public abstract void show(Activity context, RewardedInterface rewardedInterface);

    public interface RewardedInterface{
        void onRewarded();
        void onDismissed();
    }
}
