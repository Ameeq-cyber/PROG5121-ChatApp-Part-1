/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapppart1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Student
 */
/**
 * Message class handles the creation, validation, hashing, 
 * array tracking, and JSON storage of individual messages.
 */
public class Message {
    private String messageID;
    private String messageText;
    private String recipient;
    private String messageHash;
    private String status;

    // Shared ArrayLists to store message information dynamically as required in Part 3
    public static ArrayList<String> sentMessages = new ArrayList<>();
    public static ArrayList<String> disregardedMessages = new ArrayList<>();
    public static ArrayList<String> storedMessages = new ArrayList<>();
    public static ArrayList<String> messageHashes = new ArrayList<>();
    public static ArrayList<String> messageIDs = new ArrayList<>();
    public static ArrayList<String> recipients = new ArrayList<>();
    private String messageNumber;

    // Constructor for testing with specific IDs
    public Message(String id, int messageNumber) {
        this.messageID = id;
    }

    // Constructor for standard runtime use
    public Message(int messageNumber) {
        this.messageID = generateRandomID();
    }

    /**
     * Generates a random 10-digit numeric ID string.
     */
    private String generateRandomID() {
        Random rand = new Random();
        long idLong = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(idLong);
    }

    /**
     * Validates recipient cell number format (Fixes Part 2 feedback on accepting +abc).
     * Must start with +27 and be followed by exactly 9 digits.
     * @param cell
     * @return 
     */
    public String checkRecipientCell(String cell) {
        if (cell != null && cell.startsWith("+27") && cell.length() == 12) {
            String subDigits = cell.substring(3);
            if (subDigits.matches("\\d+")) { // Ensures all remaining characters are numbers
                this.recipient = cell;
                return "Cell phone number successfully captured.";
            }
        }
        return "Cell phone number is incorrectly formatted or missing country code (+27). Please correct it.";
    }

    /**
     * Validates that the message does not exceed 250 characters.
     * @param text
     * @return 
     */
    public String checkMessageLength(String text) {
        if (text != null && text.length() <= 250) {
            this.messageText = text;
            return "Message ready to send.";
        } else {
            int excess = (text != null ? text.length() : 0) - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    /**
     * Creates a unique message hash using ID prefix, message number, and first/last words uppercase.
     * @param text
     * @param messageNumber
     * @return 
     */
    public String createMessageHash(String text) {
        String subText = "";
        if (text != null && !text.trim().isEmpty()) {
            String[] words = text.trim().split("\\s+");
            String firstWord = words[0];
            String lastWord = words[words.length - 1];
            subText = firstWord + " " + lastWord;
        }
        String idPrefix = (messageID != null && messageID.length() >= 2) ? messageID.substring(0, 2) : "00";
        this.messageHash = idPrefix + ":" + messageNumber + ":" + subText.toUpperCase();
        return this.messageHash;
    }

    /**
     * Processes user action choice (1: Send, 2: Disregard, 3: Store) and updates arrays.
     * @param choice
     * @return 
     */
    public String processChoice(int choice) {
        if (this.messageHash == null) {
            createMessageHash(this.messageText);
        }

        switch (choice) {

            case 1 -> {
                this.status = "Sent";
                sentMessages.add(this.messageText);
                messageHashes.add(this.messageHash);
                messageIDs.add(this.messageID);
                recipients.add(this.recipient);
                return "Message successfully sent.";
            }
            case 2 -> {
                this.status = "Disregarded";
                disregardedMessages.add(this.messageText);
                return "Message successfully disregarded.";
            }
            case 3 -> {
                this.status = "Stored";
                storedMessages.add(this.messageText);
                messageHashes.add(this.messageHash);
                messageIDs.add(this.messageID);
                recipients.add(this.recipient);
                storeMessageToJson();
                return "Message successfully stored.";
            }
            default -> {
                return "Invalid action choice.";
            }
        }
    }

    /**
     * Writes message details to a JSON file format.
     */
    public void storeMessageToJson() {
        try (FileWriter file = new FileWriter("stored_messages.json", true)) {
            String jsonEntry = "{\n" +
                    "  \"messageId\": \"" + messageID + "\",\n" +
                    "  \"messageHash\": \"" + messageHash + "\",\n" +
                    "  \"recipient\": \"" + recipient + "\",\n" +
                    "  \"message\": \"" + messageText + "\",\n" +
                    "  \"status\": \"" + status + "\"\n" +
                    "},\n";
            file.write(jsonEntry);
        } catch (IOException e) {
            System.out.println("Error writing to JSON file: " + e.getMessage());
        }
    }

    // Accessor Getter Methods
    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getStatus() { return status; }
    
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }
}


    
  