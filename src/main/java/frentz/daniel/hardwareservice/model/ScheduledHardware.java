package frentz.daniel.hardwareservice.model;

public class ScheduledHardware {
    private long id;
    private String cronString;
    private ONOFF onoff;
    private long level;
    private long hardwareId;
    private Long timerId;

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

    public ONOFF getOnoff() {
        return onoff;
    }

    public void setOnoff(ONOFF onoff) {
        this.onoff = onoff;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }
}
