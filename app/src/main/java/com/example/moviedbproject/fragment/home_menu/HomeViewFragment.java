package com.example.moviedbproject.fragment.home_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedbproject.R;

public class HomeViewFragment extends Fragment {
    public static final String TAG = HomeViewFragment.class.getSimpleName();

    private View view;
    private EditText etSearchText;
    private ImageButton imgBtnSearch;
    private RecyclerView rvTopMovies;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_view, container,false);
        initializeElements(view);
        return view;
    }

    private void initializeElements(View view){
        etSearchText = view.findViewById(R.id.editText_search_bar);
        imgBtnSearch = view.findViewById(R.id.imageButton_search_button);
        rvTopMovies = view.findViewById(R.id.fragment_home_recyclerview_top_movies);
    }
}
