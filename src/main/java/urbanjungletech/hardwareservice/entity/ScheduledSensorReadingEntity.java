package urbanjungletech.hardwareservice.entity;

import jakarta.persistence.*;
import urbanjungletech.hardwareservice.entity.sensor.SensorEntity;

@Entity
@Table(name = "ScheduledSensorReading")
public class ScheduledSensorReadingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cronString;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sensor_id")
    private SensorEntity sensorEntity;

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
}
