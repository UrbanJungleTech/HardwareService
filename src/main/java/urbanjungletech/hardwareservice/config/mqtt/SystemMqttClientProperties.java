package urbanjungletech.hardwareservice.config.mqtt;

import java.util.Objects;

public class SystemMqttClientProperties {

    private String server;
    private String queue;

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



    public SystemMqttClientProperties(String server, String queue) {
        this.server = server;
        this.queue = queue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.server);
        hash = 97 * hash + Objects.hashCode(this.queue);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final SystemMqttClientProperties other = (SystemMqttClientProperties) obj;
        if (!Objects.equals(this.server, other.getServer())) {
            return false;
        }
        if (!Objects.equals(this.queue, other.getQueue())) {
            return false;
        }
        return true;
    }
}
