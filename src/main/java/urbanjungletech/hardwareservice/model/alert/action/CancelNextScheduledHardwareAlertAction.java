package urbanjungletech.hardwareservice.model.alert.action;

public class CancelNextScheduledHardwareAlertAction extends AlertAction{
    private Long scheduledHardwareId;

    public CancelNextScheduledHardwareAlertAction(String type) {
        super();
    }

    public Long getScheduledHardwareId() {
        return scheduledHardwareId;
    }

    public void setScheduledHardwareId(Long scheduledHardwareId) {
        this.scheduledHardwareId = scheduledHardwareId;
    }
}
