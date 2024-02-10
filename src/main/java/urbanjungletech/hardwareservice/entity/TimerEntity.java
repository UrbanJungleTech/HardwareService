package urbanjungletech.hardwareservice.entity;

import jakarta.persistence.*;
import urbanjungletech.hardwareservice.entity.hardware.HardwareEntity;

@Entity
@Table(name = "Timer")
public class TimerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "hardware_id")
    private HardwareEntity hardware;

    private String cronString;

    private boolean skipNext;
    private long level;
    private String state;

    public TimerEntity() {
        this.skipNext = false;
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


    public String getCronString() {
        return cronString;
    }

    public void setCronString(String cronString) {
        this.cronString = cronString;
    }

    public boolean isSkipNext() {
        return skipNext;
    }

    public void setSkipNext(boolean skipNext) {
        this.skipNext = skipNext;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
