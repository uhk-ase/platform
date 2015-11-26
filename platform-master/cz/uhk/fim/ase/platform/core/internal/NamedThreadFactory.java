package cz.uhk.fim.ase.platform.core.internal;

import java.util.concurrent.ThreadFactory;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class NamedThreadFactory implements ThreadFactory {

    private int count = 0;
    private String name;

    public NamedThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, name + (++count));
    }
}
