package urbanjungletech.hardwareservice.digitaltwins.service;

import com.azure.digitaltwins.core.BasicDigitalTwin;
import org.hibernate.event.spi.DeleteEvent;

public interface SpecificDigitalTwinsService<CreateEvent, DeleteEvent>{
    BasicDigitalTwin createDigitalTwin(CreateEvent createEvent);
    void deleteDigitalTwin(DeleteEvent createEvent);
}
