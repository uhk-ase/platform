package cz.uhk.fim.ase.platform.agents;

import java.util.Random;

import cz.uhk.fim.ase.platform.core.Platform;
import cz.uhk.fim.ase.platform.core.Registry;
import cz.uhk.fim.ase.platform.model.Agent;
import cz.uhk.fim.ase.platform.model.Message;

/**
 * @author TomÃ¡Å¡ Kolinger <tomas@kolinger.name>
 */
public abstract class GenericAgent implements Runnable {

    private Agent identity;
    private Platform platform;
    
    public GenericAgent(Platform platform, Agent identity) {
        this.platform = platform;
        this.identity = identity;
    }

    public Agent getIdentity() {
        return identity;
    }

    public Registry getRegistry() {
        return platform.getRegistry();
    }

    public void send(Message message) {
        if (message.getSender() == null) {
            message.setSender(getIdentity());
        }
        platform.getSender().send(message);
    }

    public Message receive() {
        return platform.getQueue().get(getIdentity());
    }

    public abstract void run();
    
    public Message Shop() {
    	//TODO sjednotit do metody kde se bude jen mìnit produkt, quntity, price, a èasem pøevést do decision
    	Message request = new Message();
		Agent a;
		
		if (identity.getProdukt() == "food" && identity.getInventory().getFood()>1) {
			a=FindAgent("food");
			
			request.setRecipient(a);
			request.setFipa_type("offer");
			request.setBuy_sell("sell");
			request.setProdukt("food");
			request.setQuantity(10);
			request.setPrice(1000);
			request.setMessageID((int)Math.random());
			identity.getDealings().add(request.getMessageID());
			
			return request;
		}
		if (identity.getProdukt() == "painkiller" && identity.getInventory().getPainkiller()>1) {
			a=FindAgent("painkiller");
			
			request.setRecipient(a);
			request.setFipa_type("offer");
			request.setBuy_sell("sell");
			request.setProdukt("painkiller");
			request.setQuantity(10);
			request.setPrice(1000);
			request.setMessageID((int)Math.random());
			identity.getDealings().add(request.getMessageID());
			
			return request;
		}
		if (identity.getProdukt() == "tool" && identity.getInventory().getTool()>1) {
			a=FindAgent("tool");
			
			request.setRecipient(a);
			request.setFipa_type("offer");
			request.setBuy_sell("sell");
			request.setProdukt("tool");
			request.setQuantity(10);
			request.setPrice(1000);
			request.setMessageID((int)Math.random());
			identity.getDealings().add(request.getMessageID());
			
			return request;
		}
		else {
			request.setFipa_type("fail");
		}
		return request;
	}
    
    public Agent FindAgent(String filter) {
    	
		Agent a = null;
		
		if (filter=="food")
		for (Agent agent : identity.getPartners()) {
			if(agent.getInventory().getFood()<=50 && agent.getProdukt()!="food" && agent.getInventory().getFinance()>=1){
				return a;
			}
		}
		if (filter=="painkiller")
			for (Agent agent : identity.getPartners()) {
				if(agent.getInventory().getPainkiller()<=15 && agent.getProdukt()!="painkiller" && agent.getInventory().getFinance()>=10){
					return a;
				}
			}
		if (filter=="tool")
			for (Agent agent : identity.getPartners()) {
				if(agent.getInventory().getTool()<=5 && agent.getProdukt()!="tool" && agent.getInventory().getFinance()>=50){
					return a;
				}
			}
				return a;
	}
    public void Work() {
    	
		if (identity.getProdukt() == "food") {
			if (identity.getInventory().getFood() <= 50)
				identity.getInventory().workFood();
			else
				identity.getInventory().rest();
		}
		if (identity.getProdukt() == "painkiller") {
			if (identity.getInventory().getPainkiller() <= 15)
				identity.getInventory().workPainkiller();
			else
				identity.getInventory().rest();
		}
		if (identity.getProdukt() == "tool") {
			if (identity.getInventory().getTool() <= 5)
				identity.getInventory().workTool();
			else
				identity.getInventory().rest();
		}
	}
    
    public void Repair() {
    	
		if (identity.getInventory().getToolHealth() < 10 && identity.getInventory().getTool() > 0)
			identity.getInventory().useTool();
		else if (identity.getInventory().getHealth() <= 90 && identity.getInventory().getPainkiller() > 0)
			identity.getInventory().usePainkiller();
		else if (identity.getInventory().getHunger() <= 97 && identity.getInventory().getFood() > 0)
			identity.getInventory().useFood();
	}
    
    public Message decision(Message messege) {
    	// rozhodování se èasem rozroste a dáme ho do samostatný tøídy
    	if (messege.getFipa_type() == "offer") {
    		if (messege.getBuy_sell() == "sell") {
    			//decisio if offer accept or not
    			Random r = new Random();
    			//int randomInt = r.nextInt(100) + 1;
				if (itsGoodOffer(messege) == true) {
					switch (messege.getProdukt()) {
					case "food":
						identity.getInventory().buyFood(messege.getQuantity(),
								messege.getPrice());
						messege.setFipa_type("Accept");
						break;
					case "tool":
						identity.getInventory().buyTool(messege.getQuantity(),
								messege.getPrice());
						messege.setFipa_type("Accept");
						break;
					case "painkiler":
						identity.getInventory().buyPainkiller(
								messege.getQuantity(), messege.getPrice());
						messege.setFipa_type("Accept");
						break;

					default:
						messege.setFipa_type("Fail");
						break;
					}
				}
			}else {
				messege.setFipa_type("Refuse");
			}
		}
    	if (messege.getFipa_type() == "Accept") {
			switch (messege.getProdukt()) {
			case "food":
				identity.getInventory().sellFood(messege.getQuantity(), messege.getPrice());
				messege = null;
				break;
			case "tool":
				identity.getInventory().sellTool(messege.getQuantity(), messege.getPrice());
				messege = null;
				break;
			case "painkiler":
				identity.getInventory().sellPainkiller(messege.getQuantity(), messege.getPrice());
				messege = null;
				break;

			default:
				break;
			}
			identity.getDealings().remove(messege.getMessageID());
		}
    	if (messege.getFipa_type() == "Refuse") {
			identity.getDealings().remove(messege.getMessageID());
			lern(false,"sell");
		}
		return messege;
	}

	private void lern(boolean b, String type) {
		if (type == "sell") { //agent sell some thing
			if (b==true) {
				//trade was successful. Agent can rise his price or keep it same. 
				//TODO identity.setAttribute()++
			}
			if (b==false) {
				//TODO identity.setAttribute()--
			}
		}if (type == "buy") {// agent buy some thing
			if (b==true) {
				//trade was successful. Agent can reduce his price or keep it same
				//TODO identity.setAttribute()--
			}
			if (b==false) {
				//TODO identity.setAttribute()++
			}
		}
		
	}

	private boolean itsGoodOffer(Message messageToCompare) {
		
		switch (messageToCompare.getProdukt()) {
		case "food":
			if ((messageToCompare.getPrice()/messageToCompare.getQuantity()) < identity.getDecisionParameter().get("buyParamaterFood"))// full price is divided quantity, from thet we get price from 1 unit 
			{
				return true;
			}else {
				return false;
			}
		case "tool":
			if ((messageToCompare.getPrice()/messageToCompare.getQuantity()) < identity.getDecisionParameter().get("buyParamaterTool")) 
			{
				return true;
			}else {
				return false;
			}
		case "painkiler":
			if ((messageToCompare.getPrice()/messageToCompare.getQuantity()) < identity.getDecisionParameter().get("buyParamaterPainkiler")) 
			{
				return true;
			}else {
				return false;
			}
		default:
			return false;
			
		}
		}
	}
		
	

