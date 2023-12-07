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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HardwareStateChangeAlertCondition)) return false;
        if (!super.equals(o)) return false;

        HardwareStateChangeAlertCondition that = (HardwareStateChangeAlertCondition) o;

        if (getState() != that.getState()) return false;
        return getHardwareId() != null ? getHardwareId().equals(that.getHardwareId()) : that.getHardwareId() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result += 31 * result + (getState() != null ? getState().hashCode() : 0);
        result += 31 * result + (getHardwareId() != null ? getHardwareId().hashCode() : 0);
        return result;
    }
}
