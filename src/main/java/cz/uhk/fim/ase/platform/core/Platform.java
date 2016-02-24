package cz.uhk.fim.ase.platform.core;

import cz.uhk.fim.ase.platform.agents.BuyerAgent;
import cz.uhk.fim.ase.platform.agents.GenericAgent;
import cz.uhk.fim.ase.platform.agents.SellerAgent;
import cz.uhk.fim.ase.platform.communication.broadcast.Publisher;
import cz.uhk.fim.ase.platform.communication.broadcast.Subscriber;
import cz.uhk.fim.ase.platform.communication.broadcast.SyncMessage;
import cz.uhk.fim.ase.platform.communication.direct.Listener;
import cz.uhk.fim.ase.platform.communication.direct.MessageQueue;
import cz.uhk.fim.ase.platform.communication.direct.Sender;
import cz.uhk.fim.ase.platform.communication.internal.ZeromqContext;
import cz.uhk.fim.ase.platform.model.Agent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Platform {

    private static Logger logger = LoggerFactory.getLogger(Platform.class);

    private Config config;
    private Supervisor supervisor;
    private MessageQueue queue;
    private Registry registry;
    private Sender sender;
    private Listener listener;
    private Publisher publisher;
    private Subscriber subscriber;
    private TickManager tickManager;

    public Platform(Config config) {
        this.config = config;
    }

    public Config getConfig() {
        return config;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public MessageQueue getQueue() {
        return queue;
    }

    public Registry getRegistry() {
        return registry;
    }

    public Sender getSender() {
        return sender;
    }

    public Listener getListener() {
        return listener;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public TickManager getTickManager() {
        return tickManager;
    }

    public void start() {
        logger.debug("Starting platform");

        // misc setup
        ZeromqContext.initialize(config.getIoThreads());

        // create services
        tickManager = new TickManager();
        supervisor = new Supervisor();
        supervisor.initialize(config.getComputingThreads());
        queue = new MessageQueue();
        registry = new Registry();
        sender = new Sender(config, queue);
        listener = new Listener(config, queue);
        listener.listen();
        publisher = new Publisher(config.getRouterAddress(), config.getRouterIncomingPort());
        subscriber = new Subscriber(config, registry, tickManager, publisher);
        subscriber.subscribe();

        // create agents
        List<GenericAgent> agents = new ArrayList<>();
        for (int count = 1; count <= 5000; count++) {
            Agent agent = new Agent();
            agent.setId(UUID.randomUUID().toString());
            agent.setPlatform(config.getPlatform());
            agents.add(new BuyerAgent(this, agent));
            registry.addAgent(agent);

            agent = new Agent();
            agent.setId(UUID.randomUUID().toString());
            agent.setPlatform(config.getPlatform());
            agents.add(new SellerAgent(this, agent));
            registry.addAgent(agent);
        }

        // sent sync message
        SyncMessage syncMessage = new SyncMessage();
        syncMessage.setPlatform(config.getPlatform());
        syncMessage.setAgents(registry.getAgentsByPlatform(config.getPlatform()));
        syncMessage.setTick(tickManager.getCurrent());
        syncMessage.setNewPlatform(true);
        publisher.publish(syncMessage);

        // execute agents
        while (!tickManager.isEnd()) {
            long start = System.nanoTime();
            supervisor.addTasks(agents);
            supervisor.block();
            long end = System.nanoTime();
            long time = (end - start) / 1000000;
            long sent = sender.getCounter();
            long received = queue.getCounter();
            long receivedOutside = listener.getCounter();
            logger.info("Tick #" + tickManager.getCurrent() + " is completed, time: " + time +
                    " ms, agents: " + registry.getAgents().size() + ", sent: " + sent + ", received: " + received +
                    " (" + receivedOutside + " from other platform's)");
            sender.resetCounter();
            queue.resetCounter();
            listener.resetCounter();
            tickManager.increaseTick();
        }
        logger.debug("Shutdown");
    }
}
