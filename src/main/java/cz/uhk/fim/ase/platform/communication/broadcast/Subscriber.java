package cz.uhk.fim.ase.platform.communication.broadcast;

import cz.uhk.fim.ase.platform.communication.internal.ZeromqContext;
import cz.uhk.fim.ase.platform.core.Config;
import cz.uhk.fim.ase.platform.core.Registry;
import cz.uhk.fim.ase.platform.core.TickManager;
import cz.uhk.fim.ase.platform.model.Agent;
import java.io.IOException;
import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZMQ;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Subscriber {

    private static Logger logger = LoggerFactory.getLogger(Subscriber.class);

    private FSTConfiguration fst = FSTConfiguration.createDefaultConfiguration();
    private Config config;
    private Registry registry;
    private final TickManager tickManager;
    private final Publisher publisher;

    public Subscriber(Config config, Registry registry, TickManager tickManager, Publisher publisher) {
        this.config = config;
        this.registry = registry;
        this.tickManager = tickManager;
        this.publisher = publisher;
    }

    public void subscribe() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ZMQ.Context context = ZeromqContext.getContext();
                ZMQ.Socket socket = context.socket(ZMQ.SUB);
                socket.connect("tcp://" + config.getRouterAddress() + ":" + config.getRouterOutcomingPort());
                socket.subscribe("".getBytes());
                while (!Thread.currentThread().isInterrupted()) {
                    byte[] bytes = socket.recv(0);
                    try {
                        Object message = fst.getObjectInput(bytes).readObject();
                        handle(message);
                    } catch (ClassNotFoundException | IOException e) {
                        // ignore
                    }
                }
            }
        });
        thread.setName("subscriber");
        thread.start();
    }

    private void handle(Object object) {
        if (object instanceof SyncMessage) {
            SyncMessage message = (SyncMessage) object;
            logger.debug("Received sync message");
            if (!message.getPlatform().equals(config.getPlatform())) {
                for (Agent agent : message.getAgents()) {
                    registry.addAgent(agent);
                }

                if (message.isNewPlatform()) {
                    SyncMessage syncMessage = new SyncMessage();
                    syncMessage.setPlatform(config.getPlatform());
                    syncMessage.setAgents(registry.getAgentsByPlatform(config.getPlatform()));
                    syncMessage.setTick(tickManager.getCurrent());
                    publisher.publish(syncMessage);
                }
            }
        }
    }
}
