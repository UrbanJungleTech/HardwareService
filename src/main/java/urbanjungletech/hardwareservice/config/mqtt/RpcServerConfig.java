package urbanjungletech.hardwareservice.config.mqtt;

public class RpcServerConfig {
    private String uri;
    private String queue;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }
}
