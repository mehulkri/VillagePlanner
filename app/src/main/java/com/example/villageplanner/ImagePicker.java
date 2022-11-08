package com.example.villageplanner;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.villageplanner.createAccount.CreateAccount;
import com.example.villageplanner.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ImagePicker extends AppCompatActivity {

    private Button pickImage;
    private Button submit;
    private ImageView IVPreviewImage;
    private Uri image;
    private StorageReference storageRef;


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
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    String email = auth.getCurrentUser().getEmail();
                    storageRef = FirebaseStorage.getInstance().getReference().child("/UserProfilePictures").child(email + "_pic");
                    storageRef.putFile(image)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(ImagePicker.this, "Image Upload was successful", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ImagePicker.this, "Image Upload failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                Intent create = new Intent(ImagePicker.this , LoginActivity.class);
                startActivity(create);
            }
        });
    }


}