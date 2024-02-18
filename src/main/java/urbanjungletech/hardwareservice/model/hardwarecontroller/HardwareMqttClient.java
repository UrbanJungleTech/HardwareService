package urbanjungletech.hardwareservice.model.hardwarecontroller;

import jakarta.validation.constraints.NotEmpty;
import urbanjungletech.hardwareservice.model.credentials.Credentials;

public class HardwareMqttClient {
    private Long id;
    private Long hardwareControllerId;
    @NotEmpty
    private String brokerUrl;
    @NotEmpty
    private String requestTopic;
    @NotEmpty
    private String responseTopic;
    private Credentials credentials;

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public String getRequestTopic() {
        return requestTopic;
    }

    public void setRequestTopic(String requestTopic) {
        this.requestTopic = requestTopic;
    }

    public String getResponseTopic() {
        return responseTopic;
    }

    public void setResponseTopic(String responseTopic) {
        this.responseTopic = responseTopic;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHardwareControllerId() {
        return hardwareControllerId;
    }

    public void setHardwareControllerId(Long hardwareControllerId) {
        this.hardwareControllerId = hardwareControllerId;
    }
}
