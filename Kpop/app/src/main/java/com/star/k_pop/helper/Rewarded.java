package com.star.k_pop.helper;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class Rewarded {

    public interface RewardDelay{
        void onShowDismissed();
    }

    Context context;
    RewardedAd mRewardedAd;
    RewardDelay rewardDelay;
    final String TAG = "Rewarded";

    public Rewarded(Context context) {
        this.context = context;
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallbackCustom());
    }

    public void show(Activity activity, OnUserEarnedRewardListener listener, RewardDelay rewardDelay) {
        this.rewardDelay = rewardDelay;
        if (mRewardedAd != null) {
            mRewardedAd.show(activity, listener);
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.");
        }
    }

    public boolean onLoaded(){
        return mRewardedAd != null;
    }

    class RewardedAdLoadCallbackCustom extends RewardedAdLoadCallback {

        @Override
        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
            Log.d(TAG, loadAdError.getMessage());
            mRewardedAd = null;
        }

        @Override
        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
            mRewardedAd = rewardedAd;
            Log.d(TAG, mRewardedAd.getRewardItem().getType());
            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdShowedFullScreenContent() {
                    Log.d(TAG, "Ad was shown.");
                    mRewardedAd = null;
                    AdRequest adRequest = new AdRequest.Builder().build();
                    RewardedAd.load(context, "ca-app-pub-3940256099942544/5224354917",
                            adRequest, new RewardedAdLoadCallbackCustom());
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    Log.d(TAG, "Ad failed to show.");
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    rewardDelay.onShowDismissed();
                    Log.d(TAG, "Ad was dismissed.");
                }
            });
            Log.d(TAG, "onAdFailedToLoad");
        }
    }
}
