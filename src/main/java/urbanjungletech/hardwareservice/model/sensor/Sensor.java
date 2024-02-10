package urbanjungletech.hardwareservice.model.sensor;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
public abstract class Sensor {
    private Long id;
    private String port;
    private String name;
    private Long hardwareControllerId;
    private List<ScheduledSensorReading> scheduledSensorReadings;
    private Map<String, String> metadata;
    private Map<String, String> configuration;
    private List<SensorReadingRouter> sensorReadingRouters;

    public Sensor(){
        this.configuration = new HashMap<>();
        this.scheduledSensorReadings = new ArrayList<>();
        this.sensorReadingRouters = new ArrayList<>();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScheduledSensorReading> getScheduledSensorReadings() {
        return scheduledSensorReadings;
    }

    public void setScheduledSensorReadings(List<ScheduledSensorReading> scheduledSensorReadings) {
        this.scheduledSensorReadings = scheduledSensorReadings;
    }

    public Long getHardwareControllerId() {
        return hardwareControllerId;
    }

    public void setHardwareControllerId(Long hardwareControllerId) {
        this.hardwareControllerId = hardwareControllerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<SensorReadingRouter> getSensorReadingRouters() {
        return sensorReadingRouters;
    }

    public void setSensorReadingRouters(List<SensorReadingRouter> sensorReadingRouters) {
        this.sensorReadingRouters = sensorReadingRouters;
    }
}