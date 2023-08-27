package urbanjungletech.hardwareservice.event.sensor;

public class SensorDeleteEvent {
    private long sensorId;

    public SensorDeleteEvent(long sensorId) {
        this.sensorId = sensorId;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }
}
