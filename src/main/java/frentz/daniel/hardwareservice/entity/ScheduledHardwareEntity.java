package frentz.daniel.hardwareservice.entity;

import frentz.daniel.hardwareservice.model.ONOFF;
import jakarta.persistence.*;

@Entity
@Table(name="ScheduledHardwareJob")
public class ScheduledHardwareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cronString;

    private ONOFF onoff;
    private long level;
    private Long hardwareId;
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


    public TimerEntity getTimerEntity() {
        return timerEntity;
    }

    public void setTimerEntity(TimerEntity timerEntity) {
        this.timerEntity = timerEntity;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public ONOFF getOnoff() {
        return onoff;
    }

    public void setOnoff(ONOFF onoff) {
        this.onoff = onoff;
    }

    public Long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(Long hardwareId) {
        this.hardwareId = hardwareId;
    }
}
