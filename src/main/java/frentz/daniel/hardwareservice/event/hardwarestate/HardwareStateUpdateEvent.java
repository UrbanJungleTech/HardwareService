package frentz.daniel.hardwareservice.event.hardwarestate;

public class HardwareStateUpdateEvent {
    private long hardwareStateId;

    public HardwareStateUpdateEvent(long hardwareStateId){
        this.hardwareStateId = hardwareStateId;
    }

    public long getHardwareStateId() {
        return hardwareStateId;
    }

    public void setHardwareStateId(long hardwareStateId) {
        this.hardwareStateId = hardwareStateId;
    }
}
