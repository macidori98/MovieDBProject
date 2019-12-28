package com.example.moviedbproject.fragment.home_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moviedbproject.R;
import com.example.moviedbproject.fragment.dialog.ChangePasswordDialog;
import com.example.moviedbproject.utils.Constant;

public class ProfileFragment extends Fragment {
    public static final String TAG = ProfileFragment.class.getSimpleName();

    private View view;
    private Button btnChangePassword, btnChangeProfilePicture;
    private ImageView ivProfilePicture;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initializeElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog();
                changePasswordDialog.show(getFragmentManager(), Constant.CHANGE_PASSWORD_DIALOG_TAG);
            }
        });
    }

    private void initializeElements(View view){
        btnChangePassword = view.findViewById(R.id.button_profile_fragment_change_password);
        btnChangeProfilePicture = view.findViewById(R.id.button_profile_fragment_change_profile_picture);
        ivProfilePicture = view.findViewById(R.id.imageView_profile_fragment_image);
    }

}
