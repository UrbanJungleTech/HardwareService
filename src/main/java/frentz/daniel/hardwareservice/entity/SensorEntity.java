package frentz.daniel.hardwareservice.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Sensor")
public class SensorEntity {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hardware_id")
    private HardwareControllerEntity hardwareController;
    private String sensorType;
    private long port;
    private String name;
    @OneToMany
    private List<SensorReadingEntity> readings;
    @OneToMany
    private List<ScheduledSensorReadingEntity> scheduledSensorReadings;

    public SensorEntity(){
        this.scheduledSensorReadings = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HardwareControllerEntity getHardwareController() {
        return hardwareController;
    }

    public void setHardwareController(HardwareControllerEntity hardwareController) {
        this.hardwareController = hardwareController;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public long getPort() {
        return port;
    }

    public void setPort(long port) {
        this.port = port;
    }

    public List<SensorReadingEntity> getReadings() {
        return readings;
    }

    public void setReadings(List<SensorReadingEntity> readings) {
        this.readings = readings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScheduledSensorReadingEntity> getScheduledSensorReadings() {
        return scheduledSensorReadings;
    }

    public void setScheduledSensorReadings(List<ScheduledSensorReadingEntity> scheduledSensorReadings) {
        this.scheduledSensorReadings = scheduledSensorReadings;
    }
}
