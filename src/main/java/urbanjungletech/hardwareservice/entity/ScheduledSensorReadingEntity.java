package urbanjungletech.hardwareservice.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.SensorReadingRouterEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @OneToMany(mappedBy = "scheduledSensorReadingEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SensorReadingRouterEntity> routers;

    public ScheduledSensorReadingEntity() {
        this.routers = new ArrayList<>();
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

    public List<SensorReadingRouterEntity> getRouters() {
        return routers;
    }

    public void setRouters(List<SensorReadingRouterEntity> routers) {
        this.routers = routers;
    }
}
