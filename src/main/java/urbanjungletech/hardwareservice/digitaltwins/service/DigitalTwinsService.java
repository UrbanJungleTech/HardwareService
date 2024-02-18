package urbanjungletech.hardwareservice.digitaltwins.service;

import urbanjungletech.hardwareservice.event.digitaltwins.CreateEvent;
import urbanjungletech.hardwareservice.event.digitaltwins.DeleteEvent;

public interface DigitalTwinsService {
    void createDigitalTwin(CreateEvent hardwareCreateEvent);
    void deleteDigitalTwin(DeleteEvent deleteEvent);
}
