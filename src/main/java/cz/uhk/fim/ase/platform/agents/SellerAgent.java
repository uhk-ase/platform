package cz.uhk.fim.ase.platform.agents;

import cz.uhk.fim.ase.platform.core.Platform;
import cz.uhk.fim.ase.platform.model.Agent;
import cz.uhk.fim.ase.platform.model.Message;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class SellerAgent extends GenericAgent {

    public SellerAgent(Platform platform, Agent identity) {
        super(platform, identity);
        identity.getAttributes().put("seller", "true");
    }

    @Override
    public void run() {
        Message request = receive();
        if (request != null) {
            Message response = new Message();
            response.setRecipient(request.getSender());
            response.setBody("here you go");
            send(response);
        }
    }
}
