package frentz.daniel.hardwareservice.model;

public class SensorReadingMessage {
    private String sensorSerialNumber;
    private String[] sensorTypes;

    public String getSensorSerialNumber() {
        return sensorSerialNumber;
    }

    public void setSensorSerialNumber(String sensorSerialNumber) {
        this.sensorSerialNumber = sensorSerialNumber;
    }

    public String[] getSensorTypes() {
        return sensorTypes;
    }

    public void setSensorTypes(String[] sensorTypes) {
        this.sensorTypes = sensorTypes;
    }
}
