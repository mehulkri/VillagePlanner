package com.example.villageplanner.createAccount;

import static com.example.villageplanner.createAccount.AccountCreationValidator.validateEmail;
import static com.example.villageplanner.createAccount.AccountCreationValidator.validatePassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.villageplanner.R;
import com.example.villageplanner.ui.login.LoginActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;


public class CreateAccount extends AppCompatActivity {

    MaterialEditText name;
    MaterialEditText email;
    MaterialEditText password;
    MaterialEditText confirmPassword;
    Button submit;
    Button backToLogin;
    TextView errorMessage;
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

        // Next button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = getInfo(name);
                String emailAddr = getInfo(email);
                String passwordOne = getInfo(password);
                String passwordTwo = getInfo(confirmPassword);
                String error;
                if(fullName.isEmpty()) {
                    error = "Need to input full name." ;
                    setErrorText(error, name);
                }
                if(passwordOne.isEmpty()) {
                    error = "Need to input a password.";
                    setErrorText(error, password);
                }
                if(passwordTwo.isEmpty()) {
                    error = "Need to confirm the password";
                    setErrorText(error, confirmPassword);
                }
                if(tryValidate(fullName, emailAddr, passwordOne, passwordTwo)) {
                    // Try to add the user to Firebase database
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    Task<AuthResult> result = mAuth.createUserWithEmailAndPassword(emailAddr, passwordOne);
                    if(!result.isSuccessful()) {
                        error = "Account with email " + emailAddr + " already exists. Please login in or try a different email address";
                        errorMessage.setText(error);
                    }
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
        email.validateWith(new RegexpValidator("Invalid!", "^(.+)@(\\S+)$"));
        if(!validatePassword(passwordOne, passwordTwo)) {
            setErrorText("Not the same password", password);
            setErrorText("Not the same password", confirmPassword);
            isValid = false;
        }
        if(!validateEmail(emailAddr)) {
            isValid = false;
        }
        return isValid;
    }

    public static String getInfo(MaterialEditText text) {
        return text.getText().toString();
    }

    public void setErrorText(String errorMessage, MaterialEditText pass) {
        pass.setHelperText(errorMessage);
        pass.setHelperTextAlwaysShown(true);
    }


}