package frentz.daniel.controller.service;

import frentz.daniel.controller.entity.HardwareControllerEntity;
import frentz.daniel.controller.entity.SensorEntity;
import frentz.daniel.controllerclient.model.Sensor;
import frentz.daniel.controllerclient.model.SensorResult;
import frentz.daniel.controllerclient.model.SensorType;

public interface SensorService {
    SensorEntity addAndCreateSensor(Sensor sensor, HardwareControllerEntity hardwareControllerEntity);
    SensorResult readSensor(long sensorId, SensorType[] sensorTypes);
}
