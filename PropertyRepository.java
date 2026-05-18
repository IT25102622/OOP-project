package com.realestate.repository;

import com.realestate.model.Property;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PropertyRepository {
    private static final String FILE_PATH = "data/properties.txt";

    public PropertyRepository() {
        try {
            File file = new File(FILE_PATH);
            if (file.getParentFile() != null) file.getParentFile().mkdirs();
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Property> findAll() {
        List<Property> properties = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] d = line.split(",");
                if (d.length >= 6) {
                    try {
                        properties.add(new Property(
                                d[0].trim(),
                                d[1].trim(),
                                d[2].trim(),
                                Double.parseDouble(d[3].trim()),
                                d[4].trim(),
                                Integer.parseInt(d[5].trim())
                        ));
                    } catch (NumberFormatException e) {
                        // Skip invalid lines
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public void save(Property property) {
        List<Property> properties = findAll();
        boolean exists = false;
        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getId().equals(property.getId())) {
                properties.set(i, property);
                exists = true;
                break;
            }
        }
        if (!exists) properties.add(property);
        writeAll(properties);
    }

    public void deleteById(String id) {
        List<Property> properties = findAll();
        properties.removeIf(p -> p.getId().equals(id));
        writeAll(properties);
    }

    private void writeAll(List<Property> properties) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Property p : properties) {
                pw.println(p.toFileFormat());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
