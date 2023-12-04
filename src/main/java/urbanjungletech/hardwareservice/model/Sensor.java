package urbanjungletech.hardwareservice.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sensor {
    private Long id;
    private String sensorType;
    private String port;
    private String name;
    private Long hardwareControllerId;
    private List<ScheduledSensorReading> scheduledSensorReadings;
    private Map<String, String> metadata;
    private Map<String, String> configuration;

    public Sensor(){
        this.configuration = new HashMap<>();
        this.scheduledSensorReadings = new ArrayList<>();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
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
}
