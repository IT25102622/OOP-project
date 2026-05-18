package com.realestate.controller;

import com.realestate.model.Review;
import com.realestate.model.User;
import com.realestate.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/list")
    public String listReviews(@RequestParam(required = false) String targetId, Model model, HttpSession session) {
        if (targetId != null) {
            model.addAttribute("reviews", reviewService.getReviewsByTargetId(targetId));
            model.addAttribute("averageRating", reviewService.getAverageRating(targetId));
            model.addAttribute("targetId", targetId);
        } else {
            model.addAttribute("reviews", reviewService.getAllReviews());
        }
        model.addAttribute("loggedInUser", session.getAttribute("loggedInUser"));
        return "review-list";
    }

    @GetMapping("/add")
    public String showAddForm(@RequestParam String targetId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        
        Review review = new Review();
        review.setTargetId(targetId);
        review.setUserName(loggedInUser.getFullName()); // Pre-fill with user's name
        model.addAttribute("review", review);
        return "add-review";
    }

    @PostMapping("/add")
    public String addReview(@ModelAttribute Review review, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        
        review.setUserName(loggedInUser.getFullName()); // Ensure it uses the logged-in user's name
        reviewService.addReview(review);
        return "redirect:/reviews/list?targetId=" + review.getTargetId();
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable String id, @RequestParam(required = false) String targetId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        boolean isAdmin = session.getAttribute("loggedInAdmin") != null;
        
        Review review = reviewService.getReviewById(id);
        if (review == null) {
            return "redirect:/reviews/list" + (targetId != null ? "?targetId=" + targetId : "");
        }
        
        // Allow if user is admin, or if user is the author of the review
        if (isAdmin || (loggedInUser != null && loggedInUser.getFullName().equals(review.getUserName()))) {
            reviewService.deleteReview(id);
        }
        
        return "redirect:/reviews/list" + (targetId != null ? "?targetId=" + targetId : "");
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        
        Review review = reviewService.getReviewById(id);
        if (review == null) {
            return "redirect:/reviews/list";
        }
        
        // Only allow editing if the user is the author
        if (!review.getUserName().equals(loggedInUser.getFullName())) {
            return "redirect:/reviews/list?targetId=" + review.getTargetId();
        }
        
        model.addAttribute("review", review);
        return "edit-review";
    }

    @PostMapping("/edit/{id}")
    public String editReview(@PathVariable String id, @ModelAttribute Review reviewDetails, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        
        Review review = reviewService.getReviewById(id);
        if (review == null) {
            return "redirect:/reviews/list";
        }
        
        // Only allow editing if the user is the author
        if (!review.getUserName().equals(loggedInUser.getFullName())) {
            return "redirect:/reviews/list?targetId=" + review.getTargetId();
        }
        
        // Update review details
        review.setRating(reviewDetails.getRating());
        review.setComment(reviewDetails.getComment());
        
        reviewService.addReview(review);
        
        return "redirect:/reviews/list?targetId=" + review.getTargetId();
    }
}
