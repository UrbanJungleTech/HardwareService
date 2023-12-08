package urbanjungletech.hardwareservice.model.alert.condition;

public class HardwareStateChangeAlertCondition extends AlertCondition{
    private String state;
    private Long hardwareId;
    public HardwareStateChangeAlertCondition() {
        super("hardwareStateChangeAlertCondition");
    }

    public Long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(Long hardwareId) {
        this.hardwareId = hardwareId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
