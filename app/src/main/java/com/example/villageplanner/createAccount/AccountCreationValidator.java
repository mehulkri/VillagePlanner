package com.example.villageplanner.createAccount;

import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import java.util.regex.Pattern;

public class AccountCreationValidator {

    public static boolean validatePassword(String passOne, String passTwo) {
        return passOne.equals(passTwo);
    }

    public static boolean validateEmail(String email) {
        RegexpValidator validate = new RegexpValidator("Invalid!", "^(.+)@(\\S+)$");
        return validate.isValid(email, false);
    }

    public static boolean validatePasswordStrength(String password, RegexpValidator validate) {
        return validate.isValid(password, false);
    }
}
