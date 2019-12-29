package com.example.moviedbproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedbproject.R;
import com.example.moviedbproject.fragment.dialog.DescriptionDialog;
import com.example.moviedbproject.fragment.dialog.OriginalSizeCoverPhotoDialog;
import com.example.moviedbproject.interfaces.OnItemClickListener;
import com.example.moviedbproject.tmdb.model.Movies;
import com.example.moviedbproject.utils.Constant;

import java.util.List;

public class HomeViewRecyclerViewAdapter extends RecyclerView.Adapter<HomeViewRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private FragmentManager fragmentManager;
    private List<Movies> moviesList;
    private OnItemClickListener listener;

    public HomeViewRecyclerViewAdapter(Context context, List<Movies> moviesList, FragmentManager man) {
        this.context = context;
        this.fragmentManager = man;
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_home_view_element, parent, false);
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTitle.setText(moviesList.get(position).getTitle());
        holder.tvDescription.setText(moviesList.get(position).getOverview());
        String URL = Constant.BASE_URL_IMAGE.concat("w185").concat(moviesList.get(position).getPoster_path());
        Glide.with(context).load(URL).into(holder.imgCoverPhoto);
        final int index = position;
        holder.tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DescriptionDialog detailDialog = new DescriptionDialog(moviesList.get(index).getTitle(), moviesList.get(index).getOverview());
                detailDialog.show(fragmentManager, Constant.DETAIL_DIALOG_TAG);
            }
        });
        holder.imgCoverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OriginalSizeCoverPhotoDialog originalSizeCoverPhotoDialog = new OriginalSizeCoverPhotoDialog(Constant.BASE_URL_IMAGE.concat("original").concat(moviesList.get(index).getPoster_path()));
                originalSizeCoverPhotoDialog.show(fragmentManager, Constant.ORIGINAL_COVER_DIALOG_TAG);
            }
        });
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgCoverPhoto;
        public TextView tvTitle, tvDescription, tvMore;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvMore = itemView.findViewById(R.id.textView_recyclerview_home_view_more);
            imgCoverPhoto = itemView.findViewById(R.id.imageView_recyclerview_home_view_cover_photo);
            tvTitle = itemView.findViewById(R.id.textView_recyclerview_home_view_title);
            tvDescription = itemView.findViewById(R.id.textView_recyclerview_home_view_description);
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
