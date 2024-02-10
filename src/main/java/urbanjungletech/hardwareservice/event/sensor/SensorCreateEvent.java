package urbanjungletech.hardwareservice.event.sensor;

import urbanjungletech.hardwareservice.event.CreateEvent;

public class SensorCreateEvent implements CreateEvent {
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
