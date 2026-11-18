# PROG5121-ChatApp Part 3

## Module: PROG5121 Programming 1A

### Student Details
Ameeq Tasmia Prins ST10510171

### Project Overview
This project is the complete console-based QuickChat application developed for Programming 1A (Final Portfolio of Evidence). Building upon user registration and login authentication (Part 1) and message creation, validation, and JSON saving (Part 2), Part 3 introduces Java parallel arrays to organize sent, stored, and disregarded messages, reloads persisted messages from a JSON file on startup, and provides a Stored Messages menu for searching, deleting, and generating message reports.

### Features
* User registration and login authentication with regex validation (Part 1)
* Message creation with automated 10-digit IDs, uppercase message hashes, and Send/Store/Disregard options (Part 2)
* Dynamic population and synchronization of parallel arrays (`messageIDs`, `messageHashes`, `senders`, `recipients`, `messageTexts`, `statuses`
* JSON persistence using Google Gson to save and reload stored messages (`storedMessages.json`) across application restart
* Stored Messages submenu (Main Menu Option 4) featuring:
  * Displaying sender and recipient details for all stored messages
  * Finding and displaying the longest stored message
  * Searching for a specific message using its 10-digit Message ID
  * Searching for all sent and stored messages linked to a specific recipient number
  * Deleting a stored message using its Message Hash while keeping parallel arrays and JSON storage synchronized
  * Generating a complete summary report of sent and stored messages
* Comprehensive JUnit 5 unit testing and automated GitHub Actions CI workflow (`TestJava.yml`)

### Project Structure
The project contains the following main classes:
* `Main.java`
* `Login.java`
* `Message.java`
* `MessageManager.java`
* `LoginTest.java`
* `MessageTest.java`
* `MessageManagerTest.java` 

### How to Run the Application
1. Open the Maven project in NetBeans.
2. Build the project to resolve dependencies (including Google Gson and JUnit).
3. Run the `Main.java` class.
4. Register a new account and log in with valid credentials.
5. Follow the console prompts to send, store, or disregard messages, or select Option `4` to manage and report on stored messages.

### Testing Information
JUnit tests are included in `LoginTest.java`, `MessageTest.java`, and `MessageManagerTest.java` to verify authentication, message hashing, array population, longest-message detection, ID and recipient searches, hash-based deletion, JSON persistence, and report generation[cite: 2, 11]. Automated tests are configured to execute via Maven (`mvn test`) in `.github/workflows/TestJava.yml` on every push to GitHub.

### References
* Google, 2026. *Gson User Guide: Primitives and Object Serialization with Gson*. [Online]. Available at: https://github.com/google/gson [Accessed: 18 November 2026].
* Oracle, 2025. *Essential Java Classes - Lesson: Regular Expressions*. [Online]. Available at: https://docs.oracle.com/javase/tutorial/essential/regex/ [Accessed: 17 September 2026].
* The Independent Institute of Education (IIE), 2026. *Programming 1A (PROG5121) Portfolio of Evidence Student Assessment Guide*.


# PROG5121-ChatApp Part 2

## Module: PROG5121 Programming 1A

## Student Details
Ameeq Tasmia Prins  
ST10510171

## Project Overview
This project is the Part 2 extension of the console-based chat application developed for Programming 1A. Building upon Part 1, this version introduces advanced features including array/collection-based message management, user messaging functionality, detailed chat logs, and comprehensive JUnit unit testing for the new components.

## Features
- All features from Part 1 (User registration, login, and validation)
- Secure message sending and receiving functionality
- Storage and display of sent and received chat messages using arrays or collections
- Message display formatting with sender details and timestamps
- Extended JUnit unit testing for Part 2 classes and methods

## Project Structure
The project contains the following main classes:
- Main.java
- Login.java
- Message.java
- MessageTest.java
- LoginTest.java

## How to Run the Application
1. Open the project in NetBeans.
2. Build the project.
3. Run the Main.java class.
4. Follow the interactive menu instructions displayed in the console to log in and manage messages.

## Testing Information
JUnit tests are included in `MessageTest.java` and `LoginTest.java` to thoroughly verify the chat message handling, array bounds, validation logic, and overall application functionality.

## References
- Oracle, 2025. Essential Java Classes. [Online].  
Available at: https://docs.oracle.com/javase/tutorial/ [Accessed: October 2026].

## Video Presentation link
https://youtu.be/f9umYxvSxQk











# PROG5121-ChatApp Part 1
# Module: PROG5121 Programming 1A

## Student Details
Ameeq Tasmia Prins
ST10510171

## Project Overview

This project is a console-based chat application registration and login feature developed for Programming 1A. The application allows users to register using a username, password and South African cell phone number and then log in using their registered details.

## Features

- Username validation
- Password complexity validation
- South African cell phone number validation using a regular expression
- User registration
- User login and authentication
- Login success and error messages
- JUnit unit testing

## Project Structure

The project contains the following main classes:

- Main.java
- Login.java
- LoginTest.java

## How to Run the Application

1. Open the project in NetBeans.
2. Build the project.
3. Run the Main.java class.
4. Follow the instructions displayed in the console.

## Testing Information

JUnit tests are included in LoginTest.java to test username validation, password complexity, cell phone validation, registration and login functionality.

## References
- Oracle, 2025. Essential Java Classes - Lesson: Regular Expressions. [Online].
  Available at: https://docs.oracle.com/javase/tutorial/essential/regex/
  [Accessed: 17 September 2026].

## Video Presentation link
[https://youtu.be/GOaqUZsNpVA]
 main

