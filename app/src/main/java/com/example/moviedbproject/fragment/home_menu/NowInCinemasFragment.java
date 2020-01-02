package com.example.moviedbproject.fragment.home_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedbproject.R;
import com.example.moviedbproject.adapter.HomeViewRecyclerViewAdapter;
import com.example.moviedbproject.service.MyService;

public class NowInCinemasFragment extends Fragment {
    public static final String TAG = NowInCinemasFragment.class.getSimpleName();

    private View view;
    private RecyclerView rvNowInCinema;
    private EditText etSearch;
    private ImageButton ibSearch;
    private HomeViewRecyclerViewAdapter mAdapter;
    private LinearLayout linearLayoutPagination;
    private TextView tv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_now_in_cinema, container, false);
        initializeElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initializeElements(View view){
        tv = view.findViewById(R.id.proba);
        rvNowInCinema = view.findViewById(R.id.fragment_now_in_cinema_recyclerview);
        etSearch = view.findViewById(R.id.editText_now_in_cinema_search_bar);
        ibSearch = view.findViewById(R.id.imageButton_now_in_cinema_search_button);
        linearLayoutPagination = view.findViewById(R.id.linearLayout_fragment_now_in_cinema_layout_for_pagination);
    }
}
