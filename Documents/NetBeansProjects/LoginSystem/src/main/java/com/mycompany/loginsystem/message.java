/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 *
 * @author malebo
 */


public class message {
 


    private static int messageCounter = 0;
    private int messageNum;
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String status; // "sent", "discarded", "stored"
    
    // Arrays for Part 3
    private static List<message> sentMessages = new ArrayList<>();
    private static List<message> disregardedMessages = new ArrayList<>();
    private static List<message> storedMessages = new ArrayList<>();
    
    // Constructor
    public message(String recipient, String messageText) {
        this.messageNum = ++messageCounter;
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
        this.status = "created";
    }
    
    // Getters for Part 3
    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getStatus() { return status; }
    public int getMessageNum() { return messageNum; }
    
    private String generateMessageID() {
        Random rand = new Random();
        long id = 1_000_000_000L + (long)(rand.nextDouble() * 9_000_000_000L);
        return String.valueOf(id).substring(0, 10);
    }
    
    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String msgNum = String.valueOf(messageNum);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        String hash = firstTwo + ":" + msgNum + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }
    
    // Validation methods
    public static boolean checkMessageLength(String msg) {
        return msg != null && msg.length() <= 250;
    }
    
    public static String getLengthError(String msg) {
        int excess = msg.length() - 250;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }
    
    public static boolean checkRecipientCell(String cell, Login login) {
        return login.checkCellPhoneNumber(cell);
    }
    
    // Send/Store/Disregard with array population
    public static String sentMessage(message msg, int choice) {
        switch(choice) {
            case 1: // Send
                msg.status = "sent";
                sentMessages.add(msg);
                return "Message successfully sent.";
            case 2: // Disregard
                msg.status = "discarded";
                disregardedMessages.add(msg);
                return "Press 0 to delete the message.";
            case 3: // Store
                msg.status = "stored";
                storedMessages.add(msg);
                storeMessageToJSON(msg);
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }
    
    // JSON storage
    private static void storeMessageToJSON(message msg) {
        try (FileWriter fw = new FileWriter("messages.json", true)) {
            String json = String.format(
                "{\"messageID\":\"%s\",\"messageNum\":%d,\"recipient\":\"%s\",\"messageText\":\"%s\",\"hash\":\"%s\",\"status\":\"%s\"}\n",
                msg.messageID, msg.messageNum, msg.recipient, msg.messageText.replace("\"", "\\\""), msg.messageHash, msg.status
            );
            fw.write(json);
        } catch (IOException e) {
            System.err.println("Error storing message: " + e.getMessage());
        }
    }
    
    // Load stored messages from JSON file (for Part 3)
    public static void loadStoredMessagesFromJSON() {
        storedMessages.clear();
        try {
            List<String> lines = Files.readAllLines(Paths.get("messages.json"));
            for (String line : lines) {
                // Simple parsing (assuming JSON format from storeMessageToJSON)
                if (line.contains("\"status\":\"stored\"")) {
                    // Extract fields
                    String id = extractValue(line, "messageID");
                    String recipient = extractValue(line, "recipient");
                    String text = extractValue(line, "messageText");
                    String hash = extractValue(line, "hash");
                    String numStr = extractValue(line, "messageNum");
                    
                    if (id != null && recipient != null && text != null) {
                        message msg = new message(recipient, text);
                        msg.messageID = id;
                        msg.messageHash = hash;
                        msg.messageNum = Integer.parseInt(numStr);
                        msg.status = "stored";
                        storedMessages.add(msg);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("No existing messages.json file found.");
        }
    }
    
    private static String extractValue(String json, String key) {
        String search = "\"" + key + "\":\"";
        int start = json.indexOf(search);
        if (start == -1) {
            // Try for numeric values (no quotes)
            search = "\"" + key + "\":";
            start = json.indexOf(search);
            if (start != -1) {
                start += search.length();
                int end = json.indexOf(",", start);
                if (end == -1) end = json.indexOf("}", start);
                if (end != -1) return json.substring(start, end).trim();
            }
            return null;
        }
        start += search.length();
        int end = json.indexOf("\"", start);
        if (end != -1) return json.substring(start, end);
        return null;
    }
    
    // ========== PART 3 METHODS ==========
    
    // Display longest message from sent messages
    public static String getLongestMessage() {
        if (sentMessages.isEmpty()) return "No sent messages found.";
        message longest = sentMessages.get(0);
        for (message msg : sentMessages) {
            if (msg.getMessageText().length() > longest.getMessageText().length()) {
                longest = msg;
            }
        }
        return longest.getMessageText();
    }
    
    // Search message by ID
    public static message searchByMessageID(String messageID) {
        for (message msg : sentMessages) {
            if (msg.getMessageID().equals(messageID)) {
                return msg;
            }
        }
        for (message msg : storedMessages) {
            if (msg.getMessageID().equals(messageID)) {
                return msg;
            }
        }
        return null;
    }
    
    // Search all messages by recipient
    public static List<message> searchByRecipient(String recipient) {
        List<message> result = new ArrayList<>();
        for (message msg : sentMessages) {
            if (msg.getRecipient().equals(recipient)) {
                result.add(msg);
            }
        }
        for (message msg : storedMessages) {
            if (msg.getRecipient().equals(recipient)) {
                result.add(msg);
            }
        }
        return result;
    }
    
    // Delete message by hash
    public static boolean deleteByMessageHash(String hash) {
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).getMessageHash().equals(hash)) {
                sentMessages.remove(i);
                return true;
            }
        }
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).getMessageHash().equals(hash)) {
                storedMessages.remove(i);
                return true;
            }
        }
        return false;
    }
    
    // Display report of all sent messages
    public static void displayReport() {
        if (sentMessages.isEmpty()) {
            System.out.println("No sent messages to display.");
            return;
        }
        System.out.println("\n=== MESSAGES REPORT ===");
        System.out.println("--------------------------------------------------");
        for (message msg : sentMessages) {
            System.out.println("Message Hash: " + msg.getMessageHash());
            System.out.println("Recipient:    " + msg.getRecipient());
            System.out.println("Message:      " + msg.getMessageText());
            System.out.println("--------------------------------------------------");
        }
    }
    
    // Getters for arrays (for testing)
    public static List<message> getSentMessages() { return sentMessages; }
    public static List<message> getDisregardedMessages() { return disregardedMessages; }
    public static List<message> getStoredMessages() { return storedMessages; }
    
    // Reset for testing
    public static void resetData() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageCounter = 0;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %s | Hash: %s | To: %s | Msg: %s",
                messageID, messageHash, recipient, messageText);
    }
    
    public static int returnTotalMessagesSent() {
        return sentMessages.size();
    }
    
    public static void printAllMessages() {
        System.out.println("\n--- Sent Messages ---");
        for (message m : sentMessages) {
            System.out.println(m);
        }
        System.out.println("\n--- Stored Messages ---");
        for (message m : storedMessages) {
            System.out.println(m);
        }
    }
}