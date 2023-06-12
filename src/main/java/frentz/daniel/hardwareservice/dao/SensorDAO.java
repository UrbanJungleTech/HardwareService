package frentz.daniel.hardwareservice.dao;

import frentz.daniel.hardwareservice.entity.SensorEntity;
import frentz.daniel.hardwareservice.client.model.Sensor;

import java.util.List;

public interface SensorDAO {
    SensorEntity addSensor(Sensor sensor);
    SensorEntity getSensor(long sensorId);
    void delete(long sensorId);
    SensorEntity getSensorByPort(String serialNumber, int hardwarePort);
    SensorEntity updateSensor(Sensor sensor);
    List<SensorEntity> findByHardwareControllerIdAndSensorType(long hardwareControllerId, String sensorType);

    List<SensorEntity> getSensorsByHardwareControllerId(long hardwareControllerId);
}
