package urbanjungletech.hardwareservice.entity.alert.action;

import jakarta.persistence.Entity;
import jdk.jfr.Enabled;

@Entity
public class CancelNextScheduledHardwareAlertActionEntity extends AlertActionEntity{
    private Long scheduledHardwareId;

    public Long getScheduledHardwareId() {
        return scheduledHardwareId;
    }

    public void setScheduledHardwareId(Long scheduledHardwareId) {
        this.scheduledHardwareId = scheduledHardwareId;
    }
}
