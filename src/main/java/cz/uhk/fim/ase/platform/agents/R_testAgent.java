package cz.uhk.fim.ase.platform.agents;

import java.security.Identity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.uhk.fim.ase.platform.communication.direct.MessageQueue;
import cz.uhk.fim.ase.platform.core.Platform;
import cz.uhk.fim.ase.platform.model.Agent;
import cz.uhk.fim.ase.platform.model.Message;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class R_testAgent extends GenericAgent {

	private int stuff = 0;
    private ArrayList<Message> offerList;
    private Boolean haveParners = false;
    private Random random = new Random();

	Message offer_for_me = null;
	Message offer_respond = null;
    
    public R_testAgent(Platform platform, Agent identity) {
        super(platform, identity);
        identity.getAttributes().put("seller", "true");
        identity.getAttributes().put("Finance", "50000");
        //TODO price by agent strategy 
        identity.getDecisionParameter().put("buyParamaterFood",100);//100 per 1 unit
        identity.getDecisionParameter().put("buyParamaterPainkiller",100);//100 per 1 unit
        identity.getDecisionParameter().put("buyParamaterTool",100);//100 per 1 unit

        identity.getDecisionParameter().put("sellParamaterFood",100);//100 per 1 unit
        identity.getDecisionParameter().put("sellParamaterPainkiller",100);//100 per 1 unit
        identity.getDecisionParameter().put("sellParamaterTool",100);//100 per 1 unit
        identity.setPartners(null);
        
        identity.setProdukt(assignsProduct());
    }

    @SuppressWarnings("null")
	@Override
    public void run() {
    	// get agent his partners
    	 while (haveParners == false) {
			assignPartners();
			haveParners = true;
		}
    	 
		//Agent standart work. He made his produkt. Repair him self. And try meke some busnis.
    	Work();
		Repair();
		//create a send message to odher agent
		for (int i = 0; i < 5; i++) {
			Message offer_for_someone = Shop();
			offer_for_someone.setSender(getIdentity());
			getIdentity().getDealings().add(offer_for_someone.getMessageID());
			send(offer_for_someone);	
		}
		// take messege from others
		while ((offer_for_me = receive()) != null) {
			offer_respond = decision(offer_for_me);
			offer_respond.setSender(getIdentity());
			send(offer_respond);
			

			offer_for_me = null;//cleer
			offer_respond = null;//cleer
			
		}
		
    }
}
