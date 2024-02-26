package urbanjungletech.hardwareservice.model.sensor;

import java.util.Map;

public class MqttSensor extends Sensor{
    private String sensorType;
    private Map<String, String> configuration;

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }
}
