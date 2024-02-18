package urbanjungletech.hardwareservice.event.hardware;

import urbanjungletech.hardwareservice.event.digitaltwins.DeleteEvent;

public class HardwareDeleteEvent implements DeleteEvent {
    private long hardwareId;

    public HardwareDeleteEvent(long hardwareId) {
        this.hardwareId = hardwareId;
    }

    public long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(long hardwareId) {
        this.hardwareId = hardwareId;
    }
}
