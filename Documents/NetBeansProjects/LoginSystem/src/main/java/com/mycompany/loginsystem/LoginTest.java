/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginsystem;

/**
 *
 * @author malebo
 */
public class LoginTest {
   
    public static void main(String[] args) {
        Login login = new Login();
        int passed = 0;
        int failed = 0;
        
        System.out.println("=== Login Unit Tests ===\n");
        
        // Test 1: Username correctly formatted
        if (login.checkUserName("kyl_1")) {
            System.out.println("✓ Test 1 PASSED: Username 'kyl_1' is valid");
            passed++;
        } else {
            System.out.println("✗ Test 1 FAILED: Username 'kyl_1' should be valid");
            failed++;
        }
        
        // Test 2: Username incorrectly formatted
        if (!login.checkUserName("kyle!!!!!!!")) {
            System.out.println("✓ Test 2 PASSED: Username 'kyle!!!!!!!' is invalid");
            passed++;
        } else {
            System.out.println("✗ Test 2 FAILED: Username 'kyle!!!!!!!' should be invalid");
            failed++;
        }
        
        // Test 3: Password meets complexity
        if (login.checkPasswordComplexity("Ch&sec@ke99!")) {
            System.out.println("✓ Test 3 PASSED: Password meets requirements");
            passed++;
        } else {
            System.out.println("✗ Test 3 FAILED: Password should be valid");
            failed++;
        }
        
        // Test 4: Password does not meet complexity
        if (!login.checkPasswordComplexity("password")) {
            System.out.println("✓ Test 4 PASSED: Password 'password' is invalid");
            passed++;
        } else {
            System.out.println("✗ Test 4 FAILED: Password 'password' should be invalid");
            failed++;
        }
        
        // Test 5: Cell phone correctly formatted
        if (login.checkCellPhoneNumber("+27838968976")) {
            System.out.println("✓ Test 5 PASSED: Cell number is valid");
            passed++;
        } else {
            System.out.println("✗ Test 5 FAILED: Cell number should be valid");
            failed++;
        }
        
        // Test 6: Cell phone incorrectly formatted
        if (!login.checkCellPhoneNumber("08966553")) {
            System.out.println("✓ Test 6 PASSED: Cell number '08966553' is invalid");
            passed++;
        } else {
            System.out.println("✗ Test 6 FAILED: Cell number '08966553' should be invalid");
            failed++;
        }
        
        // Test 7: Registration success
        String result = login.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976", "Kyle", "Smith");
        if (result.equals("User has been registered successfully.")) {
            System.out.println("✓ Test 7 PASSED: Registration successful");
            passed++;
        } else {
            System.out.println("✗ Test 7 FAILED: Registration failed - " + result);
            failed++;
        }
        
        // Test 8: Login success
        if (login.loginUser("kyl_1", "Ch&sec@ke99!")) {
            System.out.println("✓ Test 8 PASSED: Login successful");
            passed++;
        } else {
            System.out.println("✗ Test 8 FAILED: Login failed");
            failed++;
        }
        
        System.out.println("\n=== Test Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        
        if (failed == 0) {
            System.out.println("\n✅ All tests passed!");
        } else {
            System.out.println("\n❌ Some tests failed!");
        }
    }
}
    

