/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginsystem;

import java.util.regex.Pattern;

/**
 *
 * @author Student
 */
public class Login {
    
    public String registeredUsername;
    public String registeredPassword;
    public String registeredCellNumber;
    public String firstName;
    public String lastName;
    
    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }
    
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }
        
        return hasCapital && hasNumber && hasSpecial;
    }
    
    public boolean checkCellPhoneNumber(String cellNumber) {
        String regex = "^\\+27[0-9]{9}$";
        return cellNumber != null && Pattern.matches(regex, cellNumber);
    }
    
    public String registerUser(String username, String password, String cellNumber, 
                               String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        
        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell number is incorrectly formatted or does not contain international code; please correct the number and try again.";
        }
        
        this.registeredUsername = username;
        this.registeredPassword = password;
        this.registeredCellNumber = cellNumber;
        
        return "User has been registered successfully.";
    }
    
    public boolean loginUser(String username, String password) {
        return registeredUsername != null && 
               registeredUsername.equals(username) && 
               registeredPassword != null && 
               registeredPassword.equals(password);
    }
    
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
    

