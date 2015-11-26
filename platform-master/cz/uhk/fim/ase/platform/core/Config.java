package cz.uhk.fim.ase.platform.core;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Config {

    private String platformAddress = "127.0.0.1";
    private String platformPort = "19997";

    private String routerAddress = "127.0.0.1";
    private String routerIncomingPort = "13337";
    private String routerOutcomingPort = "14447";

    private int ioThreads = 4;
    private int computingThreads = 4;

    public String getPlatformAddress() {
        return platformAddress;
    }

    public void setPlatformAddress(String platformAddress) {
        this.platformAddress = platformAddress;
    }

    public String getPlatformPort() {
        return platformPort;
    }

    public void setPlatformPort(String platformPort) {
        this.platformPort = platformPort;
    }

    public String getPlatform() {
        return platformAddress + ":" + platformPort;
    }

    public String getRouterAddress() {
        return routerAddress;
    }

    public void setRouterAddress(String routerAddress) {
        this.routerAddress = routerAddress;
    }

    public String getRouterIncomingPort() {
        return routerIncomingPort;
    }

    public void setRouterIncomingPort(String routerIncomingPort) {
        this.routerIncomingPort = routerIncomingPort;
    }

    public String getRouterOutcomingPort() {
        return routerOutcomingPort;
    }

    public void setRouterOutcomingPort(String routerOutcomingPort) {
        this.routerOutcomingPort = routerOutcomingPort;
    }

    public int getIoThreads() {
        return ioThreads;
    }

    public void setIoThreads(int ioThreads) {
        this.ioThreads = ioThreads;
    }

    public int getComputingThreads() {
        return computingThreads;
    }

    public void setComputingThreads(int computingThreads) {
        this.computingThreads = computingThreads;
    }
}
