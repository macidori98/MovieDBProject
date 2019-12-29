package com.example.moviedbproject.fragment.home_menu;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedbproject.R;
import com.example.moviedbproject.adapter.HomeViewRecyclerViewAdapter;
import com.example.moviedbproject.database.DatabaseHelper;
import com.example.moviedbproject.fragment.DetailScreenFragment;
import com.example.moviedbproject.interfaces.OnItemClickListener;
import com.example.moviedbproject.tmdb.model.Movies;
import com.example.moviedbproject.utils.Constant;
import com.example.moviedbproject.utils.FragmentNavigation;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment {
    public static final String TAG = FavouritesFragment.class.getSimpleName();

    private View view;
    private RecyclerView rvFavMovies;
    private HomeViewRecyclerViewAdapter mAdapter;
    private DatabaseHelper db;
    private List<Movies> moviesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favourites, container, false);
        initializeElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    private void initializeElements(View view){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvFavMovies = view.findViewById(R.id.recyclerView_favourites_fav_movies);
        rvFavMovies.setLayoutManager(linearLayoutManager);
        db = new DatabaseHelper(getContext());
        moviesList = new ArrayList<>();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog = new ProgressDialog(getContext());

        @Override
        protected void onPostExecute(Void aVoid) {
            mAdapter = new HomeViewRecyclerViewAdapter(getContext(), moviesList, getFragmentManager());
            rvFavMovies.setAdapter(mAdapter);
            mAdapter.setOnClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                    FragmentNavigation.getInstance(getContext()).replaceFragment(new DetailScreenFragment(moviesList.get(position)), R.id.fragment_content);
                }
            });
            dialog.hide();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            moviesList = db.getUserFavMovies();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage(Constant.GET_DATA);
            dialog.setCancelable(false);
            dialog.setInverseBackgroundForced(false);
            dialog.show();
        }
    }
}
