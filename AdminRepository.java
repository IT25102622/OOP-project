package com.realestate.repository;

import com.realestate.model.Admin;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminRepository {
    private static final String FILE_PATH = "data/admins.txt";

    public AdminRepository() {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                file.createNewFile();
                // Add default admin if empty
                save(new Admin("1", "admin", "admin123", "admin@realestate.com"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Admin> findAll() {
        List<Admin> admins = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                Admin admin = Admin.fromFileFormat(line);
                if (admin != null) admins.add(admin);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return admins;
    }

    public void save(Admin admin) {
        List<Admin> admins = findAll();
        boolean exists = false;
        for (int i = 0; i < admins.size(); i++) {
            if (admins.get(i).getAdminId().equals(admin.getAdminId())) {
                admins.set(i, admin);
                exists = true;
                break;
            }
        }
        if (!exists) admins.add(admin);
        writeAll(admins);
    }

    public void deleteById(String id) {
        List<Admin> admins = findAll();
        admins.removeIf(a -> a.getAdminId().equals(id));
        writeAll(admins);
    }

    public Admin findByUsername(String username) {
        return findAll().stream()
                .filter(a -> a.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    private void writeAll(List<Admin> admins) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Admin a : admins) {
                pw.println(a.toFileFormat());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
