package com.example.moviedbproject.fragment.home_menu;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedbproject.R;
import com.example.moviedbproject.adapter.HomeViewRecyclerViewAdapter;
import com.example.moviedbproject.fragment.HomeFragment;
import com.example.moviedbproject.fragment.LoginFragment;
import com.example.moviedbproject.interfaces.Service;
import com.example.moviedbproject.tmdb.NetworkConnection;
import com.example.moviedbproject.tmdb.model.Movies;
import com.example.moviedbproject.tmdb.model.MoviesResponse;
import com.example.moviedbproject.tmdb.model.NewToken;
import com.example.moviedbproject.utils.Constant;
import com.example.moviedbproject.utils.FragmentNavigation;

import java.util.List;

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_view, container,false);
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
        etSearchText = view.findViewById(R.id.editText_search_bar);
        imgBtnSearch = view.findViewById(R.id.imageButton_search_button);
        rvTopMovies = view.findViewById(R.id.fragment_home_recyclerview_top_movies);
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog=new ProgressDialog(getContext());
        private MoviesResponse moviesResponse;

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.hide();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //SystemClock.sleep(5000);
            Retrofit retrofit = NetworkConnection.getRetrofitClient();
            final Service service = retrofit.create(Service.class);
            Call call = service.getTopMovies();
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.code()==Constant.GET_SUCCESS_CODE){
                        moviesResponse = (MoviesResponse) response.body();
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        mAdapter = new HomeViewRecyclerViewAdapter(getContext(), moviesResponse.getResults(), getActivity().getSupportFragmentManager());
                        rvTopMovies.setLayoutManager(linearLayoutManager);
                        rvTopMovies.setAdapter(mAdapter);
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
