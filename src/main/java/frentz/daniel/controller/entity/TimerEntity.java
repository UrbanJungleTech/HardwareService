package frentz.daniel.controller.entity;

import frentz.daniel.controllerclient.model.HardwareState;

import javax.persistence.*;

@Entity
public class TimerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long onCronId;
    private Long offCronId;
    @ManyToOne
    @JoinColumn(name = "hardware_id")
    private HardwareEntity hardware;

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

    public Long getOnCronId() {
        return onCronId;
    }

    public void setOnCronId(Long onCronId) {
        this.onCronId = onCronId;
    }

    public Long getOffCronId() {
        return offCronId;
    }

    public void setOffCronId(Long offCronId) {
        this.offCronId = offCronId;
    }
}
