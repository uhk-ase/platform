package cz.uhk.fim.ase.platform.communication.broadcast;

import cz.uhk.fim.ase.platform.communication.internal.ZeromqContext;
import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZMQ;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Publisher {

    private static Logger logger = LoggerFactory.getLogger(Publisher.class);

    private FSTConfiguration fst = FSTConfiguration.createDefaultConfiguration();
    private String address;
    private String port;
    private ZMQ.Socket socket;

    public Publisher(String address, String port) {
        this.address = address;
        this.port = port;
    }

    public void publish(Object message) {
        byte[] bytes = fst.asByteArray(message);
        getSocket().send(bytes, 0);
    }

    private ZMQ.Socket getSocket() {
        if (socket == null) {
            ZMQ.Context context = ZeromqContext.getContext();
            socket = context.socket(ZMQ.PUB);
            socket.connect("tcp://" + address + ":" + port);
        }
        return socket;
    }
}
