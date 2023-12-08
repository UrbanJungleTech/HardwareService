package urbanjungletech.hardwareservice.entity.alert.action;

import jakarta.persistence.Entity;

@Entity
public class HardwareStateChangeAlertActionEntity extends AlertActionEntity {
    private Long hardwareId;

    private String state;
    private Long level;


    public Long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(Long hardwareId) {
        this.hardwareId = hardwareId;
    }


    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
