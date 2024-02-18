package urbanjungletech.hardwareservice.event.hardware;

import urbanjungletech.hardwareservice.event.digitaltwins.CreateEvent;

public class HardwareCreateEvent implements CreateEvent {
    private long hardwareId;

    public HardwareCreateEvent(long hardwareId) {
        this.hardwareId = hardwareId;
    }

    public long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(long hardwareId) {
        this.hardwareId = hardwareId;
    }
}
