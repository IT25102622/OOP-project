package com.realestate.controller;

import com.realestate.model.Property;
import com.realestate.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Controller
public class PropertyController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/add")
    public String addPage() {
        return "add";
    }

    @PostMapping("/addProperty")
    public String add(@RequestParam String id,
                      @RequestParam String title,
                      @RequestParam String location,
                      @RequestParam double price,
                      @RequestParam String type,
                      @RequestParam int bedrooms) throws IOException {
        PropertyService.addProperty(new Property(id, title, location, price, type, bedrooms));
        return "redirect:/view";
    }

    @GetMapping("/view")
    public String view(Model model) throws IOException {
        model.addAttribute("properties", PropertyService.getAll());
        return "view";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) throws IOException {
        PropertyService.delete(id);
        return "redirect:/view";
    }
}
