package com.realestate.repository;

import com.realestate.model.Review;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepository {
    private static final String FILE_PATH = "data/reviews.txt";

    public ReviewRepository() {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Review> findAll() {
        List<Review> reviews = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                Review review = Review.fromFileFormat(line);
                if (review != null) reviews.add(review);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public void save(Review review) {
        List<Review> reviews = findAll();
        boolean exists = false;
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).getReviewId().equals(review.getReviewId())) {
                reviews.set(i, review);
                exists = true;
                break;
            }
        }
        if (!exists) reviews.add(review);
        writeAll(reviews);
    }

    public void deleteById(String id) {
        List<Review> reviews = findAll();
        reviews.removeIf(r -> r.getReviewId().equals(id));
        writeAll(reviews);
    }

    private void writeAll(List<Review> reviews) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Review r : reviews) {
                pw.println(r.toFileFormat());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
