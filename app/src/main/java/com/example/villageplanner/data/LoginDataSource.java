package com.example.villageplanner.data;

import androidx.annotation.NonNull;

import com.example.villageplanner.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private FirebaseUser user;

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            FirebaseAuth mAuth;

            mAuth = FirebaseAuth.getInstance();
            Task<AuthResult> authTask = mAuth.signInWithEmailAndPassword(username, password);
            if(authTask.isSuccessful()) {
                LoggedInUser fakeUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                "Mehul Krishna");
                return new Result.Success<>(fakeUser);
            } else {
                return new Result.Error(new IOException("Firebase Login Error"));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
        FirebaseAuth.getInstance().signOut();
    }
}