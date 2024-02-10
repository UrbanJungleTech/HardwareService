package urbanjungletech.hardwareservice.entity.sensorreadingrouter;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import urbanjungletech.hardwareservice.entity.connectiondetails.AzureConnectionDetailsEntity;

@Entity
public class AzureQueueSensorReadingRouterEntity extends SensorReadingRouterEntity{
    private Long id;
    @ManyToOne
    private AzureConnectionDetailsEntity azureConnectionDetails;
    private String queueName;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setAzureConnectionDetails(AzureConnectionDetailsEntity azureConnectionDetails) {
        this.azureConnectionDetails = azureConnectionDetails;
    }

    public AzureConnectionDetailsEntity getAzureConnectionDetails() {
        return azureConnectionDetails;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
