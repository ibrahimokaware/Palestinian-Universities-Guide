package com.rivierasoft.palestinianuniversitiesguide;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;


public class LoadingAds {

    private static String TAG = "LoadingAds";

    public static RewardedInterstitialAd rewardedInterstitialAd;
    public static boolean isRewardedInterstitialAdLoading = false;

    public static RewardedInterstitialAd rewardedInterstitialAd2;
    public static boolean isRewardedInterstitialAdLoading2 = false;

    public static InterstitialAd mInterstitialAd;
    public static boolean isInterstitialAdLoading = false;

    public static InterstitialAd mInterstitialAd2;
    public static boolean isInterstitialAdLoading2 = false;

    public static void loadRewardedInterstitialAd(Context context) {
        RewardedInterstitialAd.load(context, "",
                new AdRequest.Builder().build(), new RewardedInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedInterstitialAd ad) {
                        rewardedInterstitialAd = ad;
                        isRewardedInterstitialAdLoading = true;
                    }
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        //Log.e(TAG, "onAdFailedToLoad");
                    }
                });
    }

    public static void loadRewardedInterstitialAd2(Context context) {
        RewardedInterstitialAd.load(context, "",
                new AdRequest.Builder().build(), new RewardedInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedInterstitialAd ad) {
                        rewardedInterstitialAd2 = ad;
                        isRewardedInterstitialAdLoading2 = true;
                    }
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        //Log.e(TAG, "onAdFailedToLoad");
                    }
                });
    }


    public static void loadInterstitialAd(Context context) {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context,"", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        isInterstitialAdLoading = true;
                        //Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        //Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }

    public static void loadInterstitialAd2(Context context) {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context,"", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd2 = interstitialAd;
                        isInterstitialAdLoading2 = true;
                        //Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        //Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd2 = null;
                    }
                });
    }
}
