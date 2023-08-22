package urbanjungletech.hardwareservice.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ScheduledSensorReading")
public class ScheduledSensorReadingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cronString;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sensor_id")
    private SensorEntity sensorEntity;

    @OneToMany(mappedBy = "scheduledSensorReading", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SensorReadingAlertEntity> sensorReadingAlerts;

    public ScheduledSensorReadingEntity() {
        this.sensorReadingAlerts = new ArrayList<>();
    }

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

    public SensorEntity getSensor() {
        return sensorEntity;
    }

    public void setSensor(SensorEntity sensorEntity) {
        this.sensorEntity = sensorEntity;
    }

    public List<SensorReadingAlertEntity> getSensorReadingAlerts() {
        return sensorReadingAlerts;
    }

    public void setSensorReadingAlerts(List<SensorReadingAlertEntity> sensorReadingAlerts) {
        this.sensorReadingAlerts = sensorReadingAlerts;
    }
}
