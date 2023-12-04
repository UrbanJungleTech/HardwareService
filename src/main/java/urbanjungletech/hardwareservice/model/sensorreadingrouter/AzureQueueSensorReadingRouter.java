package urbanjungletech.hardwareservice.model.sensorreadingrouter;

import urbanjungletech.hardwareservice.model.credentials.Credentials;

public class AzureQueueSensorReadingRouter extends SensorReadingRouter{
    public AzureQueueSensorReadingRouter() {
        super("azureQueueSensorReadingRouter");
    }

    private String queueName;
    private Credentials credentials;

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
