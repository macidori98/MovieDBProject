package com.example.moviedbproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedbproject.R;
import com.example.moviedbproject.adapter.ImagesAdapter;
import com.example.moviedbproject.adapter.RelatedMoviesAdapter;
import com.example.moviedbproject.fragment.dialog.DescriptionDialog;
import com.example.moviedbproject.interfaces.OnItemClickListener;
import com.example.moviedbproject.interfaces.Service;
import com.example.moviedbproject.tmdb.NetworkConnection;
import com.example.moviedbproject.tmdb.model.ImageResponse;
import com.example.moviedbproject.tmdb.model.Movies;
import com.example.moviedbproject.tmdb.model.MoviesResponse;
import com.example.moviedbproject.tmdb.model.VideoResponse;
import com.example.moviedbproject.utils.Constant;
import com.example.moviedbproject.utils.FragmentNavigation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailScreenFragment extends Fragment {
    public static final String TAG = DescriptionDialog.class.getSimpleName();

    private View view;
    private WebView wvVideo;
    private ImageView ivClose, ivFavourite;
    private TextView tvTitle, tvDescription;
    private Movies movie;
    private LinearLayoutManager linearLayoutManager,linearLayoutManager2;
    private RecyclerView rvImages, rvRelatedMovies;
    private boolean bChecked;
    private ImagesAdapter imagesAdapter;
    private RelatedMoviesAdapter relatedMoviesAdapter;

    public DetailScreenFragment(Movies movie){
        this.movie = movie;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_screen, container, false);
        initializeElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitle.setText(movie.getTitle());
        tvDescription.setText(movie.getOverview());
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigation.getInstance(getContext()).popBackstack();
            }
        });
        ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivFavourite.setImageResource(R.drawable.favouritefull);
            }
        });
        loadVideo();
        loadImages();
        loadRelatedMovies();

    }

    private void loadImages(){
        Retrofit retrofit = NetworkConnection.getRetrofitClient();
        final Service service = retrofit.create(Service.class);
        Call call = service.getMovieImages(String.valueOf(movie.getId()), Constant.API_KEY);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.code() == Constant.GET_SUCCESS_CODE){
                    ImageResponse imageResponse = (ImageResponse) response.body();
                    imagesAdapter = new ImagesAdapter(getContext(), imageResponse.getBackdrops());
                    rvImages.setAdapter(imagesAdapter);
                } else {
                    Toast.makeText(getContext(), R.string.login_fail_query_retrofit, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getContext(), R.string.login_fail_no_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadVideo(){
        Retrofit retrofit = NetworkConnection.getRetrofitClient();
        final Service service = retrofit.create(Service.class);
        Call call = service.getMovieVideo(String.valueOf(movie.getId()), Constant.API_KEY, "en-US");
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.code() == Constant.GET_SUCCESS_CODE){
                    VideoResponse videoResponse = (VideoResponse) response.body();
                    wvVideo.loadData("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"+ videoResponse.getResults().get(0).getKey() +"\" frameborder=\"0\" allowfullscreen=\"allowfullscreen\"></iframe>","text/html" , "utf-8");
                } else {
                    Toast.makeText(getContext(), R.string.login_fail_query_retrofit, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getContext(), R.string.login_fail_no_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadRelatedMovies(){
        Retrofit retrofit = NetworkConnection.getRetrofitClient();
        final Service service = retrofit.create(Service.class);
        Call call = service.getSimilarMovies(String.valueOf(movie.getId()), Constant.API_KEY);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.code() == Constant.GET_SUCCESS_CODE){
                    final MoviesResponse moviesResponse = (MoviesResponse) response.body();
                    relatedMoviesAdapter = new RelatedMoviesAdapter(getContext(),moviesResponse.getResults());
                    relatedMoviesAdapter.setOnClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            FragmentNavigation.getInstance(getContext()).replaceFragment(new DetailScreenFragment(moviesResponse.getResults().get(position)), R.id.fragment_content);
                        }
                    });
                    rvRelatedMovies.setAdapter(relatedMoviesAdapter);
                } else {
                    Toast.makeText(getContext(), R.string.login_fail_query_retrofit, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getContext(), R.string.login_fail_no_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeElements(View view){
        wvVideo = view.findViewById(R.id.webView_detail_screen_fragment_video);
        wvVideo.getSettings().setJavaScriptEnabled(true);
        wvVideo.setWebChromeClient(new WebChromeClient() {
            //                Log.d("","");
        } );
        bChecked = false;
        rvImages = view.findViewById(R.id.recyclerView_linearLayout_detail_screen_fragment_images);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        rvImages.setLayoutManager(linearLayoutManager);
        ivClose = view.findViewById(R.id.imageView_detail_screen_fragment_close);
        ivFavourite = view.findViewById(R.id.imageView_detail_screen_fragment_favourite);
        tvTitle = view.findViewById(R.id.textView_detail_screen_fragment_title);
        tvDescription = view.findViewById(R.id.textView_detail_screen_fragment_description);
        rvRelatedMovies = view.findViewById(R.id.recyclerView_linearLayout_detail_screen_fragment_related_movies);
        rvRelatedMovies.setLayoutManager(linearLayoutManager2);
    }
}
