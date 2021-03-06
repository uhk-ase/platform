package cz.uhk.fim.ase.platform.core;


import cz.uhk.fim.ase.platform.agents.GenericAgent;
import cz.uhk.fim.ase.platform.agents.R_testAgent;
import cz.uhk.fim.ase.platform.database.DatabaseSQL;
import cz.uhk.fim.ase.platform.communication.broadcast.Publisher;
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
    private TickManager tickManager;
    private DatabaseSQL dbs;

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

    public void start() {
        logger.debug("Starting platform");

        // misc setup
        ZeromqContext.initialize(config.getIoThreads());

        // create services
        supervisor = new Supervisor();
        queue = new MessageQueue();
        registry = new Registry();
        sender = new Sender(config, queue);
        listener = new Listener(queue);
        publisher = new Publisher(config.getRouterAddress(), config.getRouterIncomingPort());
        supervisor.initialize(config.getComputingThreads());
        tickManager = new TickManager();

        // create agents
        List<GenericAgent> agents = new ArrayList<>();
        for (int count = 1; count <= 100; count++) {
            Agent agent = new Agent();
            agent.setId(UUID.randomUUID().toString());
            agent.setPlatform(config.getPlatform());
            agents.add(new R_testAgent(this, agent));
            registry.addAgent(agent);
//
//            agent = new Agent();
//            agent.setId(UUID.randomUUID().toString());
//            agent.setPlatform(config.getPlatform());
//            agents.add(new SellerAgent(this, agent));
//            registry.addAgent(agent);
        }
        
        

        while (!tickManager.isEnd()) {
            logger.debug("Tick #" + tickManager.getCurrent() + "Agents #" + registry.getAgents().size());
            supervisor.addTasks(agents);
            supervisor.block();
            tickManager.increaseTick();
            
            //P�id� ��dek do datab�ze
            dbs.TestRow(tickManager.getCurrent());
            
            
        }

        logger.debug("Shutdown");
        
        //Vyp�e datab�zi
        dbs.TestGet();
    }
}
