package com.example.villageplanner;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImagePicker extends AppCompatActivity {

    private Button pickImage;
    private Button submit;
    private ImageView IVPreviewImage;
    private Uri image;


    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri selectedImageUri) {
                    // Handle the returned Uri
                    if (null != selectedImageUri) {
                        // update the preview image in the layout
                        IVPreviewImage.setImageURI(selectedImageUri);
                        image = selectedImageUri;
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);

        // register the UI widgets with their appropriate IDs
        pickImage = (Button) findViewById(R.id.imagePicker);
        IVPreviewImage = (ImageView) findViewById(R.id.IVPreviewImage);
        submit = (Button) findViewById(R.id.submitImage);

        // handle the Choose Image button to trigger
        // the image chooser function
        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(image != null) {
                    // Upload image to Firebase
                }
            }
        });
    }


}