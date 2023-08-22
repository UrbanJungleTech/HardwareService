package urbanjungletech.hardwareservice.entity;

import urbanjungletech.hardwareservice.action.entity.ActionEntity;
import urbanjungletech.hardwareservice.model.AlertType;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class SensorReadingAlertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    private ScheduledSensorReadingEntity scheduledSensorReading;
    @OneToMany(mappedBy = "sensorReadingAlert")
    private List<ActionEntity> actions;

    private AlertType alertType;
    private Double threshold;
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

    public ScheduledSensorReadingEntity getScheduledSensorReading() {
        return scheduledSensorReading;
    }

    public void setScheduledSensorReading(ScheduledSensorReadingEntity scheduledSensorReading) {
        this.scheduledSensorReading = scheduledSensorReading;
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

    public List<ActionEntity> getActions() {
        return actions;
    }

    public void setActions(List<ActionEntity> actions) {
        this.actions = actions;
    }
}
