package com.rivierasoft.palestinianuniversitiesguide.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.rivierasoft.palestinianuniversitiesguide.Models.MyTab;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<MyTab> myTabArrayList = new ArrayList<>();

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void addTab (MyTab myTab) {
        myTabArrayList.add(myTab);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return myTabArrayList.get(position).getFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return myTabArrayList.get(position).getTabName();
    }

    @Override
    public int getCount() {
        return myTabArrayList.size();
    }
}
