package urbanjungletech.hardwareservice.event.sensorreading;

public class SensorReadingCreateEvent {
    private Long sensorId;

    public SensorReadingCreateEvent(Long sensorReadingId) {
        this.sensorId = sensorReadingId;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }
}
