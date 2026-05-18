package realestate.model;

/**
 * Model class for Booking / Inquiry
 */
public class Booking {
    private String inquiryId;
    private String userName;
    private String propertyId;
    private String propertyTitle;
    private String message;
    private String bookingDate;
    private String status; // Pending, Approved, Rejected

    public Booking() {}

    public Booking(String inquiryId, String userName, String propertyId, String propertyTitle, String message, String bookingDate, String status) {
        this.inquiryId = inquiryId;
        this.userName = userName;
        this.propertyId = propertyId;
        this.propertyTitle = propertyTitle;
        this.message = message;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    public String toFileFormat() {
        return inquiryId + "|" + userName + "|" + propertyId + "|" + propertyTitle + "|" + message + "|" + bookingDate + "|" + status;
    }

    public static Booking fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 7) {
            return new Booking(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
        }
        return null;
    }

    // Getters and Setters
    public String getInquiryId() { return inquiryId; }
    public void setInquiryId(String inquiryId) { this.inquiryId = inquiryId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPropertyId() { return propertyId; }
    public void setPropertyId(String propertyId) { this.propertyId = propertyId; }

    public String getPropertyTitle() { return propertyTitle; }
    public void setPropertyTitle(String propertyTitle) { this.propertyTitle = propertyTitle; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
