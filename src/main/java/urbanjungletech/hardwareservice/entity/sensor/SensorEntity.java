package urbanjungletech.hardwareservice.entity.sensor;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import urbanjungletech.hardwareservice.entity.ScheduledSensorReadingEntity;
import urbanjungletech.hardwareservice.entity.SensorReadingEntity;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareControllerEntity;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.SensorReadingRouterEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name="Sensor")
public class SensorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "hardware_id")
    private HardwareControllerEntity hardwareController;
    private String port;
    private String name;
    @ElementCollection
    @CollectionTable(name = "sensor_metadata",
            joinColumns = {@JoinColumn(name = "sensor_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "metadata_data")
    @Column(name = "metadata_value")
    @Fetch(FetchMode.SUBSELECT)
    private Map<String, String> metadata;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sensor_configuration",
            joinColumns = {@JoinColumn(name = "sensor_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "configuration_data")
    @Column(name = "configuration_value")
    @Fetch(FetchMode.SUBSELECT)
    private Map<String, String> configuration;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SensorReadingEntity> readings;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ScheduledSensorReadingEntity> scheduledSensorReadings;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SensorReadingRouterEntity> sensorReadingRouters;

    public SensorEntity(){
        this.scheduledSensorReadings = new ArrayList<>();
        this.sensorReadingRouters = new ArrayList<>();
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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
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

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }

    public List<SensorReadingRouterEntity> getSensorReadingRouters() {
        return sensorReadingRouters;
    }

    public void setSensorReadingRouters(List<SensorReadingRouterEntity> sensorReadingRouters) {
        this.sensorReadingRouters = sensorReadingRouters;
    }
}
