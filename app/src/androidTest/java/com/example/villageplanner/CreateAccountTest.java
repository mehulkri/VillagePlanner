package com.example.villageplanner;

import static com.example.villageplanner.createAccount.AccountCreationValidator.validateEmail;
import static com.example.villageplanner.createAccount.AccountCreationValidator.validatePassword;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CreateAccountTest {

    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(validateEmail("mehulkri@usc.edu"));
        assertTrue(validateEmail("m@usc.edu"));
        assertTrue(validateEmail("admin@campusgroups.com"));
    }

    @Test
    public void emailValidator_BadEmail_ReturnsFalse() {
        assertTrue(!validateEmail("mehul"));
        assertTrue(!validateEmail("jsdkjfkljskljs;kjzklfdjslgkjlk"));
        assertTrue(!validateEmail("mkg@"));
    }

    @Test
    public void passwordMatchAreTrue() {
        String passwordOne = "Superstrong123!";
        String passwordTwo = "Hallo";
        String passwordThree = "J";
        assertTrue(validatePassword(passwordOne, passwordOne));
        assertTrue(validatePassword(passwordTwo, passwordTwo));
        assertTrue(validatePassword(passwordThree, passwordThree));
    }

    @Test
    public void passwordDontMatchAreFalse() {
        String passwordOne = "Superstrong123!";
        String passwordTwo = "Hallo";
        String passwordThree = "J";
        String passwordFour = "shjflkdsghkjfhdskghjsld456gd!2345gvfljksdhksjkl";
        assertTrue(!validatePassword(passwordOne, passwordTwo));
        assertTrue(!validatePassword(passwordTwo, passwordThree));
        assertTrue(!validatePassword(passwordThree, passwordFour));
    }
}
