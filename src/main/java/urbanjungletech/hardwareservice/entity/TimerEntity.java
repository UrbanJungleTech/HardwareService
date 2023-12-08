package urbanjungletech.hardwareservice.entity;

import jakarta.persistence.*;

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
}
