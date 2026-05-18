package com.realestate.model;

/**
 * Model class for User
 * Represents a customer (Buyer/Seller) in the system .
 */
public class User {
    private String userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    private String role; // Buyer or Seller

    public User() {}

    public User(String userId, String fullName, String email, String phoneNumber, String password, String address, String role) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    // Convert object to a string for text file storage (CSV format)
    public String toFileFormat() {
        return userId + "|" + fullName + "|" + email + "|" + phoneNumber + "|" + password + "|" + address + "|" + role;
    }

    // Create object from a string from the text file
    public static User fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 7) {
            return new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
        }
        return null;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
