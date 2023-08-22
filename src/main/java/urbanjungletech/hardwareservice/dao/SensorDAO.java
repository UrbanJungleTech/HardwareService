package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.SensorEntity;
import urbanjungletech.hardwareservice.model.Sensor;

import java.util.List;

public interface SensorDAO {
    SensorEntity addSensor(Sensor sensor);
    SensorEntity getSensor(long sensorId);
    void delete(long sensorId);
    SensorEntity getSensorByPort(String serialNumber, String hardwarePort);
    SensorEntity updateSensor(Sensor sensor);
    List<SensorEntity> findByHardwareControllerIdAndSensorType(long hardwareControllerId, String sensorType);

    List<SensorEntity> getSensorsByHardwareControllerId(long hardwareControllerId);
}
