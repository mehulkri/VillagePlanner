package com.example.villageplanner.createAccount;

import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import java.util.regex.Pattern;

import kotlin.text.Regex;

public class AccountCreationValidator {

    public static boolean validatePassword(String passOne, String passTwo) {
        return passOne.equals(passTwo);
    }

    public static boolean validateEmail(String email) {
        RegexpValidator validate = new RegexpValidator("Invalid!", "^(.+)@(\\S+)$");
        return validate.isValid(email, false);
    }

    public static boolean validatePasswordStrength(String password) {
        return getPassValidator().isValid(password, false);
    }

    public static RegexpValidator getPassValidator() {
        String passwordRegex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=!])"
                + "(?=\\S+$).{8,40}$";
        RegexpValidator passwordValidator = new RegexpValidator("Weak password",passwordRegex);
        return passwordValidator;
    }
}
