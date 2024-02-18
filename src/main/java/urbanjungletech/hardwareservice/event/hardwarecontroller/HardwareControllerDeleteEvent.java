package urbanjungletech.hardwareservice.event.hardwarecontroller;

import urbanjungletech.hardwareservice.event.digitaltwins.DeleteEvent;

public class HardwareControllerDeleteEvent implements DeleteEvent {
    private long hardwareControllerId;

    public HardwareControllerDeleteEvent(long hardwareControllerId) {
        this.hardwareControllerId = hardwareControllerId;
    }

    public long getHardwareControllerId() {
        return hardwareControllerId;
    }

    public void setHardwareControllerId(long hardwareControllerId) {
        this.hardwareControllerId = hardwareControllerId;
    }
}
