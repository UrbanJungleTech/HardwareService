package urbanjungletech.hardwareservice.model.alert.condition;

public class HardwareStateChangeAlertCondition extends AlertCondition{
    private String state;
    private Long hardwareId;
    public HardwareStateChangeAlertCondition() {
        super();
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HardwareStateChangeAlertCondition)) return false;
        if (!super.equals(o)) return false;

        HardwareStateChangeAlertCondition that = (HardwareStateChangeAlertCondition) o;

        if (getState() != that.getState()) return false;
        return getHardwareId() != null ? getHardwareId().equals(that.getHardwareId()) : that.getHardwareId() == null;
    }

    public int hashCode() {
        int result = super.hashCode();
        result += 31 * result + (getState() != null ? getState().hashCode() : 0);
        result += 31 * result + (getHardwareId() != null ? getHardwareId().hashCode() : 0);
        return result;
    }
}
