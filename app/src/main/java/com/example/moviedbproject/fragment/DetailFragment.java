package com.example.moviedbproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moviedbproject.R;
import com.example.moviedbproject.fragment.dialog.DetailDialog;

public class DetailFragment extends Fragment {
    public static final String TAG = DetailDialog.class.getSimpleName();

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail, container, false);

        return view;
    }


}
