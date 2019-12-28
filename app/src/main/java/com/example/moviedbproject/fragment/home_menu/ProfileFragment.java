package com.example.moviedbproject.fragment.home_menu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moviedbproject.R;
import com.example.moviedbproject.database.DatabaseHelper;
import com.example.moviedbproject.database.model.Image;
import com.example.moviedbproject.fragment.dialog.ChangePasswordDialog;
import com.example.moviedbproject.utils.Constant;

import java.net.URI;

public class ProfileFragment extends Fragment {
    public static final String TAG = ProfileFragment.class.getSimpleName();

    private View view;
    private Button btnChangePassword, btnChangeProfilePicture;
    private ImageView ivProfilePicture;
    private Uri selectedImage;
    private Image image;

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
        DatabaseHelper db = new DatabaseHelper(getContext());
        image = db.getImage(Constant.CURRENT_USER.getId());
        if (image != null) {
            ivProfilePicture.setImageURI(Uri.parse(image.getName()));
        }
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog();
                changePasswordDialog.show(getFragmentManager(), Constant.CHANGE_PASSWORD_DIALOG_TAG);
            }
        });
        btnChangeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                // Sets the type as image/*. This ensures only components of type image are selected
                intent.setType("image/*");
                //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                // Launching the Intent
                startActivityForResult(intent,1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case 1:
                    //data.getData returns the content URI for the selected Image
                    selectedImage = data.getData();
                    ivProfilePicture.setImageURI(selectedImage);
                    DatabaseHelper db = new DatabaseHelper(getContext());
                    if (image != null){
                        Image image2 = new Image(image.getId(), String.valueOf(selectedImage), Constant.CURRENT_USER.getId(), null);
                        db.updateImage(image2);
                    } else {
                        db.insertImage(String.valueOf(selectedImage),null);
                    }
                    break;
            }
    }

    private void initializeElements(View view){
        btnChangePassword = view.findViewById(R.id.button_profile_fragment_change_password);
        btnChangeProfilePicture = view.findViewById(R.id.button_profile_fragment_change_profile_picture);
        ivProfilePicture = view.findViewById(R.id.imageView_profile_fragment_image);
    }

}
