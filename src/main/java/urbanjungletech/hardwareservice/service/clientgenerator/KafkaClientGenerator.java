package urbanjungletech.hardwareservice.service.clientgenerator;

import org.springframework.kafka.core.KafkaTemplate;
import urbanjungletech.hardwareservice.model.connectiondetails.KafkaConnectionDetails;

public interface KafkaClientGenerator {
    KafkaTemplate generateClient(KafkaConnectionDetails connectionDetails);
}
