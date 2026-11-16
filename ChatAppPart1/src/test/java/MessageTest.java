/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.mycompany.chatapppart1.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Student
 */
public class MessageTest {
    
    @Test
    public void testMessageIDGeneration() {
        Message msg = new Message(1);
        assertNotNull(msg.getMessageID());
        assertEquals(10, msg.getMessageID().length());
        assertTrue(msg.checkMessageID());
    }

    @Test
    public void testValidRecipient() {
        Message msg = new Message(1);
        String result = msg.checkRecipientCell("+27718693002");
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testInvalidRecipient() {
        Message msg = new Message(1);
        String result = msg.checkRecipientCell("08575975889");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    @Test
    public void testMessageLengthWithinLimit() {
        Message msg = new Message(1);
        String text = "Hi Mike, can you join us for dinner tonight?";
        String result = msg.checkMessageLength(text);
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testMessageLengthExceeded() {
        Message msg = new Message(1);
        // Create a string of 257 characters
        String longText = "a".repeat(257);
        String result = msg.checkMessageLength(longText);
        assertEquals("Message exceeds 250 characters by 7; please reduce the size.", result);
    }

    @Test
    public void testCreateMessageHash() {
        // Using a controlled test ID starting with "00" as specified in the guide
        Message msg = new Message("0012345678", 0);
        String text = "Hi Mike, can you join us for dinner tonight?";
        String hash = msg.createMessageHash(text);
        assertEquals("00:0:HITONIGHT", hash);
    }

    @Test
    public void testSendChoiceFeedback() {
        Message msg = new Message(1);
        assertEquals("Message successfully sent.", msg.processChoice(1));
    }

    @Test
    public void testDisregardChoiceFeedback() {
        Message msg = new Message(1);
        assertEquals("Press 0 to delete the message.", msg.processChoice(2));
    }

    @Test
    public void testStoreChoiceFeedback() {
        Message msg = new Message(1);
        assertEquals("Message successfully stored.", msg.processChoice(3));
    }
    
}

