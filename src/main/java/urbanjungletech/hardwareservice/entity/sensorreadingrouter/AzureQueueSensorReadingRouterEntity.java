package urbanjungletech.hardwareservice.entity.sensorreadingrouter;

import jakarta.persistence.*;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.model.credentials.Credentials;

@Entity
public class AzureQueueSensorReadingRouterEntity extends SensorReadingRouterEntity{
    @Id
    private Long id;
    private String queueName;
    @ManyToOne
    private CredentialsEntity credentials;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public CredentialsEntity getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsEntity credentials) {
        this.credentials = credentials;
    }
}
