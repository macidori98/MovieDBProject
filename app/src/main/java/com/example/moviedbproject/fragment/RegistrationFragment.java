package com.example.moviedbproject.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moviedbproject.R;
import com.example.moviedbproject.database.DatabaseHelper;
import com.example.moviedbproject.database.model.User;
import com.example.moviedbproject.fragment.dialog.InformativeDialog;
import com.example.moviedbproject.utils.Constant;
import com.example.moviedbproject.utils.FragmentNavigation;

import java.util.List;

public class RegistrationFragment extends Fragment {
    public static final String TAG = RegistrationFragment.class.getSimpleName();

    private View view;
    private Button btnRegistration;
    private EditText etUsername, etPassword, etConfirmPassword;
    private DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registration, container, false);
        initializeElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTextLengthEnough(etUsername.getText().toString())
                && isTextLengthEnough(etPassword.getText().toString())
                && isTextLengthEnough(etConfirmPassword.getText().toString())){
                    if (arePasswordsMatching(etPassword.getText().toString(), etConfirmPassword.getText().toString())){
                        MyAsyncTask myAsyncTask = new MyAsyncTask();
                        myAsyncTask.execute();
                    } else {
                        Toast.makeText(getContext(), R.string.change_password_dialog_new_confirm_new_passwords_dont_match, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.change_password_dialog_characters, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean doesUserExist() {
        List<User> userList = db.getAllUsers();
        for (User user : userList) {
            if (user.getUsername().equals(etUsername.getText().toString())) {
                Constant.CURRENT_USER = user;
                return true;
            }
        }
        return false;
    }

    private void registerUser() {
        long id = db.insertUser(etUsername.getText().toString(), etPassword.getText().toString());
        User user = db.getUser(id);
        Constant.CURRENT_USER = user;
        InformativeDialog informativeDialog = new InformativeDialog(Constant.SUCCESSFUL);
        informativeDialog.show(getFragmentManager(), Constant.SUCCESSFUL);
        FragmentNavigation.getInstance(getContext()).replaceFragment(new LoginFragment(), R.id.fragment_content);
    }

    private void initializeElements(View view){
        btnRegistration = view.findViewById(R.id.button_registration);
        etPassword = view.findViewById(R.id.editText_registration_password);
        etConfirmPassword = view.findViewById(R.id.editText_registration_confirm_password);
        etUsername = view.findViewById(R.id.editText_registration_username);
        db = new DatabaseHelper(getContext());
    }

    private boolean isTextLengthEnough(String string){
        return string.length() >= 5;
    }

    private boolean arePasswordsMatching(String s1, String s2){
        return s1.equals(s2);
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog = new ProgressDialog(getContext());

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.hide();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SystemClock.sleep(500);
            if (doesUserExist()) {
                InformativeDialog informativeDialog = new InformativeDialog(Constant.FAIL);
                informativeDialog.show(getFragmentManager(), Constant.FAIL);
            } else {
                registerUser();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage(Constant.INSERT_DATA);
            dialog.setCancelable(false);
            dialog.setInverseBackgroundForced(false);
            dialog.show();
        }
    }
}
