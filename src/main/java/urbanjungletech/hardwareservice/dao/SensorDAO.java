package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.sensor.SensorEntity;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

import java.util.List;

public interface SensorDAO {
    SensorEntity createSensor(Sensor sensor);
    SensorEntity getSensor(long sensorId);
    void delete(long sensorId);
    SensorEntity getSensorByPort(String serialNumber, String hardwarePort);
    SensorEntity updateSensor(Sensor sensor);
    List<SensorEntity> getSensorsByHardwareControllerId(long hardwareControllerId);
}
