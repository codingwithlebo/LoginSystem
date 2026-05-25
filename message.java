/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginsystem;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 *
 * @author malebo
 */


public class message {
    private static int messageCounter = 0;          // auto-incremented message number
    private int messageNum;                         // stored message number
    private String messageID;                       // 10-digit random
    private String recipient;                       // cell number
    private String messageText;                     // actual message (max 250)
    private String messageHash;                     // generated hash
    private String status;                          // "sent", "stored", "discarded"
    
    // In-memory list to keep all messages for the current session
    private static List<message> allMessages = new ArrayList<>();
    
    // ---------- Constructor ----------
    public message(String recipient, String messageText) {
        this.messageNum = ++messageCounter;
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
        this.status = "created";
    }
    
    // ---------- Auto-generation methods ----------
    String generateMessageID() {
        Random rand = new Random();
        long id = 1_000_000_000L + (long)(rand.nextDouble() * 9_000_000_000L);
        return String.valueOf(id).substring(0, 10);
    }
    
    public String createMessageHash() {
        // first two digits of messageID
        String firstTwo = messageID.substring(0, 2);
        // message number (starts at 1,2,3...)
        String msgNum = String.valueOf(messageNum);
        // first and last words of messageText
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length-1] : firstWord;
        String hash = firstTwo + ":" + msgNum + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }
    
    // ---------- Validation methods ----------
    public static boolean checkMessageLength(String msg) {
        return msg != null && msg.length() <= 250;
    }
    
    public static String getLengthError(String msg) {
        int excess = msg.length() - 250;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }
    
    // Reuse the existing cell phone validator from Login class
    public static boolean checkRecipientCell(String cell, Login loginObj) {
        return loginObj.checkCellPhoneNumber(cell);
    }
    
    // ---------- Menu for send/store/disregard ----------
    public static String sentMessage(message msg, int choice) {
        // choice: 1 = Send, 2 = Disregard, 3 = Store
        switch(choice) {
            case 1:
                msg.status = "sent";
                allMessages.add(msg);
                return "Message successfully sent.";
            case 2:
                msg.status = "discarded";
                // Do not add to allMessages because it's discarded
                return "Press 0 to delete the message.";
            case 3:
                msg.status = "stored";
                allMessages.add(msg);
                storeMessageToJSON(msg);
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }
    
    // ---------- JSON storage (manual, no extra libraries) ----------
    private static void storeMessageToJSON(message msg) {
        try (FileWriter fw = new FileWriter("messages.json", true)) {
            String json = String.format(
                "{\"messageID\":\"%s\",\"messageNum\":%d,\"recipient\":\"%s\",\"messageText\":\"%s\",\"hash\":\"%s\",\"status\":\"%s\"}\n",
                msg.messageID, msg.messageNum, msg.recipient, msg.messageText.replace("\"", "\\\""), msg.messageHash, msg.status
            );
            fw.write(json);
        } catch (IOException e) {
            System.err.println("Error storing message to JSON: " + e.getMessage());
        }
    }
    
    // ---------- Display methods ----------
    public static void printAllMessages() {
        if (allMessages.isEmpty()) {
            System.out.println("No messages sent or stored yet.");
            return;
        }
        for (message m : allMessages) {
            System.out.println(m);
        }
    }
    
    @Override
    public String toString() {
        return String.format("Message ID: %s | Hash: %s | Recipient: %s | Message: %s",
                messageID, messageHash, recipient, messageText);
    }
    
    // ---------- Statistics ----------
    public static int returnTotalMessagesSent() {
        return (int) allMessages.stream().filter(m -> "sent".equals(m.status)).count();
    }
}
