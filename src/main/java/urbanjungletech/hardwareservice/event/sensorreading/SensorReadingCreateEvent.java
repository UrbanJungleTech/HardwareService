package urbanjungletech.hardwareservice.event.sensorreading;

import urbanjungletech.hardwareservice.event.digitaltwins.CreateEvent;

public class SensorReadingCreateEvent implements CreateEvent {
    private Long id;

    public SensorReadingCreateEvent(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
