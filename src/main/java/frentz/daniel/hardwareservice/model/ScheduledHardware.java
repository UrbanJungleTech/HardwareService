package frentz.daniel.hardwareservice.model;

public class ScheduledHardware {
    private long id;
    private String hardwareControllerSerialNumber;
    private long port;
    private String cronString;
    private HardwareState hardwareState;
    private long hardwareId;

    public long getPort() {
        return port;
    }

    public void setPort(long port) {
        this.port = port;
    }

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

    public String getHardwareControllerSerialNumber() {
        return hardwareControllerSerialNumber;
    }

    public void setHardwareControllerSerialNumber(String hardwareControllerSerialNumber) {
        this.hardwareControllerSerialNumber = hardwareControllerSerialNumber;
    }

    public long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(long hardwareId) {
        this.hardwareId = hardwareId;
    }
}
