package com.example.moviedbproject.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.moviedbproject.R;

public class InformativeDialog extends AppCompatDialogFragment {

    private String sTitle;

    public InformativeDialog(String title) {
        this.sTitle = title;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_informative, null);
        insertDescription(builder, view);
        return builder.create();
    }

    private void insertDescription(AlertDialog.Builder builder, View view) {
        builder.setView(view).setTitle(sTitle)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(sTitle, sTitle);
                    }
                });
    }
}
