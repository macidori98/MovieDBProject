package com.example.moviedbproject.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.moviedbproject.R;
import com.example.moviedbproject.adapter.ScreenSlidePagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.content.Context.SEARCH_SERVICE;

public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getSimpleName();

    private View view;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        bottomNavigationView = view.findViewById(R.id.fragment_home_bottom_navigation);
        viewPager = view.findViewById(R.id.fragment_home_viewpager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewPager();
        setupBottomNavigation();
    }

    private void setupViewPager(){
        viewPager.setAdapter(new ScreenSlidePagerAdapter(getChildFragmentManager()));
        viewPager.setOnPageChangeListener(new PageChange());
        viewPager.setCurrentItem(1);
    }

    private void setupBottomNavigation(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_home:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.action_favourite:
                        viewPager.setCurrentItem(2);
                        return true;
                    case R.id.action_now_in_cinemas:
                        viewPager.setCurrentItem(3);
                        return true;
                    case R.id.action_profile:
                        viewPager.setCurrentItem(0);
                        return true;
                }
                return false;
            }
        });
    }

    public class PageChange implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    bottomNavigationView.setSelectedItemId(R.id.action_profile);
                    break;
                case 1:
                    bottomNavigationView.setSelectedItemId(R.id.action_home);
                    break;
                case 2:
                    bottomNavigationView.setSelectedItemId(R.id.action_favourite);
                    break;
                case 3:
                    bottomNavigationView.setSelectedItemId(R.id.action_now_in_cinemas);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
