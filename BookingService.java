package realestate.service;

import com.realestate.model.Booking;
import realestate.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public void createBooking(Booking booking) {
        if (booking.getInquiryId() == null || booking.getInquiryId().isEmpty()) {
            booking.setInquiryId(UUID.randomUUID().toString().substring(0, 8));
        }
        if (booking.getStatus() == null || booking.getStatus().isEmpty()) {
            booking.setStatus("Pending");
        }
        bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(String id) {
        return bookingRepository.findById(id);
    }

    public void updateStatus(String id, String status) {
        Booking booking = bookingRepository.findById(id);
        if (booking != null) {
            booking.setStatus(status);
            bookingRepository.save(booking);
        }
    }

    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> searchBookings(String query) {
        return bookingRepository.findAll().stream()
                .filter(b -> b.getUserName().toLowerCase().contains(query.toLowerCase()) || 
                             b.getPropertyTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
