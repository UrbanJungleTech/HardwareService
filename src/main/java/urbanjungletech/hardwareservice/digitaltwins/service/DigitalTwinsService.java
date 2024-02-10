package urbanjungletech.hardwareservice.digitaltwins.service;

import org.hibernate.event.spi.DeleteEvent;
import urbanjungletech.hardwareservice.event.CreateEvent;
import urbanjungletech.hardwareservice.event.hardware.HardwareCreateEvent;

public interface DigitalTwinsService {
    void createDigitalTwin(CreateEvent hardwareCreateEvent);
    void deleteDigitalTwin(DeleteEvent deleteEvent);
}
