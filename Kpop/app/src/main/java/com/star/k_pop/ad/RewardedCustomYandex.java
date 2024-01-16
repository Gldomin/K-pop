package com.star.k_pop.ad;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yandex.mobile.ads.common.AdError;
import com.yandex.mobile.ads.common.AdRequestConfiguration;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.rewarded.Reward;
import com.yandex.mobile.ads.rewarded.RewardedAd;
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener;
import com.yandex.mobile.ads.rewarded.RewardedAdLoadListener;
import com.yandex.mobile.ads.rewarded.RewardedAdLoader;

public class RewardedCustomYandex extends RewardedCustom{

    private final String AdUnitId;
    private RewardedInterface yandexInterface;
    private RewardedAd mRewardedAd = null;
    @Nullable
    private final RewardedAdLoader mRewardedAdLoader;

    public RewardedCustomYandex(Activity context, String AdUnitId){
        this.AdUnitId = AdUnitId;
        mRewardedAdLoader = new RewardedAdLoader(context);
        mRewardedAdLoader.setAdLoadListener(new RewardedAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull final RewardedAd rewardedAd) {
                mRewardedAd = rewardedAd;
                // The ad was loaded successfully. Now you can show loaded ad.
            }

            @Override
            public void onAdFailedToLoad(@NonNull final AdRequestError adRequestError) {
                Log.e("TAGS", "ERROR LOADING REWARD " + adRequestError.getDescription());
                // Ad failed to load with AdRequestError.
                // Attempting to load a new ad from the onAdFailedToLoad() method is strongly discouraged.
            }
        });
        loadRewardedAd();
    }

    private void loadRewardedAd() {
        if (mRewardedAdLoader != null ) {
            final AdRequestConfiguration adRequestConfiguration =
                    new AdRequestConfiguration.Builder(AdUnitId).build();
            mRewardedAdLoader.loadAd(adRequestConfiguration);
        }
    }

    @Override
    public boolean onLoaded() {
        return mRewardedAd != null;
    }

    @Override
    public void show(Activity context, RewardedInterface rewardYandexInterface){
        yandexInterface = rewardYandexInterface;
        if (mRewardedAd != null) {
            mRewardedAd.setAdEventListener(new RewardedAdEventListener() {
                @Override
                public void onAdShown() {
                    // Called when ad is shown.
                }

                @Override
                public void onAdFailedToShow(@NonNull final AdError adError) {
                    // Called when an InterstitialAd failed to show.
                }

                @Override
                public void onAdDismissed() {
                    // Called when ad is dismissed.
                    // Clean resources after Ad dismissed
                    if (mRewardedAd != null) {
                        mRewardedAd.setAdEventListener(null);
                        mRewardedAd = null;
                    }
                    // Now you can preload the next interstitial ad.
                    loadRewardedAd();
                    yandexInterface.onDismissed();
                }

                @Override
                public void onAdClicked() {
                    // Called when a click is recorded for an ad.
                }

                @Override
                public void onAdImpression(@Nullable final ImpressionData impressionData) {
                    // Called when an impression is recorded for an ad.
                }

                @Override
                public void onRewarded(@NonNull final Reward reward) {
                    // Called when the user can be rewarded.
                    yandexInterface.onRewarded();
                }
            });
            mRewardedAd.show(context);
        }
    }
}
