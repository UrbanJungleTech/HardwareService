package urbanjungletech.hardwareservice.entity.sensorreadingrouter;

import jakarta.persistence.*;
import urbanjungletech.hardwareservice.entity.ScheduledSensorReadingEntity;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SensorReadingRouterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "scheduled_sensor_reading_id")
    private ScheduledSensorReadingEntity scheduledSensorReadingEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ScheduledSensorReadingEntity getScheduledSensorReadingEntity() {
        return scheduledSensorReadingEntity;
    }

    public void setScheduledSensorReadingEntity(ScheduledSensorReadingEntity scheduledSensorReadingEntity) {
        this.scheduledSensorReadingEntity = scheduledSensorReadingEntity;
    }
}
