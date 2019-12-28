package com.example.moviedbproject.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.moviedbproject.R;
import com.example.moviedbproject.database.DatabaseHelper;
import com.example.moviedbproject.database.model.User;
import com.example.moviedbproject.utils.Constant;


public class ChangePasswordDialog extends AppCompatDialogFragment {

    private EditText etNewPassword, etOldPassword, etConfirmNewPassword;

    public ChangePasswordDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_change_password, null);
        etConfirmNewPassword = view.findViewById(R.id.editText_change_password_confirm_new_password);
        etNewPassword = view.findViewById(R.id.editText_change_password_new_password);
        etOldPassword = view.findViewById(R.id.editText_change_password_old_password);
        changePassword(builder, view);
        return builder.create();
    }

    private void changePassword(AlertDialog.Builder builder, View view) {
        builder.setView(view).setTitle(R.string.change_password_dialog_title)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), R.string.cancel, Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sOldPassword = etOldPassword.getText().toString();
                        String sNewPassword = etNewPassword.getText().toString();
                        String sConfirmNewPassword = etConfirmNewPassword.getText().toString();
                        if (!arePasswordsMatch(sOldPassword, sNewPassword) && arePasswordsMatch(Constant.CURRENT_USER.getPassword(), sOldPassword)) {
                            if (arePasswordsMatch(sNewPassword, sConfirmNewPassword)) {
                                if (isPasswordLengthOk(sNewPassword)) {
                                    User user = new User(Constant.CURRENT_USER.getId(), Constant.CURRENT_USER.getUsername(), etNewPassword.getText().toString());
                                    Constant.CURRENT_USER.setPassword(etNewPassword.getText().toString());
                                    DatabaseHelper db = new DatabaseHelper(getContext());
                                    int index = db.updateUser(user);
                                    Toast.makeText(getContext(), R.string.change_password_dialog_successful, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), R.string.change_password_dialog_characters, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), R.string.change_password_dialog_new_confirm_new_passwords_dont_match, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), R.string.change_password_dialog_old_new_password_match, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean isPasswordLengthOk(String password) {
        return password.length() >= 5 && password.length() <= 20;
    }

    private boolean arePasswordsMatch(String np, String cnp) {
        return np.equals(cnp);
    }
}
