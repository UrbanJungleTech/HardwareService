package urbanjungletech.hardwareservice.digitaltwins.service;

import com.azure.digitaltwins.core.DigitalTwinsClient;
import org.hibernate.event.spi.DeleteEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.event.CreateEvent;

import java.util.Map;

@Service
@ConditionalOnProperty(name = "digitaltwins.enabled", havingValue = "true")
public class DigitalTwinsServiceImpl implements DigitalTwinsService {

    private final DigitalTwinsClient digitalTwinsClient;
    private Map<Class<?extends CreateEvent>, SpecificDigitalTwinsService> specificDigitalTwinsServiceMap;

    public DigitalTwinsServiceImpl(Map<Class<?extends CreateEvent>, SpecificDigitalTwinsService> specificDigitalTwinsServiceMap,
                                   DigitalTwinsClient digitalTwinsClient) {
        this.specificDigitalTwinsServiceMap = specificDigitalTwinsServiceMap;
        this.digitalTwinsClient = digitalTwinsClient;
    }

    @Override
    public void createDigitalTwin(CreateEvent createEvent) {
        specificDigitalTwinsServiceMap.get(createEvent.getClass()).createDigitalTwin(createEvent);
    }

    @Override
    public void deleteDigitalTwin(DeleteEvent deleteEvent) {
    }
}
