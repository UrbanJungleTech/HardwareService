package frentz.daniel.hardwareservice.model;

public class HardwareStateMessage {
    private long port;
    private HardwareState state;
    private String hardwareSerialNumber;
    private long level;

    public long getPort() {
        return port;
    }

    public void setPort(long port) {
        this.port = port;
    }


    public String getHardwareSerialNumber() {
        return hardwareSerialNumber;
    }

    public void setHardwareSerialNumber(String hardwareSerialNumber) {
        this.hardwareSerialNumber = hardwareSerialNumber;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public HardwareState getState() {
        return state;
    }

    public void setState(HardwareState state) {
        this.state = state;
    }
}
