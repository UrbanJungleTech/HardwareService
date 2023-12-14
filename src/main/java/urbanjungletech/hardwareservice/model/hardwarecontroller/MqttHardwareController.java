package urbanjungletech.hardwareservice.model.hardwarecontroller;

import urbanjungletech.hardwareservice.model.credentials.Credentials;

public class MqttHardwareController extends HardwareController {
    public MqttHardwareController() {
        super();
    }
    private String brokerUrl;
    private String requestTopic;
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
}
