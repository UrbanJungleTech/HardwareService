package urbanjungletech.hardwareservice.entity.hardwarecontroller;

import jakarta.persistence.Entity;

@Entity
public class MqttHardwareControllerEntity extends HardwareControllerEntity {

    private String brokerUrl;
    private String requestTopic;
    private String responseTopic;
    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public void setResponseTopic(String responseTopic) {
        this.responseTopic = responseTopic;
    }

    public String getResponseTopic() {
        return responseTopic;
    }

    public void setRequestTopic(String requestTopic) {
        this.requestTopic = requestTopic;
    }

    public String getRequestTopic() {
        return requestTopic;
    }
}
