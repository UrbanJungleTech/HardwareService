package urbanjungletech.hardwareservice.event.hardwarestate;

import urbanjungletech.hardwareservice.event.digitaltwins.CreateEvent;

public class HardwareStateCreateEvent implements CreateEvent {
    private long hardwareStateId;

    public HardwareStateCreateEvent(long hardwareStateId) {
        this.hardwareStateId = hardwareStateId;
    }

    public long getHardwareStateId() {
        return hardwareStateId;
    }

    public void setHardwareStateId(long hardwareStateId) {
        this.hardwareStateId = hardwareStateId;
    }
}
