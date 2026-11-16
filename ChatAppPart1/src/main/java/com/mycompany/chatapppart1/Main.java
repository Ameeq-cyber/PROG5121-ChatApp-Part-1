/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapppart1;


import java.util.Scanner;


/**
 *
 * @author Student
 */
/**
 * Main application class controlling user login, messaging loops, 
 * main menu options, and the Part 3 reporting sub-menu.
 */
public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Login login = new Login();
            
            // Registration Phase
            System.out.println("=== Registration ===");
            System.out.print("Enter username: ");
            String regUser = scanner.nextLine();
            System.out.print("Enter password: ");
            String regPass = scanner.nextLine();
            System.out.print("Enter first name: ");
            String regFirst = scanner.nextLine();
            System.out.print("Enter last name: ");
            String regLast = scanner.nextLine();
            
            System.out.println(login.registerUser(regUser, regPass, regFirst, regLast));
            
            // Login Loop - Keeps prompting until correct credentials are entered
            boolean loggedIn = false;
            while (!loggedIn) {
                System.out.println("\n=== Login ===");
                System.out.print("Enter username: ");
                String lUser = scanner.nextLine();
                System.out.print("Enter password: ");
                String lPass = scanner.nextLine();
                
                loggedIn = login.loginUser(lUser, lPass);
                System.out.println(login.returnLoginStatus(loggedIn));
            }
            
            // Main Application Loop
            boolean running = true;
            int successfulSendCount = 0;
            
            while (running) {
                System.out.println("\nWelcome to QuickChat.");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Stored Messages & Reports Menu (Part 3)");
                System.out.println("4) Quit");
                System.out.print("Choose an option (1-4): ");
                
                String choiceInput = scanner.nextLine().trim();
                int choice = -1;
                try {
                    choice = Integer.parseInt(choiceInput);
                } catch (NumberFormatException e) {
                    choice = -1;
                }
                
                switch (choice) {
                    case 1 -> {
                        System.out.print("How many messages would you like to enter? ");
                        int messageAllowance = scanner.nextInt();
                        scanner.nextLine(); // clear buffer
                        
                        for (int i = 0; i < messageAllowance; i++) {
                            System.out.println("\n--- Entering Message " + (i + 1) + " of " + messageAllowance + " ---");
                            Message msg = new Message(i + 1);
                            
                            // Recipient Validation Loop (Rejects invalid formats like +abc)
                            boolean validCell = false;
                            while (!validCell) {
                                System.out.print("Enter recipient cellphone number (e.g., +27...): ");
                                String recipient = scanner.nextLine();
                                String cellResult = msg.checkRecipientCell(recipient);
                                System.out.println(cellResult);
                                if (cellResult.contains("successfully captured")) {
                                    validCell = true;
                                }
                            }
                            
                            // Message Length Validation Loop (Stays in loop if > 250 characters)
                            boolean validText = false;
                            String messageText = "";
                            while (!validText) {
                                System.out.print("Enter your message (max 250 characters): ");
                                messageText = scanner.nextLine();
                                String textResult = msg.checkMessageLength(messageText);
                                System.out.println(textResult);
                                
                                if (!textResult.contains("exceeds")) {
                                    validText = true;
                                }
                            }
                            
                            // Generate Hash
                            String hash = msg.createMessageHash(messageText);
                            System.out.println("Generated Message Hash: " + hash);
                            
                            // Action Selection Loop (Ensures valid numeric choice 1-3)
                            int actionChoice = 0;
                            while (actionChoice < 1 || actionChoice > 3) {
                                System.out.println("\nSelect an action:");
                                System.out.println("1) Send Message");
                                System.out.println("2) Disregard Message");
                                System.out.println("3) Store Message");
                                System.out.print("Enter choice (1-3): ");
                                
                                String actionInput = scanner.nextLine().trim();
                                try {
                                    actionChoice = Integer.parseInt(actionInput);
                                } catch (NumberFormatException e) {
                                    actionChoice = 0;
                                }
                                if (actionChoice < 1 || actionChoice > 3) {
                                    System.out.println("Invalid selection. Please enter 1, 2, or 3.");
                                }
                            }
                            
                            String actionResult = msg.processChoice(actionChoice);
                            System.out.println(actionResult);
                            
                            if (actionChoice == 1 || actionChoice == 3) {
                                successfulSendCount++;
                            }
                        }
                        // Addresses Part 2 feedback: Displays total successfully sent messages
                        System.out.println("\nTotal successfully processed/sent messages: " + successfulSendCount);
                    }
                        
                    case 2 -> {
                        System.out.println("\n--- Sent Messages ---");
                        if (!Message.sentMessages.isEmpty()) {
                            for (var sm : Message.sentMessages) {
                                System.out.println("- " + sm);
                            }
                        } else {
                            System.out.println("No sent messages recorded yet.");
                        }
                    }



                        
                    case 3 -> {
                        // Part 3 Stored Messages and Reports Sub-Menu
                        boolean subRunning = true;
                        while (subRunning) {
                            System.out.println("\n--- STORED MESSAGES & REPORTS MENU ---");
                            System.out.println("a) Display sender and recipient of all stored messages");
                            System.out.println("b) Display the longest stored message");
                            System.out.println("c) Search for a message ID and display recipient/message");
                            System.out.println("d) Search for all messages for a particular recipient");
                            System.out.println("e) Delete a message using its message hash");
                            System.out.println("f) Display full message report");
                            System.out.println("g) Return to Main Menu");
                            System.out.print("Select an option (a-g): ");
                            
                            String subOpt = scanner.nextLine().trim().toLowerCase();
                            switch (subOpt) {
                                case "a" -> {
                                    System.out.println("\n[Sender & Recipient of Stored Messages]");
                                    if (Message.storedMessages.isEmpty()) {
                                        System.out.println("No stored messages found.");
                                    } else {
                                        for (int j = 0; j < Message.storedMessages.size(); j++) {
                                            System.out.println("Recipient: " + Message.recipients.get(j) + " | Message: " + Message.storedMessages.get(j));
                                        }
                                    }
                                }
                                case "b" -> {
                                    System.out.println("\n[Longest Stored Message]");
                                    if (Message.storedMessages.isEmpty()) {
                                        System.out.println("No stored messages found.");
                                    } else {
                                        String longest = Message.storedMessages.get(0);
                                        for (String m : Message.storedMessages) {
                                            if (m.length() > longest.length()) {
                                                longest = m;
                                            }
                                        }
                                        System.out.println("Longest Stored Message: " + longest);
                                    }
                                }
                                case "c" -> {
                                    System.out.print("Enter Message ID to search: ");
                                    String searchId = scanner.nextLine().trim();
                                    boolean foundId = false;
                                    for (int j = 0; j < Message.messageIDs.size(); j++) {
                                        if (Message.messageIDs.get(j).equals(searchId)) {
                                            System.out.println("Found! Recipient: " + Message.recipients.get(j) + " | Message: " + Message.sentMessages.get(j));
                                            foundId = true;
                                        }
                                    }
                                    if (!foundId) System.out.println("Message ID not found.");
                                }
                                case "d" -> {
                                    System.out.print("Enter recipient number to search (+27...): ");
                                    String searchRec = scanner.nextLine().trim();
                                    boolean foundRec = false;
                                    for (int j = 0; j < Message.recipients.size(); j++) {
                                        if (Message.recipients.get(j).equals(searchRec)) {
                                            System.out.println("Message: " + Message.storedMessages.get(j));
                                            foundRec = true;
                                        }
                                    }
                                    if (!foundRec) System.out.println("No messages found for this recipient.");
                                }
                                case "e" -> {
                                    System.out.print("Enter message hash to delete: ");
                                    String delHash = scanner.nextLine().trim();
                                    int indexToRemove = Message.messageHashes.indexOf(delHash);
                                    if (indexToRemove != -1) {
                                        String removedMsg = Message.sentMessages.get(indexToRemove);
                                        Message.messageHashes.remove(indexToRemove);
                                        Message.sentMessages.remove(indexToRemove);
                                        Message.messageIDs.remove(indexToRemove);
                                        Message.recipients.remove(indexToRemove);
                                        System.out.println("Message: \"" + removedMsg + "\" successfully deleted.");
                                    } else {
                                        System.out.println("Message hash not found.");
                                    }
                                }
                                case "f" -> {
                                    System.out.println("\n--- FULL MESSAGE REPORT ---");
                                    if (Message.messageHashes.isEmpty()) {
                                        System.out.println("No message records found.");
                                    } else {
                                        for (int j = 0; j < Message.messageHashes.size(); j++) {
                                            System.out.println("Hash: " + Message.messageHashes.get(j));
                                            System.out.println("Recipient: " + Message.recipients.get(j));
                                            System.out.println("Message: " + Message.sentMessages.get(j));
                                            System.out.println("-----------------------------------");
                                        }
                                    }
                                }
                                case "g" -> subRunning = false;
                                default -> System.out.println("Invalid menu option. Please enter a letter from a to g.");
                            }
                        }
                    }
                        
                    case 4 -> {
                        System.out.println("\nThank you for using QuickChat. Goodbye!");
                        running = false;
                    }
                        
                    default -> System.out.println("\nInvalid choice. Please select an option between 1 and 4.");
                }
            }
        }
    }
}

