package frentz.daniel.hardwareservice.model;

public class ScheduledHardware {
    private long id;
    private String cronString;
    private HardwareState hardwareState;
    private long hardwareId;
    private Long timerId;

    public HardwareState getHardwareState() {
        return hardwareState;
    }

    public void setHardwareState(HardwareState hardwareState) {
        this.hardwareState = hardwareState;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCronString() {
        return cronString;
    }

    public void setCronString(String cronString) {
        this.cronString = cronString;
    }

    public long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(long hardwareId) {
        this.hardwareId = hardwareId;
    }

    public Long getTimerId() {
        return timerId;
    }

    public void setTimerId(Long timerId) {
        this.timerId = timerId;
    }
}
