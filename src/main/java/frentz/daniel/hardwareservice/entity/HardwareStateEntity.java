package frentz.daniel.hardwareservice.entity;

import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.model.ONOFF;

import jakarta.persistence.*;

@Entity
public class HardwareStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long level;
    private ONOFF state;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}) 
    private HardwareEntity hardware;
    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public ONOFF getState() {
        return state;
    }

    public void setState(ONOFF state) {
        this.state = state;
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
}
