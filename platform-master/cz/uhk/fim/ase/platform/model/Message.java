package cz.uhk.fim.ase.platform.model;

import java.io.Serializable;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Message implements Serializable {

    private Agent sender;
    private Agent recipient;
    private String body;
    //add
    private String fipa_type;
    private String buy_sell;
    private String produkt;
    private int quantity;
    private int price;

    public String getFipa_type() {
		return fipa_type;
	}

	public void setFipa_type(String fipa_type) {
		this.fipa_type = fipa_type;
	}

	public String getBuy_sell() {
		return buy_sell;
	}

	public void setBuy_sell(String buy_sell) {
		this.buy_sell = buy_sell;
	}

	public String getProdukt() {
		return produkt;
	}

	public void setProdukt(String produkt) {
		this.produkt = produkt;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Agent getSender() {
        return sender;
    }

    public void setSender(Agent sender) {
        this.sender = sender;
    }

    public Agent getRecipient() {
        return recipient;
    }

    public void setRecipient(Agent recipient) {
        this.recipient = recipient;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
