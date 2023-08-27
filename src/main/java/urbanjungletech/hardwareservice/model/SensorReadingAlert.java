package urbanjungletech.hardwareservice.model;

import java.util.ArrayList;
import java.util.List;

public class SensorReadingAlert {
    private Long id;
    private String name;
    private long scheduledSensorReadingId;
    private AlertType alertType;
    private Double threshold;
    private List<Action> actions;

    public SensorReadingAlert() {
        this.actions = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScheduledSensorReadingId() {
        return scheduledSensorReadingId;
    }

    public void setScheduledSensorReadingId(long scheduledSensorReadingId) {
        this.scheduledSensorReadingId = scheduledSensorReadingId;
    }

    public AlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(AlertType alertType) {
        this.alertType = alertType;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
