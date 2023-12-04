package urbanjungletech.hardwareservice.event.hardwarecontroller;

public class HardwareControllerDeleteEvent {
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
