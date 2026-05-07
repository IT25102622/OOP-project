package com.realestate.service;

import com.realestate.model.Property;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyService {

    private static final String FILE = "data/properties.txt";

    // CREATE (Add Property)
    public static void addProperty(Property p) throws IOException {
        FileWriter fw = new FileWriter(FILE, true);
        fw.write(p.toFileFormat() + "\n");
        fw.close();
    }

    // READ (View All Properties)
    public static List<Property> getAll() throws IOException {
        List<Property> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(FILE));
        String line;

        while ((line = br.readLine()) != null) {
            String[] d = line.split(",");
            list.add(
                    new Property(
                            d[0],
                            d[1],
                            d[2],
                            Double.parseDouble(d[3]),
                            d[4],
                            Integer.parseInt(d[5])
                    )
            );
        }
        br.close();
        return list;
    }

    // DELETE (Remove Property)
    public static void delete(String id) throws IOException {
        List<Property> props = getAll();
        FileWriter fw = new FileWriter(FILE);

        for (Property p : props) {
            if (!p.getId().equals(id)) {
                fw.write(p.toFileFormat() + "\n");
            }
        }
        fw.close();
    }
}
