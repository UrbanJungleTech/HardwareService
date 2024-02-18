package urbanjungletech.hardwareservice.event.hardwarecontroller;

import urbanjungletech.hardwareservice.event.digitaltwins.CreateEvent;

public class HardwareControllerCreateEvent implements CreateEvent {
    private long id;

    public HardwareControllerCreateEvent(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
