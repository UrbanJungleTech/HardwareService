package urbanjungletech.hardwareservice.entity;

import jakarta.persistence.*;
import urbanjungletech.hardwareservice.entity.hardware.HardwareEntity;

@Entity
public class HardwareStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long level;
    private String state;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private HardwareEntity hardware;
    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HardwareEntity getHardware() {
        return hardware;
    }

    public void setHardware(HardwareEntity hardware) {
        this.hardware = hardware;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
