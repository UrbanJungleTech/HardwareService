package urbanjungletech.hardwareservice.model.sensorreadingrouter;

import urbanjungletech.hardwareservice.model.connectiondetails.MqttConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.UsernamePasswordCredentials;

public class MqttSensorReadingRouter extends SensorReadingRouter {
    private String topic;
    private String clientId;
    private MqttConnectionDetails connectionDetails;

    public MqttSensorReadingRouter(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    public MqttConnectionDetails getConnectionDetails() {
        return connectionDetails;
    }

    public void setConnectionDetails(MqttConnectionDetails connectionDetails) {
        this.connectionDetails = connectionDetails;
    }
}
