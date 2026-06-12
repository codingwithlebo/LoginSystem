/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.loginsystem;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author malebo
 */
public class LoginSystem {

    public static void main(String[] args) {
       

    
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();
        
        // ---------- REGISTRATION ----------
        System.out.println("=== Registration System ===\n");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter username (must contain _ and max 5 chars): ");
        String username = scanner.nextLine();
        System.out.print("Enter password (8+ chars, 1 capital, 1 number, 1 special): ");
        String password = scanner.nextLine();
        System.out.print("Enter cell number (format: +27XXXXXXXXX): ");
        String cellNumber = scanner.nextLine();
        
        String regResult = login.registerUser(username, password, cellNumber, firstName, lastName);
        System.out.println("\n" + regResult);
        
        if (!regResult.equals("User has been registered successfully.")) {
            System.out.println("Registration failed. Exiting.");
            scanner.close();
            return;
        }
        
        // ---------- LOGIN ----------
        System.out.println("\n=== Login ===\n");
        System.out.print("Enter username: ");
        String loginUser = scanner.nextLine();
        System.out.print("Enter password: ");
        String loginPass = scanner.nextLine();
        
        if (!login.loginUser(loginUser, loginPass)) {
            System.out.println(login.returnLoginStatus(loginUser, loginPass));
            scanner.close();
            return;
        }
        
        System.out.println(login.returnLoginStatus(loginUser, loginPass));
        
        // ---------- QUICKCHAT MESSAGING ----------
        System.out.println("\nWelcome to QuickChat.");
        
        System.out.print("How many messages do you wish to enter? ");
        int totalMessages = scanner.nextInt();
        scanner.nextLine();
        
        for (int i = 0; i < totalMessages; i++) {
            System.out.println("\n--- Message " + (i + 1) + "/" + totalMessages + " ---");
            
            // Recipient
            System.out.print("Recipient cell number (+27...): ");
            String recipient = scanner.nextLine();
            while (!message.checkRecipientCell(recipient, login)) {
                System.out.println("Cell phone number is incorrectly formatted.");
                System.out.print("Recipient cell number (+27...): ");
                recipient = scanner.nextLine();
            }
            
            // Message
            System.out.print("Your message (max 250 chars): ");
            String msgText = scanner.nextLine();
            while (!message.checkMessageLength(msgText)) {
                System.out.println(message.getLengthError(msgText));
                System.out.print("Your message (max 250 chars): ");
                msgText = scanner.nextLine();
            }
            
            // Create message
            message newMsg = new message(recipient, msgText);
            System.out.println("Message ID generated: " + newMsg.getMessageID());
            System.out.println("Message Hash generated: " + newMsg.createMessageHash());
            
            // Action menu
            System.out.println("\nChoose an option:");
            System.out.println("1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Store Message");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            String statusMsg = message.sentMessage(newMsg, choice);
            System.out.println(statusMsg);
            
            if (choice == 1 || choice == 3) {
                System.out.println("\n--- Message Details ---");
                System.out.println(newMsg);
            }
        }
        
        // Load previously stored messages from JSON
        message.loadStoredmessagesFromJSON();
        
        // ---------- PART 3: MESSAGE MANAGEMENT MENU ----------
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Message Management Menu ===");
            System.out.println("1. Display Longest Message");
            System.out.println("2. Search Message by ID");
            System.out.println("3. Search Messages by Recipient");
            System.out.println("4. Delete Message by Hash");
            System.out.println("5. Display Full Report");
            System.out.println("6. Show All Messages (Sent & Stored)");
            System.out.println("7. Exit");
            System.out.print("Your choice: ");
            
            int menuChoice = scanner.nextInt();
            scanner.nextLine();
            
            switch (menuChoice) {
                case 1:
                    System.out.println("\nLongest Message: " + message.getLongestmessage());
                    break;
                    
                case 2:
                    System.out.print("Enter Message ID (10 digits): ");
                    String searchId = scanner.nextLine();
                    message foundById = message.searchByMessageID(searchId);
                    if (foundById != null) {
                        System.out.println("Message found: " + foundById.getMessageText());
                    } else {
                        System.out.println("Message not found.");
                    }
                    break;
                    
                case 3:
                    System.out.print("Enter Recipient cell number: ");
                    String searchRecipient = scanner.nextLine();
                    List<message> foundByRecipient = message.searchByRecipient(searchRecipient);
                    if (foundByRecipient.isEmpty()) {
                        System.out.println("No messages found for this recipient.");
                    } else {
                        System.out.println("\nMessages found:");
                        for (message m : foundByRecipient) {
                            System.out.println("  - " + m.getmessageText());
                        }
                    }
                    break;
                    
                case 4:
                    System.out.print("Enter Message Hash to delete: ");
                    String hashToDelete = scanner.nextLine();
                    boolean deleted = message.deleteBymessageHash(hashToDelete);
                    if (deleted) {
                        System.out.println("Message successfully deleted.");
                    } else {
                        System.out.println("Message not found.");
                    }
                    break;
                    
                case 5:
                    message.displayReport();
                    break;
                    
                case 6:
                    message.printAllMessages();
                    System.out.println("\nTotal messages sent: " + message.returnTotalMessagesSent());
                    break;
                    
                case 7:
                    exit = true;
                    System.out.println("Goodbye!");
                    break;
                    
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        
        scanner.close();
    }
}
