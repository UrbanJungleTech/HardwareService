package urbanjungletech.hardwareservice.model.sensorreadingrouter;

import urbanjungletech.hardwareservice.model.connectiondetails.KafkaConnectionDetails;

public class KafkaSensorReadingRouter extends SensorReadingRouter {
    private KafkaConnectionDetails kafkaConnectionDetails;

    public KafkaConnectionDetails getKafkaConnectionDetails() {
        return kafkaConnectionDetails;
    }

    public void setKafkaConnectionDetails(KafkaConnectionDetails kafkaConnectionDetails) {
        this.kafkaConnectionDetails = kafkaConnectionDetails;
    }
}
