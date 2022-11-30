package com.example.villageplanner.accountManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.villageplanner.HomeLogic.HomepageActivity;
import com.example.villageplanner.R;
import com.example.villageplanner.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class AccountPage extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Toolbar toolbar = findViewById(R.id.welcomeSign);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        String email = "guest@usc.edu";
        if (mAuth.getCurrentUser() == null) {
            toolbar.setTitle("Welcome, Guest!");

        }
        else {
            email = mAuth.getCurrentUser().getEmail();
            toolbar.setTitle("Welcome, " + email + "!");
        }

        ImageView imageView = findViewById(R.id.profilePic);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("/UserProfilePictures").child(email + "_pic").getDownloadUrl().addOnSuccessListener(uri -> Glide.with(getApplicationContext()).load(uri).into(imageView)).addOnFailureListener(exception -> {
        });;
    }

    public void goToHomepage(View view) {
        Intent i = new Intent(AccountPage.this, HomepageActivity.class);
        startActivity(i);
    }


    public void logOff(View view) {
        mAuth.signOut();
        Intent i = new Intent(AccountPage.this, LoginActivity.class);
        i.setFlags
                (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

}
