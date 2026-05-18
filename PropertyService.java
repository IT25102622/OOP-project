package com.realestate.service;

import com.realestate.model.Property;
import com.realestate.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Service layer for Property management.
 */
@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    // For backward compatibility with static calls if any
    private static PropertyRepository staticRepo;
    
    @Autowired
    public void setStaticRepo(PropertyRepository repo) {
        PropertyService.staticRepo = repo;
    }

    public static List<Property> getAll() throws IOException {
        return staticRepo.findAll();
    }

    public static void addProperty(Property p) throws IOException {
        staticRepo.save(p);
    }

    public static void delete(String id) throws IOException {
        staticRepo.deleteById(id);
    }
    
    // Instance methods for modern Spring usage
    public List<Property> findAllProperties() {
        return propertyRepository.findAll();
    }
    
    public void saveProperty(Property property) {
        propertyRepository.save(property);
    }
    
    public void removeProperty(String id) {
        propertyRepository.deleteById(id);
    }
}
