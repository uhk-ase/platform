package cz.uhk.fim.ase.platform.agents;

import cz.uhk.fim.ase.platform.core.Platform;
import cz.uhk.fim.ase.platform.core.Registry;
import cz.uhk.fim.ase.platform.model.Agent;
import cz.uhk.fim.ase.platform.model.Message;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public abstract class GenericAgent implements Runnable {

    private Agent identity;
    private Platform platform;

    public GenericAgent(Platform platform, Agent identity) {
        this.platform = platform;
        this.identity = identity;
    }

    public Agent getIdentity() {
        return identity;
    }

    public Registry getRegistry() {
        return platform.getRegistry();
    }

    public void send(Message message) {
        if (message.getSender() == null) {
            message.setSender(getIdentity());
        }
        platform.getSender().send(message);
    }

    public Message receive() {
        return platform.getQueue().get(getIdentity());
    }

    public abstract void run();
}
