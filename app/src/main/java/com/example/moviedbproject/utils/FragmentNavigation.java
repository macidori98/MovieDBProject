package com.example.moviedbproject.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.moviedbproject.MainActivity;
import com.example.moviedbproject.R;
import com.example.moviedbproject.fragment.DetailScreenFragment;
import com.example.moviedbproject.fragment.LoginFragment;
import com.example.moviedbproject.fragment.RegistrationFragment;

public class FragmentNavigation extends Fragment {
    public final static String TAG = FragmentNavigation.class.getSimpleName();

    private static FragmentNavigation sInstance;
    private static FragmentManager mFragmentManager;
    private static FragmentTransaction mFragmentTransaction;
    private static Handler mHandler = new Handler();
    private static int mMainActivityFragmentContainer;
    private static boolean mDoubleBackToExitPressedOnce = false;
    private Activity act;

    public static FragmentNavigation getInstance(Context context) {

        if (sInstance == null) {
            mMainActivityFragmentContainer = R.id.fragment_content;
            sInstance = new FragmentNavigation();
            mFragmentManager = ((MainActivity) context).getSupportFragmentManager();
        }
        return sInstance;
    }

    private void addFragment(Fragment fragment, int container) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(container, fragment, fragment.getTag());
        mFragmentTransaction.addToBackStack(null);
        try {
            mFragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void replaceFragment(Fragment fragment, int container) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        Fragment topFragment = mFragmentManager.findFragmentById(container);
        if (topFragment == null) {
            // if there is nothing to replace, then add a new one:
            addFragment(fragment, container);
        } else {
            // if there is fragment to replace, then replace it:
            mFragmentTransaction.replace(container, fragment, fragment.getTag());
            mFragmentTransaction.addToBackStack(null);
            try {
                mFragmentTransaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Fragment getCurrentFragment(int container) {
        return mFragmentManager.findFragmentById(container);
    }

    public void onBackPressed(MainActivity activity) {

        // If Home page is open: double press exit:
        if (getCurrentFragment(mMainActivityFragmentContainer) instanceof LoginFragment) {
            doublePressExit(activity);
            return;
        }

        if (getCurrentFragment(mMainActivityFragmentContainer) instanceof RegistrationFragment) {
            popBackstack();
            return;
        }

        if (getCurrentFragment(mMainActivityFragmentContainer) instanceof DetailScreenFragment) {
            popBackstack();
            return;
        }

        // Other cases:
        activity.moveTaskToBack(true);
    }

    private void doublePressExit(MainActivity activity) {

        if (mDoubleBackToExitPressedOnce) {
            mDoubleBackToExitPressedOnce = false;
            activity.moveTaskToBack(true);
            return;
        }

        mDoubleBackToExitPressedOnce = true;
        Toast.makeText(activity, R.string.back_button_press, Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDoubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void popBackstack() {
        mFragmentManager.popBackStack();
    }

}
