package com.star.k_pop.helper;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class Interstitial {

    Context context;
    InterstitialAd mInterstitialAd;
    int interstitialId;

    public Interstitial(Context context, int interstitialId) {
        this.context = context;
        this.interstitialId = interstitialId;
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, context.getResources().getString(interstitialId),
                adRequest, new InterstitialAdLoadCallbackCustom());
    }

    public void show(Activity activity) {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(activity);
        }
    }

    public boolean onLoaded() {
        return mInterstitialAd != null;
    }

    class InterstitialAdLoadCallbackCustom extends InterstitialAdLoadCallback {

        @Override
        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
            mInterstitialAd = null;
        }

        @Override
        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
            mInterstitialAd = interstitialAd;
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdShowedFullScreenContent() {
                    mInterstitialAd = null;
                    AdRequest adRequest = new AdRequest.Builder().build();
                    InterstitialAd.load(context, context.getResources().getString(interstitialId),
                            adRequest, new InterstitialAdLoadCallbackCustom());
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                }

                @Override
                public void onAdDismissedFullScreenContent() {

                }
            });
        }
    }

    /*

    private Interstitial mInterstitialAd;
    mInterstitialAd = new Interstitial(this,R.string.admob_id_interstitial);
    if (mInterstitialAd.onLoaded()) {
        mInterstitialAd.show(MainActivity.this);
    }

     */
}
