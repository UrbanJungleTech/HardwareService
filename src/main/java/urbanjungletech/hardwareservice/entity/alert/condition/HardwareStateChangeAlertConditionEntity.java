package urbanjungletech.hardwareservice.entity.alert.condition;

import jakarta.persistence.Entity;
import urbanjungletech.hardwareservice.model.ONOFF;

@Entity
public class HardwareStateChangeAlertConditionEntity extends AlertConditionEntity{
    private ONOFF state;
    private Long hardwareStateId;

    public ONOFF getState() {
        return state;
    }

    public void setState(ONOFF state) {
        this.state = state;
    }

    public Long getHardwareStateId() {
        return hardwareStateId;
    }

    public void setHardwareStateId(Long hardwareId) {
        this.hardwareStateId = hardwareId;
    }
}
