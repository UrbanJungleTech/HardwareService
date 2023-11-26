package urbanjungletech.hardwareservice.entity.alert.condition;

import jakarta.persistence.Entity;
import urbanjungletech.hardwareservice.model.ONOFF;

@Entity
public class HardwareStateChangeAlertConditionEntity extends AlertConditionEntity{
    private ONOFF state;
    private Long hardwareId;

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
