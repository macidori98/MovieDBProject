package com.example.moviedbproject.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.moviedbproject.R;
import com.example.moviedbproject.utils.Constant;

public class DetailDialog extends AppCompatDialogFragment {
    private String sDescription, sTitle;
    private TextView tvDescription;

    public DetailDialog(String sTitle, String sDescription){
        this.sDescription = sDescription;
        this.sTitle = sTitle;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_detail_description, null);
        tvDescription = view.findViewById(R.id.textView_dialog_detail_description);
        tvDescription.setText(sDescription);
        insertDescription(builder, view);
        return builder.create();
    }

    private void insertDescription(AlertDialog.Builder builder, View view){
        builder.setView(view).setTitle(Constant.DESCRIPTION + (sTitle))
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(Constant.CANCEL_DESCRIPTION, Constant.CANCEL_DESCRIPTION);
                    }
                });
    }
}
