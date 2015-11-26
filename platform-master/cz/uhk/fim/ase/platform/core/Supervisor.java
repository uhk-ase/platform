package cz.uhk.fim.ase.platform.core;

import cz.uhk.fim.ase.platform.core.internal.NamedThreadFactory;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Supervisor {

    private static Logger logger = LoggerFactory.getLogger(Supervisor.class);

    private ExecutorService executor;

    public void initialize(int threadsCount) {
        executor = Executors.newFixedThreadPool(threadsCount, new NamedThreadFactory("computing"));
    }

    public void addTask(Runnable task) {
        executor.execute(task);
    }

    public void addTasks(List<? extends Runnable> tasks) {
        for (Runnable task : tasks) {
            addTask(task);
        }
    }

    public void block() {
        try {
            executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.warn("Supervisor termination problem", e);
        }
    }
}
