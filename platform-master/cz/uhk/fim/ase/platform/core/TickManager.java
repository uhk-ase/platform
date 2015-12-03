package cz.uhk.fim.ase.platform.core;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class TickManager {

    private Long current = 1L;
    private Long end = 0L;

    public Long getCurrent() {
        return current;
    }

    public Long getEnd() {
        return end;
    }

    public void increaseTick() {
        current++;
    }

    public boolean isEnd() {
        return end > 0 && current >= end;
    }
}
