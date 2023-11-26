package urbanjungletech.hardwareservice.event.sensorreading;

public class SensorReadingCreateEvent {
    private Long sensorReadingId;

    public SensorReadingCreateEvent(Long sensorReadingId) {
        this.sensorReadingId = sensorReadingId;
    }
}
