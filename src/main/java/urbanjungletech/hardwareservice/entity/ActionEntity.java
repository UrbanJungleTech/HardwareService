package urbanjungletech.hardwareservice.entity;

import urbanjungletech.hardwareservice.entity.SensorReadingAlertEntity;
import jakarta.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ActionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    @ManyToOne
    private SensorReadingAlertEntity sensorReadingAlert;

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

    public SensorReadingAlertEntity getSensorReadingAlert() {
        return sensorReadingAlert;
    }

    public void setSensorReadingAlert(SensorReadingAlertEntity sensorReadingAlert) {
        this.sensorReadingAlert = sensorReadingAlert;
    }
}
