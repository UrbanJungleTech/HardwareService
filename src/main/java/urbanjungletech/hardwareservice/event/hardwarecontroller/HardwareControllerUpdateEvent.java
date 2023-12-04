package urbanjungletech.hardwareservice.event.hardwarecontroller;

public class HardwareControllerUpdateEvent {
    private long hardwareControllerId;

    public HardwareControllerUpdateEvent(long hardwareControllerId) {
        this.hardwareControllerId = hardwareControllerId;
    }

    public long getHardwareControllerId() {
        return hardwareControllerId;
    }

    public void setHardwareControllerId(long hardwareControllerId) {
        this.hardwareControllerId = hardwareControllerId;
    }
}
