/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.loginsystem;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class LoginSystem {

    public static void main(String[] args) {
          Scanner scanner = new Scanner(System.in);
        Login login = new Login();
        
        System.out.println("=== Registration System ===\n");
        
        // Get user information
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Enter username (must contain _ and be max 5 chars): ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password (8+ chars, 1 capital, 1 number, 1 special): ");
        String password = scanner.nextLine();
        
        System.out.print("Enter cell number (format: +27XXXXXXXXX): ");
        String cellNumber = scanner.nextLine();
        
        // Attempt registration
        String registrationResult = login.registerUser(username, password, cellNumber, 
                                                        firstName, lastName);
        System.out.println("\n" + registrationResult);
        
        // If registration successful, attempt login
        if (registrationResult.equals("User has been registered successfully.")) {
            System.out.println("\n=== Login ===\n");
            
            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();
            
            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();
            
            String loginStatus = login.returnLoginStatus(loginUsername, loginPassword);
            System.out.println("\n" + loginStatus);
        }
        
        scanner.close();
    }
}
    

