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
}
