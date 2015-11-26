package cz.uhk.fim.ase.platform.core;

import cz.uhk.fim.ase.platform.model.Agent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Registry {

    private List<Agent> agents = new ArrayList<>();
    private Map<String, List<Agent>> agentsByPlatform = new HashMap<>();
    private Map<String, Map<String, List<Agent>>> agentsByAttributes = new HashMap<>();

    public List<Agent> getAgents() {
        return agents;
    }

    public List<Agent> getAgentsByPlatform(String platform) {
        List<Agent> agents = agentsByPlatform.get(platform);
        if (agents == null) {
            agents = new ArrayList<>();
        }
        return agents;
    }

    public List<Agent> getAgentsByAttributeValue(String attributeName, String attributeValue) {
        Map<String, List<Agent>> agentsByValues = agentsByAttributes.get(attributeName);
        List<Agent> agents = new ArrayList<>();
        if (agentsByValues != null) {
            if (attributeValue != null) {
                agents.addAll(agentsByValues.get(attributeValue));
            } else {
                for (List<Agent> list : agentsByValues.values()) {
                    agents.addAll(list);
                }
            }
        }
        return agents;
    }

    public List<Agent> getAgentsByAttribute(String attributeName) {
        return getAgentsByAttributeValue(attributeName, null);
    }

    public void addAgent(Agent agent) {
        agents.add(agent);

        if (agentsByPlatform.containsKey(agent.getPlatform())) {
            agentsByPlatform.get(agent.getPlatform()).add(agent);
        } else {
            List<Agent> list = new ArrayList<>();
            list.add(agent);
            agentsByPlatform.put(agent.getPlatform(), list);
        }

        for (Map.Entry<String, String> entry : agent.getAttributes().entrySet()) {
            if (!agentsByAttributes.containsKey(entry.getKey())) {
                agentsByAttributes.put(entry.getKey(), new HashMap<String, List<Agent>>());
            } else {
                if (agentsByAttributes.get(entry.getKey()).containsKey(entry.getValue())) {
                    agentsByAttributes.get(entry.getKey()).get(entry.getValue()).add(agent);
                } else {
                    List<Agent> list = new ArrayList<>();
                    list.add(agent);
                    agentsByAttributes.get(entry.getKey()).put(entry.getValue(), list);
                }
            }
        }
    }

    public void removeAgents(List<Agent> agents) {
        if (agents != null) {
            agents.removeAll(agents);
            for (List<Agent> list : this.agentsByPlatform.values()) {
                list.removeAll(agents);
            }
            for (Map<String, List<Agent>> agentsByValues : agentsByAttributes.values()) {
                for (List<Agent> list : agentsByValues.values()) {
                    list.removeAll(agents);
                }
            }
        }
    }

    public void removeAgentsByPlatform(String platform) {
        List<Agent> agents = agentsByPlatform.get(platform);
        removeAgents(agents);
    }
}
