package cz.uhk.fim.ase.platform.communication.broadcast;

import cz.uhk.fim.ase.platform.model.Agent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class SyncMessage {

    private String platform;
    private Long tick;
    private List<Agent> agents = new ArrayList<>();

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Long getTick() {
        return tick;
    }

    public void setTick(Long tick) {
        this.tick = tick;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }
}
