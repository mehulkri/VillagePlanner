package com.example.villageplanner.createAccount;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.villageplanner.createAccount.AccountCreationValidator.getPassValidator;
import static com.example.villageplanner.createAccount.AccountCreationValidator.validateEmail;
import static com.example.villageplanner.createAccount.AccountCreationValidator.validatePassword;
import static com.example.villageplanner.createAccount.AccountCreationValidator.validatePasswordStrength;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.villageplanner.HomeLogic.HomepageActivity;
import com.example.villageplanner.ImagePicker;
import com.example.villageplanner.R;
import com.example.villageplanner.ReminderLogic.ReminderPage;
import com.example.villageplanner.ui.login.LoginActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class CreateAccount extends AppCompatActivity {

    MaterialEditText name;
    MaterialEditText email;
    MaterialEditText password;
    MaterialEditText confirmPassword;
    Button submit;
    Button backToLogin;
    TextView errorMessage;
    CoordinatorLayout bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Initialize components
        name = (MaterialEditText) findViewById(R.id.fullName);
        email = (MaterialEditText) findViewById(R.id.emailField);
        password = (MaterialEditText) findViewById(R.id.passOne);
        confirmPassword = (MaterialEditText) findViewById(R.id.passTwo);
        submit = (Button) findViewById(R.id.nextButton);
        backToLogin = (Button) findViewById(R.id.backToLogin);
        errorMessage = (TextView) findViewById(R.id.createAccountError);
        bottom = (CoordinatorLayout) findViewById(R.id.snackbar_layout);

        // Next button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String error;
                boolean canProceed = true;
                if(determineIfUserTypedInValidFields()) {
                    // Try to add the user to Firebase database
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    Task<AuthResult> result = mAuth.createUserWithEmailAndPassword(getInfo(email), getInfo(password));
                    if(!result.isSuccessful()) {
                        if(result.getException() != null) {
                            error = result.getException().toString();
                            errorMessage.setText(error);
                            setError(error);
                            canProceed = false;
                        } else {
                            canProceed = true;
                        }

                    }
                } else {
                    canProceed = false;
                }
                // Move to next screen
                if(canProceed) {
                    Intent next = new Intent(CreateAccount.this, ImagePicker.class);
                    next.putExtra("Email", getInfo(email));
                    next.putExtra("Name", getInfo(name));
                    next.putExtra("calledFrom", "createAccount");
                    startActivity(next);
                }
            }
        });

        // Login Button
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(CreateAccount.this, LoginActivity.class);
                startActivity(login);
            }
        });
    }

    private boolean tryValidate(String fullName, String emailAddr, String passwordOne, String passwordTwo) {
        // Get information
        boolean isValid = true;
        password.validateWith(getPassValidator());
        confirmPassword.validateWith(getPassValidator());
        // Validate passwords match
        if(!validatePassword(passwordOne, passwordTwo)) {
            setErrorText("Not the same password", password);
            setErrorText("Not the same password", confirmPassword);
            isValid = false;
        }
        // Validate email is valid
        if(!validateEmail(emailAddr)) {
            setErrorText("Invalid email", email);
            isValid = false;
        }
        // Validate password is strong
        if(!validatePasswordStrength(passwordOne)) {
            isValid = false;
            String passwordRules = "Password must have: One numeric character, one lowercase character, " +
                    "one uppercase character, one special character, and should be between 8 to 40 characters in length";
            errorMessage.setText(passwordRules);
            setError(passwordRules);
        }

        return isValid;
    }

    private static String getInfo(MaterialEditText text) {
        return text.getText().toString();
    }

    public void setErrorText(String errorMessage, MaterialEditText pass) {
        pass.setHelperText(errorMessage);
        pass.setHelperTextAlwaysShown(true);
    }

    private boolean checkFieldEmpty(String errorMessage, MaterialEditText input) {
        if(getInfo(input).isEmpty()) {
            setErrorText(errorMessage, input);
             return false;
        } else {
            return true;
        }
    }

    private boolean determineIfUserTypedInValidFields() {
        String fullName = getInfo(name);
        String emailAddr = getInfo(email);
        String passwordOne = getInfo(password);
        String passwordTwo = getInfo(confirmPassword);
        boolean canProceed = checkFieldEmpty("Need to input full name.", name) &
                checkFieldEmpty("Need to input a password.", password) &
                checkFieldEmpty("Need to confirm the password", confirmPassword) &
                checkFieldEmpty("Need to input an email", email);
        if(canProceed) {
            return tryValidate(fullName, emailAddr, passwordOne, passwordTwo) ;
        } else {
            return canProceed;
        }
    }

    private void setError(String errorMessage) {
        Snackbar snack = Snackbar.make(bottom, errorMessage, LENGTH_LONG);
        snack.show();
    }

}