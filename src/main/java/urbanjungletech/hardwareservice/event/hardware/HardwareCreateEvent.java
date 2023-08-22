package urbanjungletech.hardwareservice.event.hardware;

public class HardwareCreateEvent {
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
