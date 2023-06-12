package frentz.daniel.hardwareservice.addition;

import frentz.daniel.hardwareservice.converter.SensorConverter;
import frentz.daniel.hardwareservice.dao.SensorDAO;
import frentz.daniel.hardwareservice.entity.SensorEntity;
import frentz.daniel.hardwareservice.service.HardwareQueueService;
import frentz.daniel.hardwareservice.client.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.client.model.Sensor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SensorAdditionServiceImpl implements SensorAdditionService{

    private SensorDAO sensorDAO;
    private SensorConverter sensorConverter;
    private ScheduledSensorReadingAdditionService scheduledSensorReadingAdditionService;

    private HardwareQueueService hardwareQueueService;

    public SensorAdditionServiceImpl(SensorDAO sensorDAO,
                                     SensorConverter sensorConverter,
                                     ScheduledSensorReadingAdditionService scheduledSensorReadingAdditionService,
                                     HardwareQueueService hardwareQueueService){
        this.sensorDAO = sensorDAO;
        this.sensorConverter = sensorConverter;
        this.scheduledSensorReadingAdditionService = scheduledSensorReadingAdditionService;
        this.hardwareQueueService = hardwareQueueService;
    }

    @Override
    public Sensor create(Sensor sensor) {
        SensorEntity result = this.sensorDAO.addSensor(sensor);
        sensor.getScheduledSensorReadings().forEach((ScheduledSensorReading scheduledSensorReading) -> {
            scheduledSensorReading.setSensorId(result.getId());
        });
        this.scheduledSensorReadingAdditionService.updateList(sensor.getScheduledSensorReadings());
        this.hardwareQueueService.registerSensor(result);
        return this.sensorConverter.toModel(result);
    }

    @Override
    public void delete(long sensorId) {
        SensorEntity sensorEntity = this.sensorDAO.getSensor(sensorId);
        this.hardwareQueueService.deregisterSensor(sensorEntity);
        this.sensorDAO.delete(sensorId);
    }

    @Override
    public Sensor update(long sensorId, Sensor sensor) {
        sensor.setId(sensorId);
        SensorEntity sensorEntity = this.sensorDAO.updateSensor(sensor);
        this.scheduledSensorReadingAdditionService.updateList(sensor.getScheduledSensorReadings());
        Sensor result = this.sensorConverter.toModel(sensorEntity);
        return result;
    }

    @Override
    public List<Sensor> updateList(List<Sensor> sensors) {
        List<Sensor> results = new ArrayList<>();
        for(Sensor sensor : sensors){
            if(sensor.getId() != null){
                Sensor result = this.update(sensor.getId(), sensor);
                results.add(result);
            }
            else{
                Sensor result = this.create(sensor);
                results.add(result);
            }
        }
        return results;
    }

    @Override
    public ScheduledSensorReading addScheduledReading(long sensorId, ScheduledSensorReading scheduledSensorReading) {
        scheduledSensorReading.setSensorId(sensorId);
        return this.scheduledSensorReadingAdditionService.create(scheduledSensorReading);
    }
}
