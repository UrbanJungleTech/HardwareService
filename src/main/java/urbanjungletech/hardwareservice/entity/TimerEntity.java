package urbanjungletech.hardwareservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Timer")
public class TimerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private ScheduledHardwareEntity onCronJob;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private ScheduledHardwareEntity offCronJob;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "hardware_id")
    private HardwareEntity hardware;

    private Boolean skipNext;

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

    public Boolean getSkipNext() {
        return skipNext;
    }

    public void setSkipNext(Boolean skipNext) {
        this.skipNext = skipNext;
    }
}
