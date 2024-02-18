package urbanjungletech.hardwareservice.service.clientgenerator.implementation;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.connectiondetails.KafkaConnectionDetails;
import urbanjungletech.hardwareservice.service.clientgenerator.KafkaClientGenerator;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaClientGeneratorImpl implements KafkaClientGenerator {

    @Override
    public KafkaTemplate generateClient(KafkaConnectionDetails connectionDetails) {
        Map<String, Object> producerProperties = new HashMap<>();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, connectionDetails.getUrl());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(producerProperties);
        return new KafkaTemplate<>(producerFactory);
    }
}
