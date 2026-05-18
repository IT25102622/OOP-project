package com.realestate.repository;

import com.realestate.model.User;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository layer for User management.
 * Handles reading and writing user data to users.txt
 */
@Repository
public class UserRepository {
    private static final String FILE_PATH = "data/users.txt";

    public UserRepository() {
        // Ensure data directory and file exist
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                User user = User.fromFileFormat(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void save(User user) {
        List<User> users = findAll();
        // Check if user already exists (by ID or Email) for update
        boolean exists = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(user.getUserId()) || users.get(i).getEmail().equals(user.getEmail())) {
                users.set(i, user);
                exists = true;
                break;
            }
        }
        if (!exists) {
            users.add(user);
        }
        writeAll(users);
    }

    public void deleteById(String id) {
        List<User> users = findAll();
        users.removeIf(u -> u.getUserId().equals(id));
        writeAll(users);
    }

    public User findById(String id) {
        return findAll().stream()
                .filter(u -> u.getUserId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public User findByEmail(String email) {
        return findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    private void writeAll(List<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (User u : users) {
                pw.println(u.toFileFormat());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
