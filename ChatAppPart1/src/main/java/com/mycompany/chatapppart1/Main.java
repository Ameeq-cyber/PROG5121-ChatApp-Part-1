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
public class Main {
    
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== Registration and Login ===");
            System.out.print("Enter your first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter your last name: ");
            String lastName = scanner.nextLine();
            
            Login userLogin = new Login(firstName, lastName);
            
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter South African cell phone number (e.g. +27838968976): ");
            String cellPhone = scanner.nextLine();
            
            String registrationMessage = userLogin.registerUser(username, password, cellPhone);
            System.out.println(registrationMessage);
            
            if (registrationMessage.equals("Registration successful.")) {
                System.out.println("\n=== Login ===");
                System.out.print("Enter username: ");
                String loginUsername = scanner.nextLine();
                System.out.print("Enter password: ");
                String loginPassword = scanner.nextLine();
                
                boolean loggedIn = userLogin.loginUser(loginUsername, loginPassword);
                System.out.println(userLogin.returnLoginStatus(loggedIn));
                
                if (loggedIn) {
                    boolean running = true;
                    
                    while (running) {
                        System.out.println("\nWelcome to QuickChat.");
                        System.out.println("1) Send Messages");
                        System.out.println("2) Show recently sent messages");
                        System.out.println("3) Quit");
                        System.out.print("Choose an option (1-3): ");
                        
                        int choice = -1;
                        if (scanner.hasNextInt()) {
                            choice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                        } else {
                            scanner.nextLine();
                            System.out.println("Invalid input. Please enter a number between 1 and 3.");
                            continue;
                        }

                        switch (choice) {
                            case 1 -> {
                                System.out.print("How many messages would you like to enter? ");
                                int messageAllowance = scanner.nextInt();
                                scanner.nextLine();

                                for (int i = 0; i < messageAllowance; i++) {
                                    System.out.println("\n--- Entering Message " + (i + 1) + " of " + messageAllowance + " ---");
                                    Message msg = new Message(i);

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

                                    boolean validText = false;
                                    String messageText = "";
                                    while (!validText) {
                                        System.out.print("Enter your message (max 250 characters): ");
                                        messageText = scanner.nextLine();
                                        String textResult = msg.checkMessageLength(messageText);
                                        System.out.println(textResult);
                                        if (textResult.contains("ready to send")) {
                                            validText = true;
                                        }
                                    }
                                    

                                    String hash = msg.createMessageHash(msg.getMessageText());
                                    System.out.println("Generated Message Hash: " + hash);

                                    System.out.println("\nSelect an action:");
                                    System.out.println("1) Send Message");
                                    System.out.println("2) Disregard Message");
                                    System.out.println("3) Store Message");
                                    System.out.print("Enter choice (1-3): ");
                                    String actionInput = scanner.nextLine().trim();
                                    int actionChoice = 1;
                                    try {
                                        actionChoice = Integer.parseInt(actionInput);
                                    } catch (NumberFormatException e) {
                                        actionChoice = 1; // Default safely if anything goes wrong
                                    }
                                    String actionResult = msg.processChoice(actionChoice);
                                    System.out.println(actionResult);

                                    if (actionChoice == 1) {
                                        System.out.println("\n========================================");
                                        System.out.println("MESSAGE DETAILS:");
                                        System.out.println("Message ID: " + msg.getMessageID());
                                        System.out.println("Message Hash: " + msg.getMessageHash());
                                        System.out.println("Recipient: " + msg.getRecipient());
                                        System.out.println("Message: " + msg.getMessageText());
                                        System.out.println("========================================");
                                    }
                                }
                            }

                            case 2 -> System.out.println("\nComing Soon.");

                            case 3 -> {
                                System.out.println("\nThank you for using QuickChat. Goodbye!");
                                running = false;
                            }

                            default -> System.out.println("\nInvalid choice. Please select 1, 2, or 3.");
                        }
                    }
                }
                
            }
        }
    }
}
        