package com.realestate.controller;

import com.realestate.model.Property;
import com.realestate.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * PropertyController – the "C" in MVC.
 *
 * Maps HTTP requests to service calls and passes data to Thymeleaf views.
 * Uses Spring MVC annotations:
 *   @Controller     – marks this as a web controller
 *   @GetMapping     – handles HTTP GET requests
 *   @PostMapping    – handles HTTP POST requests (form submissions)
 *   @RequestParam   – binds query / form parameters to method arguments
 *   @PathVariable   – binds URL path segments to method arguments
 */
@Controller
public class PropertyController {

    // Injected automatically by Spring – Dependency Injection
    @Autowired
    private PropertyService propertyService;

    // -----------------------------------------------------------------------
    // HOME PAGE  –  GET /
    // -----------------------------------------------------------------------

    /**
     * Displays the home / landing page with a summary count.
     */
    @GetMapping("/")
    public String homePage(Model model) {
        int totalProperties = propertyService.getAllProperties().size();
        model.addAttribute("totalProperties", totalProperties);
        return "index";   // → templates/index.html
    }

    // -----------------------------------------------------------------------
    // VIEW ALL PROPERTIES  –  GET /properties
    // -----------------------------------------------------------------------

    /**
     * Reads all properties from file and passes them to the view.
     * Demonstrates READ (file read) operation.
     */
    @GetMapping("/properties")
    public String viewProperties(Model model) {
        List<Property> properties = propertyService.getAllProperties();
        model.addAttribute("properties", properties);
        return "properties";   // → templates/properties.html
    }

    // -----------------------------------------------------------------------
    // SHOW ADD FORM  –  GET /properties/add
    // -----------------------------------------------------------------------

    /**
     * Shows the blank form for adding a new property.
     */
    @GetMapping("/properties/add")
    public String showAddForm(Model model) {
        model.addAttribute("property", new Property());
        return "add-property";   // → templates/add-property.html
    }

    // -----------------------------------------------------------------------
    // SUBMIT ADD FORM  –  POST /properties/add
    // -----------------------------------------------------------------------

    /**
     * Receives form data, creates a Property object, and persists it.
     * Demonstrates CREATE (file write) operation.
     */
    @PostMapping("/properties/add")
    public String addProperty(@RequestParam String title,
                              @RequestParam String location,
                              @RequestParam double price,
                              @RequestParam String propertyType,
                              @RequestParam int bedrooms,
                              RedirectAttributes redirectAttributes) {

        // Build Property from form fields – ID will be auto-generated
        Property property = new Property();
        property.setTitle(title);
        property.setLocation(location);
        property.setPrice(price);
        property.setPropertyType(propertyType);
        property.setBedrooms(bedrooms);

        propertyService.addProperty(property);

        redirectAttributes.addFlashAttribute("successMessage",
                "Property \"" + title + "\" added successfully!");

        return "redirect:/properties";   // PRG pattern – prevents duplicate submissions
    }

    // -----------------------------------------------------------------------
    // SHOW EDIT FORM  –  GET /properties/edit/{id}
    // -----------------------------------------------------------------------

    /**
     * Pre-fills the edit form with existing property data.
     * Demonstrates READ (find by ID) operation.
     */
    @GetMapping("/properties/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Optional<Property> property = propertyService.getPropertyById(id);

        if (property.isEmpty()) {
            return "redirect:/properties";   // redirect if ID not found
        }

        model.addAttribute("property", property.get());
        return "edit-property";   // → templates/edit-property.html
    }

    // -----------------------------------------------------------------------
    // SUBMIT EDIT FORM  –  POST /properties/edit/{id}
    // -----------------------------------------------------------------------

    /**
     * Receives updated form data and saves changes to file.
     * Demonstrates UPDATE (file rewrite) operation.
     */
    @PostMapping("/properties/edit/{id}")
    public String updateProperty(@PathVariable String id,
                                 @RequestParam String title,
                                 @RequestParam String location,
                                 @RequestParam double price,
                                 @RequestParam String propertyType,
                                 @RequestParam int bedrooms,
                                 RedirectAttributes redirectAttributes) {

        Property updated = new Property(id, title, location, price, propertyType, bedrooms);
        boolean success = propertyService.updateProperty(updated);

        if (success) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Property \"" + title + "\" updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Property with ID " + id + " was not found.");
        }

        return "redirect:/properties";
    }

    // -----------------------------------------------------------------------
    // DELETE PROPERTY  –  GET /properties/delete/{id}
    // -----------------------------------------------------------------------

    /**
     * Deletes a property by ID and redirects back to the list.
     * Demonstrates DELETE (file rewrite without the record) operation.
     *
     * Note: A GET mapping is used here for simplicity in the HTML table.
     * In production, a DELETE HTTP method via a form POST would be preferred.
     */
    @GetMapping("/properties/delete/{id}")
    public String deleteProperty(@PathVariable String id,
                                 RedirectAttributes redirectAttributes) {

        boolean deleted = propertyService.deleteProperty(id);

        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Property " + id + " deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Property with ID " + id + " was not found.");
        }

        return "redirect:/properties";
    }

    // -----------------------------------------------------------------------
    // VIEW SINGLE PROPERTY  –  GET /properties/view/{id}
    // -----------------------------------------------------------------------

    /**
     * Shows full details of a single property.
     */
    @GetMapping("/properties/view/{id}")
    public String viewProperty(@PathVariable String id, Model model) {
        Optional<Property> property = propertyService.getPropertyById(id);

        if (property.isEmpty()) {
            return "redirect:/properties";
        }

        model.addAttribute("property", property.get());
        return "view-property";   // → templates/view-property.html
    }
}
