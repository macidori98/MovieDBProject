package com.example.moviedbproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moviedbproject.database.DatabaseHelper;
import com.example.moviedbproject.database.model.User;
import com.example.moviedbproject.R;
import com.example.moviedbproject.utils.Constant;
import com.example.moviedbproject.utils.FragmentNavigation;

import java.util.List;

public class LoginFragment extends Fragment {
    public static final String TAG = LoginFragment.class.getSimpleName();

    private View view;
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        initializeElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (doesUserExist()){
                    FragmentNavigation.getInstance(getContext()).replaceFragment(new HomeFragment(), R.id.fragment_content);
                } else {
                    registerUser();
                }
            }
        });
    }

    private void initializeElements(View view){
        etPassword = view.findViewById(R.id.editText_login_password);
        etUsername = view.findViewById(R.id.editText_login_username);
        btnLogin = view.findViewById(R.id.button_login);
        db = new DatabaseHelper(getContext());
    }

    private boolean doesUserExist(){
        List<User> userList = db.getAllUsers();
        for (User user : userList) {
            if (user.getUsername().equals(etUsername.getText().toString()) &&
                    user.getPassword().equals(etPassword.getText().toString())){
                Constant.CURRENT_USER = user;
                return true;
            }
        }
        return false;
    }

    private void registerUser(){
        long id = db.insertUser(etUsername.getText().toString(), etPassword.getText().toString());
        User user = db.getUser(id);
        Constant.CURRENT_USER = user;
        FragmentNavigation.getInstance(getContext()).replaceFragment(new HomeFragment(), R.id.fragment_content);
    }
}
