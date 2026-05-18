package com.realestate.controller;

import com.realestate.model.Agent;
import com.realestate.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/agents")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @GetMapping("/list")
    public String listAgents(Model model, HttpSession session) {
        model.addAttribute("agents", agentService.getAllAgents());
        model.addAttribute("loggedInAdmin", session.getAttribute("loggedInAdmin"));
        return "agent-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/agents/list";
        }
        model.addAttribute("agent", new Agent());
        return "add-agent";
    }

    @PostMapping("/add")
    public String addAgent(@ModelAttribute Agent agent, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/agents/list";
        }
        agentService.addAgent(agent);
        return "redirect:/agents/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/agents/list";
        }
        Agent agent = agentService.getAgentById(id);
        model.addAttribute("agent", agent);
        return "edit-agent";
    }

    @PostMapping("/update")
    public String updateAgent(@ModelAttribute Agent agent, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/agents/list";
        }
        agentService.updateAgent(agent);
        return "redirect:/agents/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAgent(@PathVariable String id, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/agents/list";
        }
        agentService.deleteAgent(id);
        return "redirect:/agents/list";
    }

    @GetMapping("/details/{id}")
    public String viewAgent(@PathVariable String id, Model model) {
        model.addAttribute("agent", agentService.getAgentById(id));
        return "agent-details";
    }

    @GetMapping("/search")
    public String searchAgents(@RequestParam String query, Model model, HttpSession session) {
        model.addAttribute("agents", agentService.searchAgents(query));
        model.addAttribute("loggedInAdmin", session.getAttribute("loggedInAdmin"));
        return "agent-list";
    }
}
