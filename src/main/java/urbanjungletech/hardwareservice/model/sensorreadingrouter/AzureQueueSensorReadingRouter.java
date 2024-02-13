package urbanjungletech.hardwareservice.model.sensorreadingrouter;

import urbanjungletech.hardwareservice.model.connectiondetails.AzureConnectionDetails;

public class AzureQueueSensorReadingRouter extends SensorReadingRouter {
    private String queueName;
    private AzureConnectionDetails azureConnectionDetails;

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public AzureConnectionDetails getAzureConnectionDetails() {
        return azureConnectionDetails;
    }

    public void setAzureConnectionDetails(AzureConnectionDetails azureConnectionDetails) {
        this.azureConnectionDetails = azureConnectionDetails;
    }
}
