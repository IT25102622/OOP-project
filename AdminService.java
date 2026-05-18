package com.realestate.service;

import com.realestate.model.Admin;
import com.realestate.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AgentRepository agentRepository;
    
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    public void addAdmin(Admin admin) {
        if (admin.getAdminId() == null || admin.getAdminId().isEmpty()) {
            admin.setAdminId(UUID.randomUUID().toString().substring(0, 8));
        }
        adminRepository.save(admin);
    }

    public Admin login(String username, String password) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public void deleteAdmin(String id) {
        adminRepository.deleteById(id);
    }

    // Dashboard Statistics
    public long getTotalUsers() {
        return userRepository.findAll().size();
    }

    public long getTotalAgents() {
        return agentRepository.findAll().size();
    }

    public long getTotalBookings() {
        return bookingRepository.findAll().size();
    }

    public long getTotalProperties() {
        return propertyRepository.findAll().size();
    }
}
