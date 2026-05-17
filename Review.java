package com.realestate.model;

import java.time.LocalDate;

/**
 * Model class for Review & Rating
 */
public class Review {
    private String reviewId;
    private String userName;
    private String targetId; // Property ID or Agent ID
    private int rating; // 1 to 5
    private String comment;
    private String reviewDate;

    public Review() {
        this.reviewDate = LocalDate.now().toString();
    }

    public Review(String reviewId, String userName, String targetId, int rating, String comment, String reviewDate) {
        this.reviewId = reviewId;
        this.userName = userName;
        this.targetId = targetId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public String toFileFormat() {
        return reviewId + "|" + userName + "|" + targetId + "|" + rating + "|" + comment + "|" + reviewDate;
    }

    public static Review fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 6) {
            return new Review(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4], parts[5]);
        }
        return null;
    }

    // Getters and Setters
    public String getReviewId() { return reviewId; }
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getTargetId() { return targetId; }
    public void setTargetId(String targetId) { this.targetId = targetId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getReviewDate() { return reviewDate; }
    public void setReviewDate(String reviewDate) { this.reviewDate = reviewDate; }
}
