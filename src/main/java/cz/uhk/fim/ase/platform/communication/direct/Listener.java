package cz.uhk.fim.ase.platform.communication.direct;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Listener {

    private MessageQueue queue;

    public Listener(MessageQueue queue) {
        this.queue = queue;
    }
}
