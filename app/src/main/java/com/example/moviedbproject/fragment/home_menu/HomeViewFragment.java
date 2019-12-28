package com.example.moviedbproject.fragment.home_menu;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedbproject.R;
import com.example.moviedbproject.adapter.HomeViewRecyclerViewAdapter;
import com.example.moviedbproject.interfaces.Service;
import com.example.moviedbproject.tmdb.NetworkConnection;
import com.example.moviedbproject.tmdb.model.MoviesResponse;
import com.example.moviedbproject.utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeViewFragment extends Fragment {
    public static final String TAG = HomeViewFragment.class.getSimpleName();

    private View view;
    private EditText etSearchText;
    private ImageButton imgBtnSearch;
    private RecyclerView rvTopMovies;
    private LinearLayoutManager linearLayoutManager;
    private HomeViewRecyclerViewAdapter mAdapter;
    private LinearLayout linearLayoutPagination;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_view, container, false);
        initializeElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TopMoviesAsyncTask topMoviesAsyncTask = new TopMoviesAsyncTask(1);
        topMoviesAsyncTask.execute();
        imgBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etSearchText.getText().toString().equals("")) {
                    SearchedMoviesAsyncTask searchedMoviesAsyncTask = new SearchedMoviesAsyncTask(1, etSearchText.getText().toString());
                    searchedMoviesAsyncTask.execute();
                } else {
                    Toast.makeText(getContext(), R.string.home_view_fragment_character, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializeElements(View view) {
        etSearchText = view.findViewById(R.id.editText_search_bar);
        imgBtnSearch = view.findViewById(R.id.imageButton_search_button);
        rvTopMovies = view.findViewById(R.id.fragment_home_recyclerview_top_movies);
        linearLayoutPagination = view.findViewById(R.id.linearLayout_fragment_home_view_layout_for_pagination);
    }

    private class TopMoviesAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog = new ProgressDialog(getContext());
        private MoviesResponse moviesResponse;
        private int pageNumber;

        public TopMoviesAsyncTask(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Retrofit retrofit = NetworkConnection.getRetrofitClient();
            final Service service = retrofit.create(Service.class);
            Call call = service.getTopMovies(Constant.API_KEY, "en-US", "popularity.desc",
                    "false", "false", String.valueOf(pageNumber));
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.code() == Constant.GET_SUCCESS_CODE) {
                        moviesResponse = (MoviesResponse) response.body();
                        Constant.PAGES_NUMBER = moviesResponse.getTotal_pages();
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        mAdapter = new HomeViewRecyclerViewAdapter(getContext(), moviesResponse.getResults(), getActivity().getSupportFragmentManager());
                        rvTopMovies.setLayoutManager(linearLayoutManager);
                        rvTopMovies.setAdapter(mAdapter);
                        rvTopMovies.getViewTreeObserver()
                                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        linearLayoutPagination.removeAllViews();
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                LinearLayout.LayoutParams.MATCH_PARENT
                                        );
                                        params.setMargins(5, 0, 5, 0);
                                        for (int i = 0; i < moviesResponse.getTotal_pages(); ++i) {
                                            final int ii = i + 1;
                                            Button btn = new Button(getContext());
                                            btn.setText(String.valueOf(i + 1));
                                            btn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    TopMoviesAsyncTask topMoviesAsyncTask = new TopMoviesAsyncTask(ii);
                                                    topMoviesAsyncTask.execute();
                                                }
                                            });
                                            /*if (ii==pageNumber){
                                                btn.setBackgroundColor(getResources().getColor(R.color.blue));
                                            } else {
                                                btn.setBackgroundColor(getResources().getColor(R.color.white));
                                            }*/
                                            btn.setLayoutParams(params);
                                            linearLayoutPagination.addView(btn);
                                        }
                                        dialog.hide();
                                        rvTopMovies.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                    }
                                });
                    } else {
                        Toast.makeText(getContext(), R.string.login_fail_query_retrofit, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(getContext(), R.string.login_fail_no_internet, Toast.LENGTH_SHORT).show();
                }
            });
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

    private class SearchedMoviesAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog = new ProgressDialog(getContext());
        private MoviesResponse moviesResponse;
        private int pageNumber;
        private String sTitle;

        public SearchedMoviesAsyncTask(int pageNumber, String sTitle) {
            this.pageNumber = pageNumber;
            this.sTitle = sTitle;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Retrofit retrofit = NetworkConnection.getRetrofitClient();
            final Service service = retrofit.create(Service.class);
            Call call = service.getSearchedMovies(Constant.API_KEY, "en-US", sTitle,
                    String.valueOf(pageNumber), "false");
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.code() == Constant.GET_SUCCESS_CODE) {
                        moviesResponse = (MoviesResponse) response.body();
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        mAdapter = new HomeViewRecyclerViewAdapter(getContext(), moviesResponse.getResults(), getActivity().getSupportFragmentManager());
                        rvTopMovies.setLayoutManager(linearLayoutManager);
                        rvTopMovies.setAdapter(mAdapter);
                        rvTopMovies.getViewTreeObserver()
                                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        linearLayoutPagination.removeAllViews();
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                LinearLayout.LayoutParams.MATCH_PARENT
                                        );
                                        params.setMargins(5, 0, 5, 0);
                                        for (int i = 0; i < moviesResponse.getTotal_pages(); ++i) {
                                            final int ii = i + 1;
                                            Button btn = new Button(getContext());
                                            btn.setText(String.valueOf(i + 1));
                                            btn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    SearchedMoviesAsyncTask topMoviesAsyncTask = new SearchedMoviesAsyncTask(ii, sTitle);
                                                    topMoviesAsyncTask.execute();
                                                }
                                            });
                                            /*if (ii==pageNumber){
                                                btn.setBackgroundColor(getResources().getColor(R.color.blue));
                                            } else {
                                                btn.setBackgroundColor(getResources().getColor(R.color.white));
                                            }*/
                                            btn.setLayoutParams(params);
                                            linearLayoutPagination.addView(btn);
                                        }
                                        dialog.hide();
                                        rvTopMovies.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                    }
                                });
                    } else {
                        Toast.makeText(getContext(), R.string.login_fail_query_retrofit, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(getContext(), R.string.login_fail_no_internet, Toast.LENGTH_SHORT).show();
                }
            });
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
