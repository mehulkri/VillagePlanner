package com.example.villageplanner;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.villageplanner.HomeLogic.HomepageActivity;
import com.example.villageplanner.accountManager.AccountPage;
import com.example.villageplanner.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ImagePicker extends AppCompatActivity {

    private Button pickImage;
    private Button submit;
    private ImageView IVPreviewImage;
    private Uri image;
    private StorageReference storageRef;
    private String calledFrom = "";


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
                    String email = getIntent().getStringExtra("Email");
                    String name = getIntent().getStringExtra("Name");
                    String calledFrom = getIntent().getStringExtra("calledFrom");
                    if(email == null) {
                        email = "guest@usc.edu";
                    }
                    storageRef = FirebaseStorage.getInstance().getReference().child("/UserProfilePictures").child(email + "_pic");
                    storageRef.putFile(image)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String name = getIntent().getStringExtra("Name");
                                    if(name == null) {
                                        name = "Name Unknown";
                                    }
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name)
                                            .setPhotoUri(image)
                                            .build();

                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "User profile updated.");
                                                    }
                                                }
                                            });

                                    Toast.makeText(ImagePicker.this, "Image Upload was successful", Toast.LENGTH_SHORT).show();
                                    if (calledFrom.equals("createAccount")) {
                                        Intent i = new Intent(ImagePicker.this, HomepageActivity.class);
                                        startActivity(i);
                                    }
                                    else {
                                        Intent i = new Intent(ImagePicker.this, AccountPage.class);
                                        startActivity(i);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ImagePicker.this, "Image Upload failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        });
    }


}