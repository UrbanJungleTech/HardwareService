package urbanjungletech.hardwareservice.event.sensor;

import urbanjungletech.hardwareservice.event.digitaltwins.DeleteEvent;

public class SensorDeleteEvent implements DeleteEvent {
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
