package urbanjungletech.hardwareservice.entity.alert.action;

import jakarta.persistence.Entity;
import urbanjungletech.hardwareservice.model.ONOFF;

@Entity
public class HardwareStateChangeAlertActionEntity extends AlertActionEntity {
    private Long hardwareId;

    private ONOFF onoff;
    private Long level;


    public Long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(Long hardwareId) {
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
