/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapppart1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Student
 */
public class MessageManagerTest {
    @BeforeEach
    public void setUp() {
        // Clear memory arrays and delete any leftover test JSON file before each test
        MessageManager.resetAllDataForTesting();
        File jsonFile = new File("storedMessages.json");
        if (jsonFile.exists()) {
            jsonFile.delete();
        }

        // Populate the 5 official PoE test messages[cite: 11]:
        // Message 1: Sent[cite: 11]
        MessageManager.addMessageRecord("1000000001", "10:0:DIDCAKE", "student_user",
                "+27834557896", "Did you get the cake?", "SENT");

        // Message 2: Stored (Longest message)[cite: 9, 11]
        MessageManager.addMessageRecord("1000000002", "10:1:WHERETIME", "student_user",
                "+27838884567", "Where are you? You are late! I have asked you to be on time.", "STORED");

        // Message 3: Disregarded[cite: 11]
        MessageManager.addMessageRecord("1000000003", "10:2:YOHOOOOGATE", "student_user",
                "+27834484567", "Yohoooo, I am at your gate.", "DISREGARDED");

        // Message 4: Sent (Controlled ID test)[cite: 11]
        MessageManager.addMessageRecord("0838884567", "08:3:ITTIME", "student_user",
                "0838884567", "It is dinner time!", "SENT");

        // Message 5: Stored[cite: 11]
        MessageManager.addMessageRecord("1000000005", "10:4:OKYOU", "student_user",
                "+27838884567", "Ok, I am leaving without you.", "STORED");
    }

    @Test
    public void testSentAndDisregardedCounts() {
        // Capture report output to verify sent (2), stored (2), and disregarded (1) counts[cite: 11]
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MessageManager.displayMessageReport();
        String report = outContent.toString();

        assertTrue(report.contains("Total Sent: 2"), "Should have 2 sent messages (Messages 1 & 4)[cite: 11]");
        assertTrue(report.contains("Total Stored: 2"), "Should have 2 stored messages (Messages 2 & 5)[cite: 11]");
        assertTrue(report.contains("Disregarded: 1"), "Should have 1 disregarded message (Message 3)[cite: 11]");
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("It is dinner time!"));
    }

    @Test
    public void testLongestStoredMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MessageManager.findLongestStoredMessage();
        String output = outContent.toString();

        // Message 2 must be identified as the longest stored message[cite: 9, 11]
        assertTrue(output.contains("Where are you? You are late! I have asked you to be on time."),
                "Message 2 should be returned as the longest stored message[cite: 9, 11]");
    }

    @Test
    public void testSearchByMessageID() {
        // Simulate user typing "0838884567" into Scanner
        System.setIn(new ByteArrayInputStream("0838884567\n".getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Note: Re-initialize scanner if your MessageManager caches System.in statically
        MessageManager.searchByMessageID();
        String output = outContent.toString();

        assertTrue(output.contains("It is dinner time!"), "ID 0838884567 should return Message 4[cite: 11]");
    }

    @Test
    public void testSearchByRecipientMultipleMatches() {
        // Simulate user typing "+27838884567" (matches both Message 2 and Message 5)[cite: 10, 11]
        System.setIn(new ByteArrayInputStream("+27838884567\n".getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MessageManager.searchByRecipient();
        String output = outContent.toString();

        assertTrue(output.contains("Where are you? You are late! I have asked you to be on time."),
                "Should find Message 2 for +27838884567[cite: 10]");
        assertTrue(output.contains("Ok, I am leaving without you."),
                "Should find Message 5 for +27838884567[cite: 10]");
    }

    @Test
    public void testDeleteByHashAndJsonPersistence() {
        // Simulate deleting Message 2 using its hash "10:1:WHERETIME"[cite: 10, 11]
        System.setIn(new ByteArrayInputStream("10:1:WHERETIME\n".getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MessageManager.deleteMessageByHash();
        String output = outContent.toString();

        assertTrue(output.contains("Successfully deleted message"), "Should confirm deletion of Message 2[cite: 11]");

        // Simulate app restart: clear arrays in memory and reload from storedMessages.json[cite: 12, 13]
        MessageManager.resetAllDataForTesting();
        MessageManager.loadStoredMessagesFromJson();

        ByteArrayOutputStream reloadOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(reloadOut));
        MessageManager.displayMessageReport();
        String reloadedReport = reloadOut.toString();

        // Message 2 must remain deleted, while Message 5 must still exist[cite: 10, 12]
        assertFalse(reloadedReport.contains("Where are you? You are late!"),
                "Deleted message must stay deleted after JSON reload[cite: 1, 12]");
        assertTrue(reloadedReport.contains("Ok, I am leaving without you."),
                "Remaining stored message (Message 5) must persist after JSON reload");
    }
public static void setScannerInput(String data) {
        Scanner Scanner = new Scanner(new java.io.ByteArrayInputStream(data.getBytes()));
}

    private void assertTrue(boolean contains, String remaining_stored_message_Message_5_must_p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertFalse(boolean contains, String deleted_message_must_stay_deleted_after_J) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertTrue(boolean contains) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
