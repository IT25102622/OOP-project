package com.realestate.service;

import com.realestate.model.Agent;
import com.realestate.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    public void addAgent(Agent agent) {
        if (agent.getAgentId() == null || agent.getAgentId().isEmpty()) {
            agent.setAgentId(UUID.randomUUID().toString().substring(0, 8));
        }
        agentRepository.save(agent);
    }

    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    public Agent getAgentById(String id) {
        return agentRepository.findById(id);
    }

    public void updateAgent(Agent agent) {
        agentRepository.save(agent);
    }

    public void deleteAgent(String id) {
        agentRepository.deleteById(id);
    }

    public List<Agent> searchAgents(String query) {
        return agentRepository.findAll().stream()
                .filter(a -> a.getFullName().toLowerCase().contains(query.toLowerCase()) || 
                             a.getSpecialization().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
