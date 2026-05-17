package com.realestate.model;

/**
 * Model class for Admin
 */
public class Admin {
    private String adminId;
    private String username;
    private String password;
    private String email;

    public Admin() {}

    public Admin(String adminId, String username, String password, String email) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String toFileFormat() {
        return adminId + "|" + username + "|" + password + "|" + email;
    }

    public static Admin fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 4) {
            return new Admin(parts[0], parts[1], parts[2], parts[3]);
        }
        return null;
    }

    // Getters and Setters
    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
