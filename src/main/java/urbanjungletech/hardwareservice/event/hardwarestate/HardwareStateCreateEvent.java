package urbanjungletech.hardwareservice.event.hardwarestate;

public class HardwareStateCreateEvent {
    private long hardwareStateId;

    public HardwareStateCreateEvent(long hardwareStateId) {
        this.hardwareStateId = hardwareStateId;
    }

    public long getHardwareStateId() {
        return hardwareStateId;
    }

    public void setHardwareStateId(long hardwareStateId) {
        this.hardwareStateId = hardwareStateId;
    }
}
