package frentz.daniel.hardwareservice.event.hardware;

public class HardwareDeleteEvent {
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
