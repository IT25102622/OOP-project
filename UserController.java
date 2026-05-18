package com.realestate.controller;

import com.realestate.model.User;
import com.realestate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

/**
 * Controller for User related actions.
 * Handles registration, login, profile view, and management .
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (!userService.validatePassword(user.getPassword())) {
            model.addAttribute("error", "Password must be at least 6 characters long.");
            return "register";
        }
        if (userService.register(user)) {
            return "redirect:/users/login";
        } else {
            model.addAttribute("error", "Email already exists.");
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.login(email, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/users/profile";
        } else {
            model.addAttribute("error", "Invalid email or password.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }

    @GetMapping("/profile")
    public String viewProfile(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        model.addAttribute("user", loggedInUser);
        return "user-profile";
    }

    @GetMapping("/edit")
    public String showEditForm(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        model.addAttribute("user", loggedInUser);
        return "edit-user";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user, HttpSession session) {
        userService.updateUser(user);
        session.setAttribute("loggedInUser", user);
        return "redirect:/users/profile";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable String id, HttpSession session) {
        userService.deleteUser(id);
        session.invalidate();
        return "redirect:/users/register";
    }
}
