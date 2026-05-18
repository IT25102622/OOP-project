package realestate.repository;

import com.realestate.model.Booking;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookingRepository {
    private static final String FILE_PATH = "data/bookings.txt";

    public BookingRepository() {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                Booking booking = Booking.fromFileFormat(line);
                if (booking != null) bookings.add(booking);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public void save(Booking booking) {
        List<Booking> bookings = findAll();
        boolean exists = false;
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getInquiryId().equals(booking.getInquiryId())) {
                bookings.set(i, booking);
                exists = true;
                break;
            }
        }
        if (!exists) bookings.add(booking);
        writeAll(bookings);
    }

    public void deleteById(String id) {
        List<Booking> bookings = findAll();
        bookings.removeIf(b -> b.getInquiryId().equals(id));
        writeAll(bookings);
    }

    public Booking findById(String id) {
        return findAll().stream()
                .filter(b -> b.getInquiryId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private void writeAll(List<Booking> bookings) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Booking b : bookings) {
                pw.println(b.toFileFormat());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
