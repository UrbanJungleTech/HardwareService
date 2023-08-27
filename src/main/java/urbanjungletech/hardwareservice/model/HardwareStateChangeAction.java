package urbanjungletech.hardwareservice.model;

public class HardwareStateChangeAction extends Action{
    private long hardwareId;
    private HardwareState hardwareState;

    public HardwareStateChangeAction() {
        super("HardwareStateChange");
    }

    public long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(long hardwareId) {
        this.hardwareId = hardwareId;
    }

    public HardwareState getHardwareState() {
        return hardwareState;
    }

    public void setHardwareState(HardwareState hardwareState) {
        this.hardwareState = hardwareState;
    }
}
