package urbanjungletech.hardwareservice.model.alert.condition;

import urbanjungletech.hardwareservice.model.ONOFF;

public class HardwareStateChangeAlertCondition extends AlertCondition{
    private ONOFF state;
    private Long hardwareId;
    public HardwareStateChangeAlertCondition() {
        super("hardwareStateChangeAlertCondition");
    }

    public ONOFF getState() {
        return state;
    }

    public void setState(ONOFF state) {
        this.state = state;
    }

    public Long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(Long hardwareId) {
        this.hardwareId = hardwareId;
    }
}
