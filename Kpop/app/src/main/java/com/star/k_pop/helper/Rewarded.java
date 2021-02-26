package com.star.k_pop.helper;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.star.k_pop.R;

public class Rewarded {

    public interface RewardDelay {
        void onShowDismissed();
    }

    Context context;
    RewardedAd mRewardedAd;
    RewardDelay rewardDelay;
    int rewardId;

    public Rewarded(Context context, int rewardId) {
        this.context = context;
        this.rewardId = rewardId;
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, context.getResources().getString(rewardId),
                adRequest, new RewardedAdLoadCallbackCustom());
    }

    public void show(Activity activity, OnUserEarnedRewardListener listener, RewardDelay rewardDelay) {
        this.rewardDelay = rewardDelay;
        if (mRewardedAd != null) {
            mRewardedAd.show(activity, listener);
        }
    }

    public boolean onLoaded() {
        return mRewardedAd != null;
    }

    class RewardedAdLoadCallbackCustom extends RewardedAdLoadCallback {

        @Override
        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
            mRewardedAd = null;
        }

        @Override
        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
            mRewardedAd = rewardedAd;
            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdShowedFullScreenContent() {
                    mRewardedAd = null;
                    AdRequest adRequest = new AdRequest.Builder().build();
                    RewardedAd.load(context, context.getResources().getString(rewardId),
                            adRequest, new RewardedAdLoadCallbackCustom());
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    rewardDelay.onShowDismissed();
                }
            });
        }
    }
}
