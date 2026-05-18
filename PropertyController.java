package com.realestate.controller;

import com.realestate.model.Property;
import com.realestate.model.User;
import com.realestate.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/add")
    public String addPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"Seller".equalsIgnoreCase(user.getRole())) {
            model.addAttribute("error", "Access Denied: Only Sellers can add properties.");
            return "redirect:/view";
        }
        return "add";
    }

    @PostMapping("/addProperty")
    public String add(@RequestParam String id,
                      @RequestParam String title,
                      @RequestParam String location,
                      @RequestParam double price,
                      @RequestParam String type,
                      @RequestParam int bedrooms,
                      HttpSession session,
                      Model model) throws IOException {
        
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"Seller".equalsIgnoreCase(user.getRole())) {
            return "redirect:/view";
        }
        
        propertyService.saveProperty(new Property(id, title, location, price, type, bedrooms));
        return "redirect:/view";
    }

    @GetMapping("/view")
    public String view(@RequestParam(required = false) String query, Model model, HttpSession session) throws IOException {
        List<Property> properties = propertyService.findAllProperties();
        if (query != null && !query.isEmpty()) {
            properties = properties.stream()
                    .filter(p -> p.getTitle().toLowerCase().contains(query.toLowerCase()) || 
                                 p.getLocation().toLowerCase().contains(query.toLowerCase()) ||
                                 p.getType().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        model.addAttribute("properties", properties);
        model.addAttribute("loggedInUser", session.getAttribute("loggedInUser"));
        return "view";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"Seller".equalsIgnoreCase(user.getRole())) {
            return "redirect:/view";
        }
        propertyService.removeProperty(id);
        return "redirect:/view";
    }
}
