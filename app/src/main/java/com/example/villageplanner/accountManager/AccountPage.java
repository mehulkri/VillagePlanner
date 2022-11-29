package com.example.villageplanner.accountManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toolbar;

import com.example.villageplanner.HomeLogic.HomepageActivity;
import com.example.villageplanner.R;
import com.example.villageplanner.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

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
        if (mAuth.getCurrentUser() == null) {
            toolbar.setTitle("Welcome, Guest!");
        }
        else {
            toolbar.setTitle("Welcome, " + mAuth.getCurrentUser().getEmail() + "!");
        }
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
