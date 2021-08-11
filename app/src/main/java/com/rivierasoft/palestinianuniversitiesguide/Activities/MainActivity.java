package com.rivierasoft.palestinianuniversitiesguide.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.android.material.tabs.TabLayout;
import com.rivierasoft.palestinianuniversitiesguide.Adapters.PagerAdapter;
import com.rivierasoft.palestinianuniversitiesguide.Connectivity;
import com.rivierasoft.palestinianuniversitiesguide.Fragments.UniversitiesFragment;
import com.rivierasoft.palestinianuniversitiesguide.LoadingAds;
import com.rivierasoft.palestinianuniversitiesguide.Models.MyTab;
import com.rivierasoft.palestinianuniversitiesguide.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity { //implements OnUserEarnedRewardListener

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

//    private RewardedInterstitialAd rewardedInterstitialAd;
//    private String TAG = "MainActivity";


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
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        setSupportActionBar(toolbar);

        LoadingAds.loadRewardedInterstitialAd(getApplicationContext());
        LoadingAds.loadRewardedInterstitialAd2(getApplicationContext());
        LoadingAds.loadInterstitialAd(getApplicationContext());
        LoadingAds.loadInterstitialAd2(getApplicationContext());

        Connectivity.checkConnection(this);

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//                //loadAd();
//            }
//        });



//        uniRef.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        //String name = document.getString("name");
//                        Toast.makeText(getApplicationContext(), document.getString("name"), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        db.collection("/university")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(getApplicationContext(), document.getString("name"), Toast.LENGTH_SHORT).show();
//                                //Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Error getting documents.", Toast.LENGTH_SHORT).show();
//                            //Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });

//        db.collection("universities")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(getApplicationContext(), document.getString("name"), Toast.LENGTH_SHORT).show();
//                                //Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Error getting documents.", Toast.LENGTH_SHORT).show();
//                            //Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });

        /*String locale = Locale.getDefault().getLanguage();
        int lo =0;
        //Toast.makeText(getApplicationContext(),locale,Toast.LENGTH_SHORT).show();

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        if (locale.equals("ar")||locale.equals("ur")||locale.equals("fa")) {
            pagerAdapter.addTab(new MyTab("كليات المجتمع",UniversitiesFragment.newInstance(3,"كليات المجتمع")));
            pagerAdapter.addTab(new MyTab("الكليات الجامعية",UniversitiesFragment.newInstance(2,"الكليات الجامعية")));
            pagerAdapter.addTab(new MyTab("الجامعات",UniversitiesFragment.newInstance(1,"الجامعات")));
            lo=2;
        }
        else {
            pagerAdapter.addTab(new MyTab("الجامعات",UniversitiesFragment.newInstance(1,"الجامعات")));
            pagerAdapter.addTab(new MyTab("الكليات الجامعية",UniversitiesFragment.newInstance(2,"الكليات الجامعية")));
            pagerAdapter.addTab(new MyTab("كليات المجتمع",UniversitiesFragment.newInstance(3,"كليات المجتمع")));
        }*/

                PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

                pagerAdapter.addTab(new MyTab("جامعات غزة", UniversitiesFragment.newInstance(0, "g")));
                pagerAdapter.addTab(new MyTab("جامعات الضفة", UniversitiesFragment.newInstance(1, "b")));

                viewPager.setAdapter(pagerAdapter);
                tabLayout.setupWithViewPager(viewPager);

                int lo = 1;

                TabLayout.Tab tab = tabLayout.getTabAt(lo);
                tab.select();

                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_design, menu);
        return true;
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//
//        database = new MyDatabase(getApplicationContext());
//        ArrayList<User> users = database.getLoggedUsers();
//        //  check logged users
//        if (users.size() != 0) {
//            //  user logged
//            menu.findItem(R.id.item_sign_in).setVisible(false);
//            menu.findItem(R.id.item_sign_out).setVisible(true);
//        } else {
//            //   user not logged
//            menu.findItem(R.id.item_sign_in).setVisible(true);
//            menu.findItem(R.id.item_sign_out).setVisible(false);
//        }
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_search:
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                break;
            case R.id.item_future:
                startActivity(new Intent(getApplicationContext(), WebActivity.class).putExtra("link",
                        "https://www.for9a.com/learn/%D9%85%D8%A7-%D9%87%D9%8A-%D8%AA%D8%AE%D8%B5%D8%B5%D8%A7%D8%AA-%D8%A7%D9%84%D9%85%D8%B3%D8%AA%D9%82%D8%A8%D9%84-%D9%88-%D9%83%D9%8A%D9%81-%D8%A7%D8%AE%D8%AA%D8%A7%D8%B1-%D8%AA%D8%AE%D8%B5%D8%B5%D9%8A"));
//                startActivity(new Intent(getApplicationContext(), AddDataActivity.class));
                break;
            case R.id.item_saved:
                startActivity(new Intent(getApplicationContext(), SavedProgramsActivity.class));
                break;
            case R.id.item_contact:
                contactUsDialog();
                break;
            case R.id.item_other_app:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Riviera+Soft"));
                    startActivity(intent);
                } catch (Exception ex) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Riviera+Soft")));
                }
                break;
            case R.id.item_exit:
                finishAffinity();
//                rewardedInterstitialAd.show(/* Activity */ MainActivity.this,/*
//    OnUserEarnedRewardListener */ new OnUserEarnedRewardListener() {
//                    @Override
//                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
//                        Toast.makeText(getApplicationContext(), "onUserEarnedReward", Toast.LENGTH_SHORT).show();
//                    }
//                });
                break;
        }
        return true;
    }

    public void contactUsDialog(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_contact_us);

        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView cancelTV = dialog.findViewById(R.id.cancelTextView);
        ImageView gmailIV = dialog.findViewById(R.id.gmail_iv);
        ImageView whatsappIV = dialog.findViewById(R.id.whatsapp_iv);

        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        whatsappIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.whatsapp.com/send?phone=+972599195534";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.whatsapp");
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        gmailIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"rivierasoft@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");

                intent.setPackage("com.google.android.gm");
                intent.setType("message/rfc822");
                //intent.setData(Uri.parse("mailto:your.email@gmail.com"));
                startActivity(intent);
            }
        });

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

//    public void loadAd(){
//        RewardedInterstitialAd.load(MainActivity.this, "ca-app-pub-3940256099942544/5354046379",
//                new AdRequest.Builder().build(), new RewardedInterstitialAdLoadCallback() {
//                    @Override
//                    public void onAdLoaded(RewardedInterstitialAd ad) {
//                        rewardedInterstitialAd = ad;
//                        rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                            /** Called when the ad failed to show full screen content. */
//                            @Override
//                            public void onAdFailedToShowFullScreenContent(AdError adError) {
//                                Log.i(TAG, "onAdFailedToShowFullScreenContent");
//                            }
//
//                            /** Called when ad showed the full screen content. */
//                            @Override
//                            public void onAdShowedFullScreenContent() {
//                                Log.i(TAG, "onAdShowedFullScreenContent");
//                            }
//
//                            /** Called when full screen content is dismissed. */
//                            @Override
//                            public void onAdDismissedFullScreenContent() {
//                                Log.i(TAG, "onAdDismissedFullScreenContent");
//                            }
//                        });
//                    }
//                    @Override
//                    public void onAdFailedToLoad(LoadAdError loadAdError) {
//                        Log.e(TAG, "onAdFailedToLoad");
//                    }
//                });
//    }

//    @Override
//    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
//        Log.i(TAG, "onUserEarnedReward");
//        // TODO: Reward the user!
//    }
}