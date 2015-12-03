package cz.uhk.fim.ase.platform.communication.direct;

import cz.uhk.fim.ase.platform.core.Config;
import cz.uhk.fim.ase.platform.model.Message;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Sender {

    private Config config;
    private MessageQueue queue;

    public Sender(Config config, MessageQueue queue) {
        this.config = config;
        this.queue = queue;
    }

    public void send(Message message) {
        if (message.getRecipient() != null) {
            if (message.getRecipient().getPlatform().equals(config.getPlatform())) {
                queue.add(message);
            } else {
                // TODO
            }
        }
    }
}
