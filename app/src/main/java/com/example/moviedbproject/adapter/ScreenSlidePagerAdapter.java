package com.example.moviedbproject.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.moviedbproject.fragment.home_menu.FavouritesFragment;
import com.example.moviedbproject.fragment.home_menu.HomeViewFragment;
import com.example.moviedbproject.fragment.home_menu.NowInCinemasFragment;
import com.example.moviedbproject.fragment.home_menu.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = ScreenSlidePagerAdapter.class.getSimpleName();

    private static List<Fragment> mFragmentList = new ArrayList<>();

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentList.add(new ProfileFragment());
        mFragmentList.add(new HomeViewFragment());
        mFragmentList.add(new FavouritesFragment());
        mFragmentList.add(new NowInCinemasFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

}
