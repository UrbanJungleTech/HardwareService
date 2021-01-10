package frentz.daniel.controller.service;

import frentz.daniel.controller.entity.SensorEntity;
import frentz.daniel.controllerclient.model.SensorResult;
import frentz.daniel.controllerclient.model.SensorType;

public interface SensorQueueService {
    void createSensorReadingQueue(String hardwareControllerSerialNumber);

    String getSensorReadingQueue(String hardwareControllerSerialNumber);

    SensorResult getSensorReading(SensorEntity sensorEntity, SensorType[] sensorTypes);
}
