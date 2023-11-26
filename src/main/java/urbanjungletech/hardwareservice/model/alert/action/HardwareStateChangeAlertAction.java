package urbanjungletech.hardwareservice.model.alert.action;

import urbanjungletech.hardwareservice.model.ONOFF;

public class HardwareStateChangeAlertAction extends AlertAction {
    private long hardwareId;
    private ONOFF onoff;
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


    public ONOFF getOnoff() {
        return onoff;
    }

    public void setOnoff(ONOFF onoff) {
        this.onoff = onoff;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }
}
