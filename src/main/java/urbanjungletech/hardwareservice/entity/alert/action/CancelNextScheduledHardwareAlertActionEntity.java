package urbanjungletech.hardwareservice.entity.alert.action;

public class CancelNextScheduledHardwareAlertActionEntity extends AlertActionEntity{
    private Long scheduledHardwareId;

    public Long getScheduledHardwareId() {
        return scheduledHardwareId;
    }

    public void setScheduledHardwareId(Long scheduledHardwareId) {
        this.scheduledHardwareId = scheduledHardwareId;
    }
}
