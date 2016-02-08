package cz.uhk.fim.ase.platform.model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Agent implements Serializable {

    private String id;
    private String platform;
    private Map<String, String> attributes = new HashMap<>();
    //TODO add bude pot�eba je�t� naplnit
    private Inventory inventory;
    private ArrayList<Agent> partners;//
    private Float finenc;
    private String produkt;
    private ArrayList<Integer> dealings;
    private Map<String,Integer> decisionParameter = new HashMap<>();

    
    public ArrayList<Integer> getDealings() {
		return dealings;
	}

	public void setDealings(ArrayList<Integer> dealings) {
		this.dealings = dealings;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public ArrayList<Agent> getPartners() {
		return partners;
	}

	public void setPartners(ArrayList<Agent> partners) {
		this.partners = partners;
	}

	public Float getFinenc() {
		return finenc;
	}

	public void setFinenc(Float finenc) {
		this.finenc = finenc;
	}

	public String getProdukt() {
		return produkt;
	}

	public void setProdukt(String produkt) {
		this.produkt = produkt;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
    
    public Map<String, Integer> getDecisionParameter() {
        return decisionParameter;
    }

    public void setDecisionParameter(Map<String, Integer> decisionParameter) {
        this.decisionParameter = decisionParameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agent agent = (Agent) o;
        return !(id != null ? !id.equals(agent.id) : agent.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
   
}
