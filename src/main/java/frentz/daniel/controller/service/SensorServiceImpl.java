package frentz.daniel.controller.service;

import frentz.daniel.controller.entity.HardwareControllerEntity;
import frentz.daniel.controller.entity.SensorEntity;
import frentz.daniel.controller.repository.SensorRepository;
import frentz.daniel.controllerclient.model.Sensor;
import frentz.daniel.controllerclient.model.SensorResult;
import frentz.daniel.controllerclient.model.SensorType;
import org.springframework.stereotype.Service;

@Service
public class SensorServiceImpl implements SensorService{
    private SensorRepository sensorRepository;
    private SensorQueueService sensorQueueService;

    public SensorServiceImpl(SensorRepository sensorRepository,
                             SensorQueueService sensorQueueService){
        this.sensorRepository = sensorRepository;
        this.sensorQueueService = sensorQueueService;
    }

    @Override
    public SensorEntity addAndCreateSensor(Sensor sensor, HardwareControllerEntity hardwareControllerEntity) {
        SensorEntity sensorEntity = new SensorEntity();
        sensorEntity.setSensorType(sensor.getSensorTypes());
        sensorEntity.setHardwareController(hardwareControllerEntity);
        sensorEntity.setSerialNumber(sensor.getSerialNumber());
        this.sensorRepository.save(sensorEntity);
        return sensorEntity;
    }

    @Override
    public SensorResult readSensor(long sensorId, SensorType[] sensorTypes) {
        SensorEntity sensorEntity = this.sensorRepository.findById(sensorId).orElseGet(null);
        return this.sensorQueueService.getSensorReading(sensorEntity, sensorTypes);
    }
}
