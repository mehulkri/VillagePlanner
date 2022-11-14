package com.example.villageplanner.CreateAccountTests;

import static com.example.villageplanner.createAccount.AccountCreationValidator.validateEmail;
import static com.example.villageplanner.createAccount.AccountCreationValidator.validatePassword;
import static com.example.villageplanner.createAccount.AccountCreationValidator.validatePasswordStrength;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;

public class AccountCreationValidatorTests {

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

    @Test
    public void testPasswordStrengthBad() {
        ArrayList<String> passwords = new ArrayList<String>();
        // Only letters
        passwords.add("ksjlsjkjfljdksljl");
        // Only letters and numbers
        passwords.add("human1234");
        // Only letters, numbers, and special characters
        passwords.add("lemon3456!!");
        // Short password but correct
        passwords.add("Ab1$2");
        // Only Capital letters and special characters
        passwords.add("HELLO418902");
        // Loop through the tests
        passwords.forEach(p -> assertFalse(validatePasswordStrength(p)));
    }

    @Test
    public void testPasswordStrengthGood() {
        assertTrue(validatePasswordStrength("SupperString2948098490^835"));
        assertTrue(validatePasswordStrength("Normal#$532"));
        assertTrue(validatePasswordStrength("This4Is%The*Best!@Pass&Word^IN@dU!World"));
    }
}
