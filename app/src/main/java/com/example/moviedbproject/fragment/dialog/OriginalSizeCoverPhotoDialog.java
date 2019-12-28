package com.example.moviedbproject.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.bumptech.glide.Glide;
import com.example.moviedbproject.R;
import com.example.moviedbproject.utils.Constant;


public class OriginalSizeCoverPhotoDialog extends AppCompatDialogFragment {
    private String URL;
    private ImageView imgOriginalCoverPhoto;

    public OriginalSizeCoverPhotoDialog(String URL) {
        this.URL = URL;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.original_size_cover_photo, null);
        imgOriginalCoverPhoto = view.findViewById(R.id.imageView_original_size_cover_photo);
        Glide.with(getActivity())
                .load(URL)
                .placeholder(R.drawable.loading)
                .fitCenter()
                .into(imgOriginalCoverPhoto);
        insertDescription(builder, view);
        return builder.create();
    }

    private void insertDescription(AlertDialog.Builder builder, View view) {
        builder.setView(view).setTitle(Constant.ORIGINAL_COVER)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(Constant.CANCEL_ORIGINAL_COVER, Constant.CANCEL_ORIGINAL_COVER);
                    }
                });
    }
}
