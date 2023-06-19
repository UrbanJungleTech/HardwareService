package frentz.daniel.hardwareservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name="ScheduledHardwareJob")
public class ScheduledHardwareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cronString;
    @Embedded
    private HardwareStateEntity hardwareState;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private TimerEntity timerEntity;

    public String getCronString() {
        return cronString;
    }

    public void setCronString(String cronString) {
        this.cronString = cronString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HardwareStateEntity getHardwareState() {
        return hardwareState;
    }

    public void setHardwareState(HardwareStateEntity hardwareState) {
        this.hardwareState = hardwareState;
    }

    public TimerEntity getTimerEntity() {
        return timerEntity;
    }

    public void setTimerEntity(TimerEntity timerEntity) {
        this.timerEntity = timerEntity;
    }
}
