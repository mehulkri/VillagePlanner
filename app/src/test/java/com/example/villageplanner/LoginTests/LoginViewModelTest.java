package com.example.villageplanner.LoginTests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import android.util.Patterns;
import com.example.villageplanner.data.LoginDataSource;
import com.example.villageplanner.data.LoginRepository;
import com.example.villageplanner.ui.login.LoginViewModel;

import java.lang.reflect.*;

public class LoginViewModelTest {

    @Test
    public void validUserName() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InstantiationException {
        String userNameOne = "joshlee@usc.edu";
        String userNameTwo = "asfnasf@gmail.com";
        String userNameThree = "whousesroadrunner@roadrunner.com";
        String userNameFour = "asdf@yahoo.com";

        Class<?> loginClass = Class.forName("com.example.villageplanner.ui.login.LoginViewModel");
        Class parameterType[] = new Class[] {LoginRepository.class};
        LoginViewModel instance = (LoginViewModel) loginClass.getConstructor(parameterType).newInstance(LoginRepository.getInstance(new LoginDataSource()));

        Method userNameCheck = loginClass.getDeclaredMethod("isUserNameValid", String.class);
        userNameCheck.setAccessible(true);
        boolean checkOne = (boolean) userNameCheck.invoke(instance, userNameOne);
        boolean checkTwo = (boolean) userNameCheck.invoke(instance, userNameTwo);
        boolean checkThree = (boolean) userNameCheck.invoke(instance, userNameThree);
        boolean checkFour = (boolean) userNameCheck.invoke(instance, userNameFour);

        assertTrue(checkOne);
        assertTrue(checkTwo);
        assertTrue(checkThree);
        assertTrue(checkFour);
    }

    @Test
    public void invalidUserName() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InstantiationException {
        String userNameOne = "    ";
        String userNameTwo = "asfnasf@gmailcom";
        String userNameThree = "whousesroadrunner@roadrunner";
        String userNameFour = "asdf@.";

        Class<?> loginClass = Class.forName("com.example.villageplanner.ui.login.LoginViewModel");
        Class parameterType[] = new Class[] {LoginRepository.class};
        LoginViewModel instance = (LoginViewModel) loginClass.getConstructor(parameterType).newInstance(LoginRepository.getInstance(new LoginDataSource()));

        Method userNameCheck = loginClass.getDeclaredMethod("isUserNameValid", String.class);
        userNameCheck.setAccessible(true);
        boolean checkOne = (boolean) userNameCheck.invoke(instance, userNameOne);
        boolean checkTwo = (boolean) userNameCheck.invoke(instance, userNameTwo);
        boolean checkThree = (boolean) userNameCheck.invoke(instance, userNameThree);
        boolean checkFour = (boolean) userNameCheck.invoke(instance, userNameFour);

        assertFalse(checkOne);
        assertFalse(checkTwo);
        assertFalse(checkThree);
        assertFalse(checkFour);
    }

    @Test
    public void validPassword() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InstantiationException {
        String passwordOne = "wjhfqj1111!!!!";
        String passwordTwo = "realpassword@#$#%!";
        String passwordThree = "!@#$%^&*()AWERQWEGvasvvas__';';";
        String passwordFour = "abcdje";

        Class<?> loginClass = Class.forName("com.example.villageplanner.ui.login.LoginViewModel");
        Class parameterType[] = new Class[] {LoginRepository.class};
        LoginViewModel instance = (LoginViewModel) loginClass.getConstructor(parameterType).newInstance(LoginRepository.getInstance(new LoginDataSource()));

        Method passwordCheck = loginClass.getDeclaredMethod("isPasswordValid", String.class);
        passwordCheck.setAccessible(true);
        boolean checkOne = (boolean) passwordCheck.invoke(instance, passwordOne);
        boolean checkTwo = (boolean) passwordCheck.invoke(instance, passwordTwo);
        boolean checkThree = (boolean) passwordCheck.invoke(instance, passwordThree);
        boolean checkFour = (boolean) passwordCheck.invoke(instance, passwordFour);

        assertTrue(checkOne);
        assertTrue(checkTwo);
        assertTrue(checkThree);
        assertTrue(checkFour);
    }

    @Test
    public void invalidPassword() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InstantiationException {
        String passwordOne = "Aa1!";
        String passwordTwo = "1";
        String passwordThree = "12";
        String passwordFour = "ab#";

        Class<?> loginClass = Class.forName("com.example.villageplanner.ui.login.LoginViewModel");
        Class parameterType[] = new Class[] {LoginRepository.class};
        LoginViewModel instance = (LoginViewModel) loginClass.getConstructor(parameterType).newInstance(LoginRepository.getInstance(new LoginDataSource()));

        Method passwordCheck = loginClass.getDeclaredMethod("isPasswordValid", String.class);
        passwordCheck.setAccessible(true);
        boolean checkOne = (boolean) passwordCheck.invoke(instance, passwordOne);
        boolean checkTwo = (boolean) passwordCheck.invoke(instance, passwordTwo);
        boolean checkThree = (boolean) passwordCheck.invoke(instance, passwordThree);
        boolean checkFour = (boolean) passwordCheck.invoke(instance, passwordFour);

        assertFalse(checkOne);
        assertFalse(checkTwo);
        assertFalse(checkThree);
        assertFalse(checkFour);
    }


}
