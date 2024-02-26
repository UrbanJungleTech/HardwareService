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
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KafkaClientGeneratorImpl implements KafkaClientGenerator {
    private final Map<String, KafkaTemplate> templateCache = new ConcurrentHashMap<>();

    @Override
    public KafkaTemplate generateClient(KafkaConnectionDetails connectionDetails) {
        String url = connectionDetails.getUrl();
        if (templateCache.containsKey(url)) {
            return templateCache.get(url);
        }

        Map<String, Object> producerProperties = new HashMap<>();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(producerProperties);
        KafkaTemplate template = new KafkaTemplate<>(producerFactory);

        templateCache.put(url, template);

        return template;
    }
}
