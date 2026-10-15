/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapppart1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Student
 */
public final class Message {
    
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String status; // "SENT", "DISREGARDED", or "STORED"

    // Constructor
    public Message(int messageNumber) {
        this.messageNumber = messageNumber;
        this.messageID = generateMessageID();
    }

    /**
     * Generates a random 10-digit message ID as a String to preserve leading zeros.
     * @return 
     */
    public String generateMessageID() {
        Random rand = new Random();
        long id = (long)(rand.nextDouble() * 9000000000L) + 1000000000L;
        return String.valueOf(id);
    }

    /**
     * Overloaded constructor for unit testing with a controlled ID.
     * @param controlledID
     * @param messageNumber
     */
    public Message(String controlledID, int messageNumber) {
        this.messageID = controlledID;
        this.messageNumber = messageNumber;
    }

    /**
     * Validates that the message ID length is correct (10 digits).
     * @return 
     */
    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() == 10;
    }

    /**
     * Validates the recipient cellphone number for an international code (+).
     * @param recipient
     * @return 
     */
    public String checkRecipientCell(String recipient) {
        if (recipient != null && recipient.startsWith("+")) {
            this.recipient = recipient;
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    /**
     * Validates message text length (maximum 250 characters).
     * @param text
     * @return 
     */
    public String checkMessageLength(String text) {
        if (text == null) {
            return "Message cannot be null.";
        }
        if (text.length() <= 250) {
            this.messageText = text;
            return "Message ready to send.";
        } else {
            int excess = text.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    /**
     * Builds and returns the uppercase message hash:
     * Format: [First 2 ID chars]:[Message Number]:[First & Last Words Uppercase]
     * @param text
     * @return 
     */
    public String createMessageHash(String text) {
        if (messageID == null || messageID.length() < 2 || text == null || text.trim().isEmpty()) {
            return "";
        }
        
        // Extract first two characters of ID
        String idPrefix = messageID.substring(0, 2);

        // Clean and split words
        String cleanedText = text.replaceAll("[^a-zA-Z ]", "").trim();
        String[] words = cleanedText.split("\\s+");

        String firstWord = "";
        String lastWord = "";

        if (words.length > 0) {
            firstWord = words[0];
            lastWord = words[words.length - 1];
        }

        // Combine into hash format and convert to uppercase
        this.messageHash = idPrefix + ":" + messageNumber + ":" + (firstWord + lastWord).toUpperCase();
        return this.messageHash;
    }

    /**
     * Processes user choice: Send, Disregard, or Store.
     * @param choiceOption
     * @return 
     */
    public String processChoice(int choiceOption) {
        switch (choiceOption) {
            case 1 -> {
                this.status = "SENT";
                return "Message successfully sent.";
            }
            case 2 -> {
                this.status = "DISREGARDED";
                return "Press 0 to delete the message.";
            }
            case 3 -> {
                this.status = "STORED";
                storeMessageToJson();
                return "Message successfully stored.";
            }
            default -> {
                return "Invalid choice.";
            }
        }
    }

    /**
     * Writes stored messages into a JSON file format.
     */
    public void storeMessageToJson() {
        String jsonEntry = "{\n" +
                "  \"messageId\": \"" + messageID + "\",\n" +
                "  \"messageHash\": \"" + messageHash + "\",\n" +
                "  \"recipient\": \"" + recipient + "\",\n" +
                "  \"message\": \"" + messageText + "\",\n" +
                "  \"status\": \"" + status + "\"\n" +
                "},\n";

        try (FileWriter file = new FileWriter("stored_messages.json", true)) {
            file.write(jsonEntry);
        } catch (IOException e) {
            System.out.println("Error writing to JSON file: " + e.getMessage());
        }
    }

    // Getters
    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getStatus() { return status; }
}
    

