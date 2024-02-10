package urbanjungletech.hardwareservice.model.sensor;

public class MqttSensor extends Sensor{
    private String sensorType;

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }
}
