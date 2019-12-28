package com.example.moviedbproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moviedbproject.fragment.LoginFragment;
import com.example.moviedbproject.utils.FragmentNavigation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentNavigation.getInstance(this).replaceFragment(new LoginFragment(), R.id.fragment_content);
    }

    @Override
    public void onBackPressed() {
        FragmentNavigation.getInstance(getApplicationContext()).onBackPressed(this);
    }
}
