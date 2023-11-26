package urbanjungletech.hardwareservice.model;

public class ScheduledHardware {
    private Long id;
    private String cronString;
    private ONOFF onoff;
    private long level;
    private long hardwareId;
    private Long timerId;
    private Boolean skipNext;


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

    public Boolean getSkipNext() {
        return skipNext;
    }

    public void setSkipNext(Boolean skipNext) {
        this.skipNext = skipNext;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
