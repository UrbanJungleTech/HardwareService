package urbanjungletech.hardwareservice.event.sensor;

public class SensorUpdateEvent {
    private long sensorId;

    public SensorUpdateEvent(long sensorId) {
        this.sensorId = sensorId;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }
}
