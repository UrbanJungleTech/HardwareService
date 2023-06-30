package frentz.daniel.hardwareservice.model;

public class ScheduledSensorReadingAlert {
    private Long id;
    private Long scheduledSensorReadingId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScheduledSensorReadingId() {
        return scheduledSensorReadingId;
    }

    public void setScheduledSensorReadingId(Long scheduledSensorReadingId) {
        this.scheduledSensorReadingId = scheduledSensorReadingId;
    }

    private AlertType alertType;
}
