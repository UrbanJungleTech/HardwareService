package urbanjungletech.hardwareservice.model;

public class Timer {
    private Long id;
    private boolean skipNext;
    private String state;
    private long level;
    private String cronString;
    private long hardwareId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(long hardwareId) {
        this.hardwareId = hardwareId;
    }

    public String getCronString() {
        return cronString;
    }

    public void setCronString(String cronString) {
        this.cronString = cronString;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public boolean isSkipNext() {
        return skipNext;
    }

    public void setSkipNext(boolean skipNext) {
        this.skipNext = skipNext;
    }
}
