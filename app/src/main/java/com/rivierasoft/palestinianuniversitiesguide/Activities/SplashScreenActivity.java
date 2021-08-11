package com.rivierasoft.palestinianuniversitiesguide.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.rivierasoft.palestinianuniversitiesguide.LoadingAds;
import com.rivierasoft.palestinianuniversitiesguide.R;

import java.util.Locale;

public class SplashScreenActivity extends AppCompatActivity {

    private Animation anim;
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        Configuration newConfig = new Configuration(res.getConfiguration());
        Locale locale = new Locale("ar");
        newConfig.locale = locale;
        newConfig.setLocale(locale);
        newConfig.setLayoutDirection(locale);
        res.updateConfiguration(newConfig, null);
        setContentView(R.layout.activity_splash_screen);

        imageView= findViewById(R.id.iv_splash);
        textView= findViewById(R.id.tv_splash);

        SharedPreferences sharedPreferences = getSharedPreferences("ads", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("ria", 0);
        editor.putInt("ria2", 0);
        editor.apply();

        if (!sharedPreferences.contains("p")) {
            editor.putBoolean("p", false);
            editor.putBoolean("p2", false);
            editor.apply();
        }

        LoadingAds.loadRewardedInterstitialAd(getApplicationContext());
        LoadingAds.loadRewardedInterstitialAd2(getApplicationContext());
        LoadingAds.loadInterstitialAd(getApplicationContext());
        LoadingAds.loadInterstitialAd2(getApplicationContext());

        // Declare an imageView to show the animation.
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                // HomeActivity.class is the activity to go after showing the splash screen.
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        imageView.startAnimation(anim);
        //textView.startAnimation(anim);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}