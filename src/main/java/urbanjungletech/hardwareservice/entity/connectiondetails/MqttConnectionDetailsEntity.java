package urbanjungletech.hardwareservice.entity.connectiondetails;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;

@Entity
public class MqttConnectionDetailsEntity extends ConnectionDetailsEntity{
    @ManyToOne
    private CredentialsEntity credentials;
    private String clientId;
    private String Broker;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBroker() {
        return Broker;
    }

    public void setBroker(String broker) {
        Broker = broker;
    }

    public CredentialsEntity getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsEntity credentials) {
        this.credentials = credentials;
    }
}
