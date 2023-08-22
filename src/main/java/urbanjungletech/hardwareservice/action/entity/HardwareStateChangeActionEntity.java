package urbanjungletech.hardwareservice.action.entity;

import jakarta.persistence.Entity;

@Entity
public class HardwareStateChangeActionEntity extends ActionEntity{
    private Long hardwareId;
//    @OneToOne
//    private HardwareStateEntity hardwareStateEntity;


    public Long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(Long hardwareId) {
        this.hardwareId = hardwareId;
    }
}
