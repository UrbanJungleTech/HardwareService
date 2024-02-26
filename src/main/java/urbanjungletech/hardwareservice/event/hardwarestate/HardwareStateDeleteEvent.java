package urbanjungletech.hardwareservice.event.hardwarestate;

import urbanjungletech.hardwareservice.event.digitaltwins.DeleteEvent;

public class HardwareStateDeleteEvent implements DeleteEvent {
    private long hardwareStateId;

    public HardwareStateDeleteEvent(long hardwareStateId) {
        this.hardwareStateId = hardwareStateId;
    }

    public long getHardwareStateId() {
        return hardwareStateId;
    }

    public void setHardwareStateId(long hardwareStateId) {
        this.hardwareStateId = hardwareStateId;
    }
}
