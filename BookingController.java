package realestate.controller;

import com.realestate.model.Booking;
import com.realestate.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/list")
    public String listBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "booking-list";
    }

    @GetMapping("/add")
    public String showAddForm(@RequestParam(required = false) String propertyId, 
                              @RequestParam(required = false) String propertyTitle, 
                              Model model) {
        Booking booking = new Booking();
        booking.setPropertyId(propertyId);
        booking.setPropertyTitle(propertyTitle);
        model.addAttribute("booking", booking);
        return "add-booking";
    }

    @PostMapping("/add")
    public String addBooking(@ModelAttribute Booking booking) {
        bookingService.createBooking(booking);
        return "redirect:/bookings/list";
    }

    @GetMapping("/details/{id}")
    public String viewBooking(@PathVariable String id, Model model) {
        model.addAttribute("booking", bookingService.getBookingById(id));
        return "booking-details";
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam String inquiryId, @RequestParam String status) {
        bookingService.updateStatus(inquiryId, status);
        return "redirect:/bookings/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
        return "redirect:/bookings/list";
    }
}
