package urbanjungletech.hardwareservice.model;

public class Timer {
    private Long id;
    private long onLevel;
    private String onCronString;
    private String offCronString;
    private long hardwareId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getOnLevel() {
        return onLevel;
    }

    public void setOnLevel(long onLevel) {
        this.onLevel = onLevel;
    }

    public long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(long hardwareId) {
        this.hardwareId = hardwareId;
    }

    public String getOnCronString() {
        return onCronString;
    }

    public void setOnCronString(String onCronString) {
        this.onCronString = onCronString;
    }

    public String getOffCronString() {
        return offCronString;
    }

    public void setOffCronString(String offCronString) {
        this.offCronString = offCronString;
    }
}
