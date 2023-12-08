package urbanjungletech.hardwareservice.entity.alert.condition;

import jakarta.persistence.Entity;

@Entity
public class HardwareStateChangeAlertConditionEntity extends AlertConditionEntity{
    private String state;
    private Long hardwareId;

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
}
