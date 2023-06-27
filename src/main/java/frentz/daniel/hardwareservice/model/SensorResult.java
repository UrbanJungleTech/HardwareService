package frentz.daniel.hardwareservice.model;

import java.util.List;

public class SensorResult {
    private List<SensorReading> sensorReadings;

    public List<SensorReading> getSensorReadings() {
        return sensorReadings;
    }

    public void setSensorReadings(List<SensorReading> sensorReadings) {
        this.sensorReadings = sensorReadings;
    }
}
