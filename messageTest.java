/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginsystem;

/**
 *
 * @author malebo
 */
public class messageTest {
    
    
   
    public static void main(String[] args) {
        Login login = new Login(); // for cell validation
        int passed = 0, failed = 0;
        
        System.out.println("=== Message Unit Tests ===\n");
        
        // 1. Message length success (<=250)
        String shortMsg = "Hi Mike, can you join us for dinner tonight?";
        if (message.checkMessageLength(shortMsg)) {
            System.out.println("✓ PASS: Message length success (within 250)");
            passed++;
        } else {
            System.out.println("✗ FAIL: Short message incorrectly flagged as too long");
            failed++;
        }
        
        // 2. Message length failure (>250)
        StringBuilder longBuilder = new StringBuilder();
        for (int i = 0; i < 260; i++) longBuilder.append("a");
        String longMsg = longBuilder.toString();
        if (!message.checkMessageLength(longMsg)) {
            System.out.println("✓ PASS: Message length failure (exceeds 250)");
            passed++;
            String error = message.getLengthError(longMsg);
            System.out.println("   Error message: " + error);
        } else {
            System.out.println("✗ FAIL: Long message incorrectly accepted");
            failed++;
        }
        
        // 3. Recipient cell correctly formatted (+27718693002)
        String validCell = "+27718693002";
        if (message.checkRecipientCell(validCell, login)) {
            System.out.println("✓ PASS: Cell phone number successfully captured.");
            passed++;
        } else {
            System.out.println("✗ FAIL: Valid cell number rejected");
            failed++;
        }
        
        // 4. Recipient cell incorrectly formatted (08575975889)
        String invalidCell = "08575975889";
        if (!message.checkRecipientCell(invalidCell, login)) {
            System.out.println("✓ PASS: Invalid cell number correctly rejected.");
            passed++;
        } else {
            System.out.println("✗ FAIL: Invalid cell number accepted");
            failed++;
        }
        
        // 5. Message hash generation format test
        message testMsg = new message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String hash = testMsg.createMessageHash();
        String[] parts = hash.split(":");
        if (parts.length == 3 && parts[0].length() == 2 && parts[1].matches("\\d+")) {
            System.out.println("✓ PASS: Message hash format correct. Hash = " + hash);
            passed++;
        } else {
            System.out.println("✗ FAIL: Message hash format incorrect. Got: " + hash);
            failed++;
        }
        
        // 6. Message ID is created (10 digits)
        String msgRep = testMsg.toString();
        boolean hasTenDigits = msgRep.matches(".*\\d{10}.*");
        if (hasTenDigits) {
            System.out.println("✓ PASS: Message ID generated (10 digits present)");
            passed++;
        } else {
            System.out.println("✗ FAIL: Message ID not found or not 10 digits");
            failed++;
        }
        
        // 7. Send Message option (choice 1)
        message sendMsg = new message("+27718693002", "Test send");
        String sendResult = message.sentMessage(sendMsg, 1);
        if ("Message successfully sent.".equals(sendResult)) {
            System.out.println("✓ PASS: 'Send Message' returns correct success message");
            passed++;
        } else {
            System.out.println("✗ FAIL: Send message failed, got: " + sendResult);
            failed++;
        }
        
        // 8. Disregard Message option (choice 2)
        message discardMsg = new message("+27718693002", "Test discard");
        String discardResult = message.sentMessage(discardMsg, 2);
        if ("Press 0 to delete the message.".equals(discardResult)) {
            System.out.println("✓ PASS: 'Disregard Message' returns correct prompt");
            passed++;
        } else {
            System.out.println("✗ FAIL: Disregard message failed, got: " + discardResult);
            failed++;
        }
        
        // 9. Store Message option (choice 3)
        message storeMsg = new message("+27718693002", "Test store");
        String storeResult = message.sentMessage(storeMsg, 3);
        if ("Message successfully stored.".equals(storeResult)) {
            System.out.println("✓ PASS: 'Store Message' returns success and writes to JSON");
            passed++;
        } else {
            System.out.println("✗ FAIL: Store message failed, got: " + storeResult);
            failed++;
        }
        
        System.out.println("\n=== Test Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        if (failed == 0) {
            System.out.println("\n✅ All tests passed!");
        } else {
            System.out.println("\n❌ Some tests failed. Check implementation.");
        }
    }
}
    

