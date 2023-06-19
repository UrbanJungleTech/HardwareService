package frentz.daniel.hardwareservice.event.sensor;

public class SensorCreateEvent {
    private long sensorId;

    public SensorCreateEvent(long sensorId) {
        this.sensorId = sensorId;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }
}
