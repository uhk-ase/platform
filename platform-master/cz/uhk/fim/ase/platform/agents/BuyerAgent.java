package cz.uhk.fim.ase.platform.agents;

import cz.uhk.fim.ase.platform.core.Platform;
import cz.uhk.fim.ase.platform.model.Agent;
import cz.uhk.fim.ase.platform.model.Message;
import java.util.List;
import java.util.Random;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class BuyerAgent extends GenericAgent {

    private int stuff = 0;
    private Random random = new Random();

    public BuyerAgent(Platform platform, Agent identity) {
        super(platform, identity);
        identity.getAttributes().put("buyer", "true");
    }

    @Override
    public void run() {
        // make request
        List<Agent> agents = getRegistry().getAgentsByAttribute("seller");
        Agent seller = agents.get(random.nextInt(agents.size() - 1)); // random agent from list
        Message request = new Message();
        request.setRecipient(seller);
        send(request);

        // process response
        Message response = receive();
        if (response != null) {
            if (response.getBody().equals("here you go")) {
                stuff++;
            }
        }

        // print stuff
        System.out.println("Agent " + getIdentity().getId() + " = " + stuff);
    }
}
