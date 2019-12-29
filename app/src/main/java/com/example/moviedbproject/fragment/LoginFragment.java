package com.example.moviedbproject.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moviedbproject.R;
import com.example.moviedbproject.database.DatabaseHelper;
import com.example.moviedbproject.database.model.User;
import com.example.moviedbproject.interfaces.Service;
import com.example.moviedbproject.tmdb.NetworkConnection;
import com.example.moviedbproject.tmdb.model.NewToken;
import com.example.moviedbproject.utils.Constant;
import com.example.moviedbproject.utils.FragmentNavigation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginFragment extends Fragment {
    public static final String TAG = LoginFragment.class.getSimpleName();

    private View view;
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private DatabaseHelper db;
    private TextView tvRegistration;

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
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    private void initializeElements(View view) {
        etPassword = view.findViewById(R.id.editText_login_password);
        etUsername = view.findViewById(R.id.editText_login_username);
        btnLogin = view.findViewById(R.id.button_login);
        db = new DatabaseHelper(getContext());
        tvRegistration = view.findViewById(R.id.textView_login_registration);
    }

    private boolean doesUserExist() {
        List<User> userList = db.getAllUsers();
        for (User user : userList) {
            if (user.getUsername().equals(etUsername.getText().toString()) &&
                    user.getPassword().equals(etPassword.getText().toString())) {
                Constant.CURRENT_USER = user;
                return true;
            }
        }
        return false;
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tvRegistration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentNavigation.getInstance(getContext()).replaceFragment(new RegistrationFragment(), R.id.fragment_content);
                }
            });
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (doesUserExist()) {
                        FragmentNavigation.getInstance(getContext()).replaceFragment(new HomeFragment(), R.id.fragment_content);
                    } else {
                        Toast.makeText(getContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Retrofit retrofit = NetworkConnection.getRetrofitClient();
            final Service service = retrofit.create(Service.class);

            Call call = service.getNewToken(Constant.API_KEY);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.code() == Constant.GET_SUCCESS_CODE) {
                        NewToken newToken = (NewToken) response.body();
                        Constant.CURRENT_TOKEN = newToken.getRequest_token();
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
    }
}
