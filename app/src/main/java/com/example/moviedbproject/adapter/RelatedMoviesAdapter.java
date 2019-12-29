package com.example.moviedbproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedbproject.R;
import com.example.moviedbproject.interfaces.OnItemClickListener;
import com.example.moviedbproject.tmdb.model.Movies;
import com.example.moviedbproject.utils.Constant;

import java.util.List;

public class RelatedMoviesAdapter extends RecyclerView.Adapter<RelatedMoviesAdapter.MyViewHolder>{

    private Context context;
    private List<Movies> moviesList;
    private OnItemClickListener listener;

    public RelatedMoviesAdapter(Context context, List<Movies> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public RelatedMoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_related_movies_element, parent, false);
        return new RelatedMoviesAdapter.MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedMoviesAdapter.MyViewHolder holder, int position) {
        String URL = Constant.BASE_URL_IMAGE.concat("w185").concat(moviesList.get(position).getPoster_path());
        Glide.with(context).load(URL).into(holder.ivMovieCover);
        holder.tvTitle.setText(moviesList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivMovieCover;
        public TextView tvTitle;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            ivMovieCover = itemView.findViewById(R.id.imageView_recyclerview_related_movies_image);
            tvTitle = itemView.findViewById(R.id.textView_recyclerview_related_movies_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
