package com.realestate.model;

/**
 * Model class for Agent
 * Represents a real estate agent in the system.
 */
public class Agent {
    private String agentId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String agencyName;
    private String experience; // years
    private String specialization;
    private double rating;

    public Agent() {}

    public Agent(String agentId, String fullName, String email, String phoneNumber, String agencyName, String experience, String specialization, double rating) {
        this.agentId = agentId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.agencyName = agencyName;
        this.experience = experience;
        this.specialization = specialization;
        this.rating = rating;
    }

    public String toFileFormat() {
        return agentId + "|" + fullName + "|" + email + "|" + phoneNumber + "|" + agencyName + "|" + experience + "|" + specialization + "|" + rating;
    }

    public static Agent fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 8) {
            return new Agent(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], Double.parseDouble(parts[7]));
        }
        return null;
    }

    // Getters and Setters
    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAgencyName() { return agencyName; }
    public void setAgencyName(String agencyName) { this.agencyName = agencyName; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}
