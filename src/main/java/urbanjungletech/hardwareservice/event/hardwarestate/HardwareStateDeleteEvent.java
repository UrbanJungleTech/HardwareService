package urbanjungletech.hardwareservice.event.hardwarestate;

public class HardwareStateDeleteEvent {
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
