/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapppart1;
import com.firework.gson.GsonBuilder;
import java.util.Scanner;
import com.google.gson.Gson;
import static com.mycompany.chatapppart1.Message.storedMessages;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Student
 */
public class MessageManager {
    // Define maximum capacity for arrays based on user allowance
    private static final int CAPACITY = 100;

    // Master Parallel Arrays to keep related message data synchronized at the same index
    private static String[] messageIDs = new String[CAPACITY];
    private static String[] messageHashes = new String[CAPACITY];
    private static String[] senders = new String[CAPACITY];
    private static String[] recipients = new String[CAPACITY];
    private static String[] messageTexts = new String[CAPACITY];
    private static String[] statuses = new String[CAPACITY];
    // Category-specific arrays and tracking counters
    // "SENT", "STORED", or "DISREGARDED"
    private static int messageCount = 0;
    private static int storedCount = 0;
    private static int sentCount = 0;
    private static int disregardedCount = 0;

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Displays the Stored Messages submenu and handles user choices.
     * @param loggedInUser
     */
    public static void showStoredMessagesMenu(String loggedInUser) {
        int choice;
        do {
            System.out.println("\n========================================");
            System.out.println("          STORED MESSAGES MENU          ");
            System.out.println("========================================");
            System.out.println("1) Display sender and recipient");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search using a Message ID");
            System.out.println("4) Search all messages for a recipient");
            System.out.println("5) Delete using a Message Hash");
            System.out.println("6) Display message report");
            System.out.println("7) Return to main menu");
            System.out.print("Enter your choice (1-7): ");

            // Input validation to prevent crashes on non-integer input
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number between 1 and 7: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> displaySenderAndRecipient();
                case 2 -> findLongestStoredMessage();
                case 3 -> searchByMessageID();
                case 4 -> searchByRecipient();
                case 5 -> deleteMessageByHash();
                case 6 -> displayMessageReport();
                case 7 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Please select between 1 and 7.");
            }
        } while (choice != 7);
    }

    /**
     * 1) Displays the sender and recipient for all currently STORED messages[cite: 8].
     */
    public static void displaySenderAndRecipient() {
        System.out.println("\n--- STORED MESSAGES SENDER & RECIPIENT ---");
        if (storedCount == 0) {
            System.out.println("No stored messages found.");
            return;
        }

        int displayIndex = 1;
        for (int i = 0; i < messageCount; i++) {
            if ("STORED".equals(statuses[i])) {
                System.out.println("STORED MESSAGE " + displayIndex++);
                System.out.println("Sender:    " + senders[i]);
                System.out.println("Recipient: " + recipients[i]);
                System.out.println("----------------------------------------");
            }
        }
    }

    /**
     * 2) Finds and displays the longest stored message text based on character length.
     */
    public static void findLongestStoredMessage() {
        System.out.println("\n--- LONGEST STORED MESSAGE ---");
        if (storedCount == 0) {
            System.out.println("No stored messages found.");
            return;
        }

        // Assume the first stored message is the longest initially
        String longest = "";
        for (int i = 0; i < messageCount; i++) {
            if ("STORED".equals(statuses[i])) {
                longest = messageTexts[i];
                break;
            }
        }

        // Compare lengths across all stored messages
        for (int i = 0; i < messageCount; i++) {
            if ("STORED".equals(statuses[i])) {
                if (messageTexts[i].length() > longest.length()) {
                    longest = messageTexts[i];
                }
            }
        }

        System.out.println("Longest message text: " + longest);
    }

    /**
     * 3) Searches for a specific message using its unique 10-digit Message ID[cite: 9].
     */
    public static void searchByMessageID() {
        System.out.println("\n--- SEARCH BY MESSAGE ID ---");
        System.out.print("Enter the 10-digit Message ID: ");
        String searchID = scanner.nextLine().trim();

        boolean found = false;
        for (int i = 0; i < messageCount; i++) {
            if (messageIDs[i] != null && messageIDs[i].equals(searchID)) {
                System.out.println("\nMessage Found!");
                System.out.println("Recipient: " + recipients[i]);
                System.out.println("Message:   " + messageTexts[i]);
                System.out.println("Status:    " + statuses[i]);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Message ID not found.");
        }
    }

    /**
     * 4) Searches all messages (Sent and Stored) for a specific recipient phone number[cite: 10].
     */
    public static void searchByRecipient() {
        System.out.println("\n--- SEARCH BY RECIPIENT ---");
        System.out.print("Enter recipient cell number (e.g., +27838884567): ");
        String targetRecipient = scanner.nextLine().trim();

        int matches = 0;
        System.out.println("\nMatching Messages:");
        for (int i = 0; i < messageCount; i++) {
            // Check if status is relevant (SENT or STORED) and recipient matches
            boolean isRelevant = "SENT".equals(statuses[i]) || "STORED".equals(statuses[i]);
            if (isRelevant && recipients[i] != null && recipients[i].equals(targetRecipient)) {
                System.out.println("- [" + statuses[i] + "] " + messageTexts[i]);
                matches++;
            }
        }

        if (matches == 0) {
            System.out.println("No messages found for this recipient.");
        }
    }

    /**
     * 5) Deletes a stored message using its generated Message Hash and shifts parallel arrays.
     */
    public static void deleteMessageByHash() {
        System.out.println("\n--- DELETE MESSAGE BY HASH ---");
        System.out.print("Enter the Message Hash to delete (e.g., 00:1:WHERETIME): ");
        String targetHash = scanner.nextLine().trim().toUpperCase();

        int deleteIndex = -1;
        for (int i = 0; i < messageCount; i++) {
            // Ensure we only delete records that are currently STORED
            if ("STORED".equals(statuses[i]) && messageHashes[i] != null && messageHashes[i].equals(targetHash)) {
                deleteIndex = i;
                break;
            }
        }

        if (deleteIndex == -1) {
            System.out.println("Stored message hash not found.");
            return;
        }

        String deletedText = messageTexts[deleteIndex];

        // Shift all parallel arrays down to maintain index alignment
        for (int j = deleteIndex; j < messageCount - 1; j++) {
            messageIDs[j] = messageIDs[j + 1];
            messageHashes[j] = messageHashes[j + 1];
            senders[j] = senders[j + 1];
            recipients[j] = recipients[j + 1];
            messageTexts[j] = messageTexts[j + 1];
            statuses[j] = statuses[j + 1];
        }

        // Clear out the final cell
        int last = messageCount - 1;
        messageIDs[last] = null;
        messageHashes[last] = null;
        senders[last] = null;
        recipients[last] = null;
        messageTexts[last] = null;
        statuses[last] = null;

        messageCount--;
        storedCount--; // Decrement stored counter

        System.out.println("Successfully deleted message: \"" + deletedText + "\"");
        // TODO: Call your JSON save method here to persist the updated array state to disk.
    }

    /**
     * 6) Displays a complete summary report of all stored and sent messages.
     */
    public static void displayMessageReport() {
        System.out.println("\n========================================");
        System.out.println("         QUICKCHAT MESSAGE REPORT       ");
        System.out.println("========================================");

        if (messageCount == 0) {
            System.out.println("No message records available.");
            return;
        }

        for (int i = 0; i < messageCount; i++) {
            System.out.println("STATUS:      " + statuses[i]);
            System.out.println("Message ID:  " + messageIDs[i]);
            System.out.println("Message Hash:" + messageHashes[i]);
            System.out.println("Sender:      " + senders[i]);
            System.out.println("Recipient:   " + recipients[i]);
            System.out.println("Message:     " + messageTexts[i]);
            System.out.println("----------------------------------------");
        }

        System.out.println("Total Sent: " + sentCount + " | Total Stored: " + storedCount + " | Disregarded: " + disregardedCount);
    }
    
    private static final String JSON_FILE_PATH = "storedMessages.json";

    /**
     * Helper class representing the structure of a JSON message object[cite: 6, 7].
     * Used by Google Gson to serialize and deserialize stored messages[cite: 7].
     */
    private static class StoredRecord {
        String messageID;
        String messageHash;
        String sender;
        String recipient;
        String message;
        String status;

        StoredRecord(String id, String hash, String sender, String recipient, String message, String status) {
            this.messageID = id;
            this.messageHash = hash;
            this.sender = sender;
            this.recipient = recipient;
            this.message = message;
            this.status = status;
        }
    }

    /**
     * Populates the parallel arrays and updates category counters whenever a message
     * is created in Part 2 (Send, Store, or Disregard) or loaded from JSON[cite: 4, 5].
     * @param id
     * @param hash
     * @param sender
     * @param recipient
     * @param text
     * @param status
     */
    public static void addMessageRecord(String id, String hash, String sender, String recipient, String text, String status) {
        if (messageCount >= CAPACITY) {
            System.out.println("Maximum message capacity reached.");
            return;
        }

        // Keep all parallel arrays synchronized at messageCount[cite: 4, 5]
        messageIDs[messageCount] = id;
        messageHashes[messageCount] = hash;
        senders[messageCount] = sender;
        recipients[messageCount] = recipient;
        messageTexts[messageCount] = text;
        statuses[messageCount] = status.toUpperCase();
        messageCount++;

        // Update category arrays and counters based on status[cite: 5]
        if ("SENT".equalsIgnoreCase(status)) {
            sentCount++;
        } else if ("STORED".equalsIgnoreCase(status)) {
            storedMessages.set(storedCount, text);
            storedCount++;
            saveStoredMessagesToJson(); // Persist immediately when stored[cite: 5]
        } else if ("DISREGARDED".equalsIgnoreCase(status)) {
            disregardedCount++;
        }
    }

    /**
     * Reads stored messages from storedMessages.json into the parallel arrays[cite: 7].
     * Reference / Attribution: Adapted from Google Gson User Guide (2026), 
     * "Primitives and Object Serialization with Gson", https://github.com/google/gson[cite: 7, 17].
     */
    public static void loadStoredMessagesFromJson() {
        File file = new File(JSON_FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return; // No saved file yet; leave arrays as-is[cite: 7]
        }

        // Source attribution: Google Gson documentation (FileReader to Object[] deserialization)[cite: 7, 17]
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(file)) {
            StoredRecord[] loadedRecords = gson.fromJson(reader, StoredRecord[].class);
            if (loadedRecords != null) {
                for (StoredRecord rec : loadedRecords) {
                    if (messageCount < CAPACITY) {
                        messageIDs[messageCount] = rec.messageID;
                        messageHashes[messageCount] = rec.messageHash;
                        senders[messageCount] = rec.sender;
                        recipients[messageCount] = rec.recipient;
                        messageTexts[messageCount] = rec.message;
                        statuses[messageCount] = "STORED";
                        messageCount++;

                        storedMessages.set(storedCount, rec.message);
                        storedCount++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading stored messages from JSON: " + e.getMessage());
        }
    }

    /**
     * Writes all currently STORED records from the parallel arrays back to storedMessages.json.
     * Call this inside deleteMessageByHash() right after shifting the arrays[cite: 10]!
     */
    public static void saveStoredMessagesToJson() {
        List<StoredRecord> toSave = new ArrayList<>();
        for (int i = 0; i < messageCount; i++) {
            if ("STORED".equals(statuses[i])) {
                toSave.add(new StoredRecord(
                    messageIDs[i],
                    messageHashes[i],
                    senders[i],
                    recipients[i],
                    messageTexts[i],
                    statuses[i]
                ));
            }
        }

        // Source attribution: Google Gson GsonBuilder pretty-printing serialization[cite: 7, 17]
        com.firework.gson.Gson gson;
        gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(JSON_FILE_PATH)) {
            gson.toJson(toSave, writer);
        } catch (IOException e) {
            System.out.println("Error saving stored messages to JSON: " + e.getMessage());
        }
    }

    /**
     * Helper method for JUnit tests to reset arrays cleanly between test cases.
     */
    public static void resetAllDataForTesting() {
        for (int i = 0; i < CAPACITY; i++) {
            messageIDs[i] = null;
            messageHashes[i] = null;
            senders[i] = null;
            recipients[i] = null;
            messageTexts[i] = null;
            statuses[i] = null;
            storedMessages.set(i, null);
        }
        messageCount = 0;
        storedCount = 0;
        sentCount = 0;
        disregardedCount = 0;
    }
    
}
