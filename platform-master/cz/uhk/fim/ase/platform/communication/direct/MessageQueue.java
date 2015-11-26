package cz.uhk.fim.ase.platform.communication.direct;

import cz.uhk.fim.ase.platform.model.Agent;
import cz.uhk.fim.ase.platform.model.Message;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class MessageQueue {

    private Map<Agent, Queue<Message>> memory = new ConcurrentHashMap<>();

    public synchronized void add(Message message) {
        if (!memory.containsKey(message.getRecipient())) {
            memory.put(message.getRecipient(), new LinkedList<Message>());
        }
        memory.get(message.getRecipient()).add(message);
    }

    public synchronized Message get(Agent recipient) {
        if (memory.containsKey(recipient)) {
            return memory.get(recipient).poll();
        }
        return null;
    }
}
