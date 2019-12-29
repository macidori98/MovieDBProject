package com.example.moviedbproject.fragment.home_menu;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private ImageButton ibSearch;
    private EditText etSearch;

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

    private void initializeElements(View view) {
        rvFavMovies = view.findViewById(R.id.recyclerView_favourites_fav_movies);
        ibSearch = view.findViewById(R.id.imageButton_fav_search_button);
        etSearch = view.findViewById(R.id.editText_fav_search_bar);
        db = new DatabaseHelper(getContext());
        moviesList = new ArrayList<>();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog = new ProgressDialog(getContext());

        @Override
        protected void onPostExecute(Void aVoid) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            rvFavMovies.setLayoutManager(linearLayoutManager);
            mAdapter = new HomeViewRecyclerViewAdapter(getContext(), moviesList, getFragmentManager());
            rvFavMovies.setAdapter(mAdapter);
            mAdapter.setOnClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                    FragmentNavigation.getInstance(getContext()).replaceFragment(new DetailScreenFragment(moviesList.get(position)), R.id.fragment_content);
                }
            });
            ibSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MySearchAsyncTask mySearchAsyncTask = new MySearchAsyncTask(etSearch.getText().toString());
                    mySearchAsyncTask.execute();
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
    private class MySearchAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog = new ProgressDialog(getContext());
        private List<Movies> movies = new ArrayList<>();
        private String sTitle;

        public MySearchAsyncTask(String sTitle){
            this.sTitle = sTitle;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            rvFavMovies.setLayoutManager(linearLayoutManager);
            mAdapter = new HomeViewRecyclerViewAdapter(getContext(), movies, getFragmentManager());
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
            Movies movie = db.getUsersFavMovieSearch(sTitle);
            if (movie != null) {
                movies.add(movie);
            }
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
