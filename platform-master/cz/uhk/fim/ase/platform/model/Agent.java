package cz.uhk.fim.ase.platform.model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/**
 * @author TomÃ¡Å¡ Kolinger <tomas@kolinger.name>
 */
public class Agent implements Serializable {

    private String id;
    private String platform;
    private Map<String, String> attributes = new HashMap<>();
    //TODO add bude potøeba ještì naplnit
    private Inventory inventory;
    private ArrayList<Agent> partners;//
    private Float finenc;
    private String produkt;

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
