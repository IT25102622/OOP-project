package com.realestate.repository;

import com.realestate.model.Agent;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AgentRepository {
    private static final String FILE_PATH = "data/agents.txt";

    public AgentRepository() {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Agent> findAll() {
        List<Agent> agents = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                Agent agent = Agent.fromFileFormat(line);
                if (agent != null) agents.add(agent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return agents;
    }

    public void save(Agent agent) {
        List<Agent> agents = findAll();
        boolean exists = false;
        for (int i = 0; i < agents.size(); i++) {
            if (agents.get(i).getAgentId().equals(agent.getAgentId())) {
                agents.set(i, agent);
                exists = true;
                break;
            }
        }
        if (!exists) agents.add(agent);
        writeAll(agents);
    }

    public void deleteById(String id) {
        List<Agent> agents = findAll();
        agents.removeIf(a -> a.getAgentId().equals(id));
        writeAll(agents);
    }

    public Agent findById(String id) {
        return findAll().stream()
                .filter(a -> a.getAgentId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private void writeAll(List<Agent> agents) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Agent a : agents) {
                pw.println(a.toFileFormat());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
