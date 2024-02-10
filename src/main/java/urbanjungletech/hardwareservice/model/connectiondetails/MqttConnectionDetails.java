package urbanjungletech.hardwareservice.model.connectiondetails;

import urbanjungletech.hardwareservice.model.credentials.Credentials;

public class MqttConnectionDetails extends ConnectionDetails{
    private String broker;
    private String clientId;
    private Credentials credentials;

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
