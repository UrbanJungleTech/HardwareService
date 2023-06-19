package frentz.daniel.hardwareservice.event.hardware;

public class HardwareUpdateStateEvent {
    private long hardwareId;

    public HardwareUpdateStateEvent(long hardwareId) {
        this.hardwareId = hardwareId;
    }

    public long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(long hardwareId) {
        this.hardwareId = hardwareId;
    }
}
