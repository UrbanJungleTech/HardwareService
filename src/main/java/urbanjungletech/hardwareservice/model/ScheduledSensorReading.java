package urbanjungletech.hardwareservice.model;

import java.util.List;

public class ScheduledSensorReading {
    private Long id;
    private Long sensorId;
    private String cronString;
    private List<SensorReadingAlert> sensorReadingAlerts;

    public String getCronString() {
        return cronString;
    }

    public void setCronString(String cronString) {
        this.cronString = cronString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public List<SensorReadingAlert> getSensorReadingAlerts() {
        return sensorReadingAlerts;
    }

    public void setSensorReadingAlerts(List<SensorReadingAlert> sensorReadingAlerts) {
        this.sensorReadingAlerts = sensorReadingAlerts;
    }
}
