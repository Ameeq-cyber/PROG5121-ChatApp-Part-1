/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.mycompany.chatapppart1.Login;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Student
 */
public class LoginTest {
    
    private final String validUsername = "tas_2";
    private final String invalidUsername = "tash!!!!!!!";
    private final String validPassword = "Tp&&jul@may5!";
    private final String invalidPassword = "password";
    private final String validCellPhone = "+27839686798";
    private final String invalidCellPhone = "08755664";

    @Test
    public void testUsernameCorrectlyFormatted() {
        Login login = new Login();
        assertTrue(login.checkUserName(validUsername));
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        Login login = new Login();
        assertFalse(login.checkUserName(invalidUsername));
    }

    @Test
    public void testPasswordMeetsComplexityRequirements() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity(validPassword));
    }

    @Test
    public void testPasswordDoesNotMeetComplexityRequirements() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity(invalidPassword));
    }
    
    @Test
    public void testCellPhoneNumberCorrectlyFormatted() {
        Login login = new Login();
        assertTrue(login.checkCellPhoneNumber(validCellPhone));
    }

    @Test
    public void testCellPhoneNumberIncorrectlyFormatted() {
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber(invalidCellPhone));
    }

    @Test
    public void testLoginSuccessful() {
        Login login = new Login("Natasha", "Prins");
        assertEquals("Registration successful.",
                login.registerUser(validUsername, validPassword, validCellPhone));
        assertTrue(login.loginUser(validUsername, validPassword));
    }

    @Test
    public void testLoginFailed() {
        Login login = new Login("Natasha", "Prins");
        login.registerUser(validUsername, validPassword, validCellPhone);
        assertFalse(login.loginUser(validUsername, invalidPassword));
    }

    @Test
    public void testUsernameCorrectlyFormattedAssertTrue() {
        Login login = new Login();
        assertTrue(login.checkUserName(validUsername));
    }
    
     @Test
    public void testLoginStatusSuccess() {
        Login login = new Login("Natasha", "Prins");
        login.registerUser(validUsername, validPassword, validCellPhone);
        assertEquals("Welcome Natasha Prins, it is great to see you again.",
                login.returnLoginStatus(true));
    }

    @Test
    public void testLoginStatusFailure() {
        Login login = new Login("Natasha", "Prins");
        assertEquals("Username or password incorrect, please try again.",
                login.returnLoginStatus(false));
    }
    
    @Test
    public void testRegisterUserUsernameCaptured() {
        Login login = new Login("Natasha", "Prins");
        assertEquals("Username successfully captured.", 
            login.registerUser(validUsername, validPassword, validCellPhone));
    }

    @Test
    public void testRegisterUserCellPhoneAdded() {
        Login login = new Login("Natasha", "Prins");
        assertEquals("Cell phone number successfully added.", 
            login.registerUser(validUsername, validPassword, validCellPhone));
    }

    @Test
    public void testRegisterUserPasswordCaptured() {
        Login login = new Login("Natasha", "Prins");
        assertEquals("Password successfully captured.", 
            login.registerUser(validUsername, validPassword, validCellPhone));
    }
    
    @Test
    public void testRegisterUserInvalidUsername() {
        Login login = new Login("Natasha", "Prins");
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than 5 characters in length.", 
            login.registerUser(invalidUsername, validPassword, validCellPhone));
    }

    @Test
    public void testRegisterUserInvalidPassword() {
        Login login = new Login("Natasha", "Prins");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number and a special character.", 
            login.registerUser(validUsername, invalidPassword, validCellPhone));
    }

    @Test
    public void testRegisterUserInvalidCellPhone() {
        Login login = new Login("Natasha", "Prins");
        assertEquals("Cell number is incorrectly formatted or does not contain an international code; please correct the code.", 
            login.registerUser(validUsername, validPassword, invalidCellPhone));
    }
    
}
