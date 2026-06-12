/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginsystem;
import java.util.List;

/**
 *
 * @author malebo
 */
public class messagePart3Test {
    
    
   
   

    public static void main(String[] args) {
        // Reset all data before testing
        message.resetData();
        
        int passed = 0, failed = 0;
        
        System.out.println("=== Part 3 Unit Tests ===\n");
        
        // Create test messages (matching assignment test data)
        Login login = new Login();
        
        // Test Message 1 (as per assignment)
        message msg1 = new message("+27718693002", "Where are you? You are late! I have asked you to be on time.");
        message.sentMessage(msg1, 1); // Sent
        
        // Test Message 2 (disregarded)
        message msg2 = new message("+27718693002", "Did you get the cake?");
        message.sentMessage(msg2, 2); // Disregarded (not in sent)
        
        // Test Message 3
        message msg3 = new message("+27718693002", "It is dinner time!");
        message.sentMessage(msg3, 1); // Sent
        
        // Test Message 4 (from screenshot)
        message msg4 = new message("0838884567", "It is dinner time!");
        message.sentMessage(msg4, 1); // Sent (using test data)
        
        // Test Message 5 (from screenshot)
        message msg5 = new message("+27838884567", "Ok, I am leaving without you.");
        essage.sentMessage(msg5, 3); // Stored
        
        // Test 1: Sent Messages array correctly populated
        List<message> sent = Message.getSentMessages();
        boolean hasCake = false;
        boolean hasDinnerTime = false;
        for (message m : sent) {
            if (m.getMessageText().equals("Did you get the cake?")) hasCake = true;
            if (m.getMessageText().equals("It is dinner time!")) hasDinnerTime = true;
        }
        if (hasCake || hasDinnerTime) {
            System.out.println("✓ PASS: Sent messages array contains expected test data");
            passed++;
        } else {
            System.out.println("✗ FAIL: Sent messages array missing expected data");
            failed++;
        }
        
        // Test 2: Longest message
        String longest = message.getLongestMessage();
        String expectedLongest = "Where are you? You are late! I have asked you to be on time.";
        if (longest.equals(expectedLongest)) {
            System.out.println("✓ PASS: Longest message correctly identified");
            passed++;
        } else {
            System.out.println("✗ FAIL: Longest message incorrect. Got: " + longest);
            failed++;
        }
        
        // Test 3: Search by Message ID (for message 4)
        String msg4Id = msg4.getmessageID();
        message foundById = Message.searchByMessageID(msg4Id);
        if (foundById != null && foundById.getMessageText().equals("It is dinner time!")) {
            System.out.println("✓ PASS: Search by message ID works");
            passed++;
        } else {
            System.out.println("✗ FAIL: Search by message ID failed");
            failed++;
        }
        
        // Test 4: Search by recipient (+27838884567)
        List<message> byRecipient = Message.searchByRecipient("+27838884567");
        boolean foundLeaving = false;
        for (message m : byRecipient) {
            if (m.getMessageText().contains("leaving")) foundLeaving = true;
        }
        if (foundLeaving) {
            System.out.println("✓ PASS: Search by recipient works");
            passed++;
        } else {
            System.out.println("✗ FAIL: Search by recipient failed");
            failed++;
        }
        
        // Test 5: Delete by message hash (using msg2 hash - should not be in sent)
        String hashToDelete = msg2.getmessageHash();
        boolean deleted = message.deleteBymessageHash(hashToDelete);
        // Message 2 was disregarded, so it shouldn't be in sent/stored
        if (!deleted) {
            System.out.println("✓ PASS: Delete non-existent message returns false correctly");
            passed++;
        } else {
            System.out.println("✗ FAIL: Delete should have returned false for disregarded message");
            failed++;
        }
        
        // Test 6: Delete a sent message
        String hashToDelete2 = msg1.getmessageHash();
        boolean deleted2 = message.deleteBymessageHash(hashToDelete2);
        if (deleted2) {
            System.out.println("✓ PASS: Delete sent message works");
            passed++;
        } else {
            System.out.println("✗ FAIL: Delete sent message failed");
            failed++;
        }
        
        // Test 7: Verify deletion reduced count
        int newSize = message.getSentmessages().size();
        if (newSize == 2) { // msg3 and msg4 remain
            System.out.println("✓ PASS: Message correctly removed from array");
            passed++;
        } else {
            System.out.println("✗ FAIL: Array size incorrect after deletion. Expected 2, got " + newSize);
            failed++;
        }
        
        System.out.println("\n=== Test Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        
        if (failed == 0) {
            System.out.println("\n✅ All Part 3 tests passed!");
        } else {
            System.out.println("\n❌ Some tests failed.");
        }
    }
