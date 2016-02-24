package main.java.cz.uhk.fim.ase.platform.communication.direct;

import cz.uhk.fim.ase.platform.communication.internal.ZeromqContext;
import cz.uhk.fim.ase.platform.core.Config;
import cz.uhk.fim.ase.platform.model.Message;
import java.io.IOException;
import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZMQ;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Listener {

    private static Logger logger = LoggerFactory.getLogger(Listener.class);

    private FSTConfiguration fst = FSTConfiguration.createDefaultConfiguration();
    private Config config;
    private MessageQueue queue;
    private long counter = 0;

    public Listener(Config config, MessageQueue queue) {
        this.config = config;
        this.queue = queue;
    }

    public long getCounter() {
        return counter;
    }

    public void resetCounter() {
        counter = 0;
    }

    public void listen() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ZMQ.Context context = ZeromqContext.getContext();
                ZMQ.Socket socket = context.socket(ZMQ.PULL);
                socket.bind("tcp://" + config.getPlatform());
                while (!Thread.currentThread().isInterrupted()) {
                    byte[] bytes = socket.recv(0);
                    try {
                        Object message = fst.getObjectInput(bytes).readObject();
                        handle(message);
                    } catch (ClassNotFoundException | IOException e) {
                        // TODO: why we sometimes receive weird messages like 5 bytes?
                    }
                }
            }
        });
        thread.setName("subscriber");
        thread.start();
    }

    private void handle(Object object) {
        if (object instanceof Message) {
            counter++;
            Message message = (Message) object;
            queue.add(message);
        }
    }
}
