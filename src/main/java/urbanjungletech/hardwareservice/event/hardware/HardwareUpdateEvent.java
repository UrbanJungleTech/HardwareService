package urbanjungletech.hardwareservice.event.hardware;

public class HardwareUpdateEvent {
    private long hardwareId;

    public HardwareUpdateEvent(long hardwareId) {
        this.hardwareId = hardwareId;
    }

    public long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(long hardwareId) {
        this.hardwareId = hardwareId;
    }
}
