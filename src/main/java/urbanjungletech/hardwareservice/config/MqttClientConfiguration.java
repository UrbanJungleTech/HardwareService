package urbanjungletech.hardwareservice.config;

import java.util.List;

public class MqttClientConfiguration {
    private String server;
    private String queue;
    private String clientId;
    private List<ListenerConfiguration> listeners;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<ListenerConfiguration> getListeners() {
        return listeners;
    }

    public void setListeners(List<ListenerConfiguration> listeners) {
        this.listeners = listeners;
    }
}
