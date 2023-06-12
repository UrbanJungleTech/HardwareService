package frentz.daniel.hardwareservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Timer")
public class TimerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.REMOVE)
    private ScheduledHardwareEntity onCronJob;
    @OneToOne(cascade = CascadeType.REMOVE)
    private ScheduledHardwareEntity offCronJob;
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

    public ScheduledHardwareEntity getOnCronJob() {
        return onCronJob;
    }

    public void setOnCronJob(ScheduledHardwareEntity onCronJob) {
        this.onCronJob = onCronJob;
    }

    public ScheduledHardwareEntity getOffCronJob() {
        return offCronJob;
    }

    public void setOffCronJob(ScheduledHardwareEntity offCronJob) {
        this.offCronJob = offCronJob;
    }
}