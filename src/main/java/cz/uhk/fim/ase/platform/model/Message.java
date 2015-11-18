package cz.uhk.fim.ase.platform.model;

import java.io.Serializable;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Message implements Serializable {

    private Agent sender;
    private Agent recipient;
    private String body;

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
