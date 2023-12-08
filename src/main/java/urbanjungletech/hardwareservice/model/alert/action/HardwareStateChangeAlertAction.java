package urbanjungletech.hardwareservice.model.alert.action;

public class HardwareStateChangeAlertAction extends AlertAction {
    private long hardwareId;
    private String state;
    private Long level;

    public HardwareStateChangeAlertAction() {
        super("HardwareStateChange");
    }

    public long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(long hardwareId) {
        this.hardwareId = hardwareId;
    }


    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
