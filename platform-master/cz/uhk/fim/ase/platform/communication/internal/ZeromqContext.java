package cz.uhk.fim.ase.platform.communication.internal;

import org.zeromq.ZMQ;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class ZeromqContext {

    private static ZMQ.Context context;

    public static void initialize(int threadCount) {
        context = ZMQ.context(threadCount);
    }

    public static ZMQ.Context getContext() {
        return context;
    }
}
