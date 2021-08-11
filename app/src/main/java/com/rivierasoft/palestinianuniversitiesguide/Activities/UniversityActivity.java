package com.rivierasoft.palestinianuniversitiesguide.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rivierasoft.palestinianuniversitiesguide.Adapters.PagerAdapter;
import com.rivierasoft.palestinianuniversitiesguide.Connectivity;
import com.rivierasoft.palestinianuniversitiesguide.Fragments.InfoFragment;
import com.rivierasoft.palestinianuniversitiesguide.Fragments.ProgramsFragment;
import com.rivierasoft.palestinianuniversitiesguide.Models.MyTab;
import com.rivierasoft.palestinianuniversitiesguide.Models.University;
import com.rivierasoft.palestinianuniversitiesguide.R;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class UniversityActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ImageView backIV, coverIV, logoIV;
    private TextView nameTV;

    private University university;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
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
        setContentView(R.layout.activity_university);

        Connectivity.checkConnection(this);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        university = (University) intent.getSerializableExtra("data");

        backIV = findViewById(R.id.iv_back);
        coverIV = findViewById(R.id.iv_cover);
        logoIV = findViewById(R.id.iv_logo);
        nameTV = findViewById(R.id.tv_name);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        pagerAdapter.addTab(new MyTab("معلومات الاتصال", InfoFragment.newInstance("c", university)));
        pagerAdapter.addTab(new MyTab("الطلاب الحاليون", InfoFragment.newInstance("cs", university)));
        pagerAdapter.addTab(new MyTab("الطلاب الجدد", InfoFragment.newInstance("ns", university)));
        pagerAdapter.addTab(new MyTab("البرامج", ProgramsFragment.newInstance(university, "")));
        pagerAdapter.addTab(new MyTab("عن الجامعة", InfoFragment.newInstance("a", university)));

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        int lo = 4;

        TabLayout.Tab tab = tabLayout.getTabAt(lo);
        tab.select();

        Picasso.with(getApplicationContext())
                .load(university.getCover())
                .placeholder(R.drawable.app_icon)
                //.error(R.drawable.ic_broken_image)
                .into(coverIV);

        Picasso.with(getApplicationContext())
                .load(university.getLogo())
                .placeholder(R.drawable.app_icon)
                //.error(R.drawable.ic_broken_image)
                .into(logoIV);

        nameTV.setText(university.getName());

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

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finish();
        //startActivity(new Intent(getApplicationContext(),TabbedActivity.class));
        //getIntent().setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    }
}