package cz.uhk.fim.ase.platform.communication.direct;

import cz.uhk.fim.ase.platform.communication.internal.ZeromqContext;
import cz.uhk.fim.ase.platform.core.Config;
import cz.uhk.fim.ase.platform.model.Message;
import java.util.HashMap;
import java.util.Map;
import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZMQ;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Sender {

    private static Logger logger = LoggerFactory.getLogger(Sender.class);

    private FSTConfiguration fst = FSTConfiguration.createDefaultConfiguration();
    private Config config;
    private MessageQueue queue;
    private Map<String, ZMQ.Socket> sockets = new HashMap<>();
    private long counter = 0;

    public Sender(Config config, MessageQueue queue) {
        this.config = config;
        this.queue = queue;
    }

    public long getCounter() {
        return counter;
    }

    public void resetCounter() {
        counter = 0;
    }

    public synchronized void send(Message message) {
        if (message.getRecipient() != null) {
            counter++;
            if (message.getRecipient().getPlatform().equals(config.getPlatform())) {
                queue.add(message);
            } else {
                byte[] bytes = fst.asByteArray(message);
                getSocket(message.getRecipient().getPlatform()).send(bytes, 0);
            }
        }
    }

    private synchronized ZMQ.Socket getSocket(String address) {
        if (!sockets.containsKey(address)) {
            ZMQ.Context context = ZeromqContext.getContext();
            ZMQ.Socket socket = context.socket(ZMQ.PUSH);
            socket.connect("tcp://" + address);
            sockets.put(address, socket);
        }
        return sockets.get(address);
    }
}
