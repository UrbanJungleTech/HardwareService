package urbanjungletech.hardwareservice.addition.implementation;

import urbanjungletech.hardwareservice.addition.ScheduledSensorReadingAdditionService;
import urbanjungletech.hardwareservice.addition.SensorAdditionService;
import urbanjungletech.hardwareservice.converter.SensorConverter;
import urbanjungletech.hardwareservice.dao.SensorDAO;
import urbanjungletech.hardwareservice.entity.SensorEntity;
import urbanjungletech.hardwareservice.event.sensor.SensorEventPublisher;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.Sensor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SensorAdditionServiceImpl implements SensorAdditionService {

    private SensorDAO sensorDAO;
    private SensorConverter sensorConverter;
    private ScheduledSensorReadingAdditionService scheduledSensorReadingAdditionService;
    private SensorEventPublisher sensorEventPublisher;

    public SensorAdditionServiceImpl(SensorDAO sensorDAO,
                                     SensorConverter sensorConverter,
                                     ScheduledSensorReadingAdditionService scheduledSensorReadingAdditionService,
                                     SensorEventPublisher sensorEventPublisher){
        this.sensorDAO = sensorDAO;
        this.sensorConverter = sensorConverter;
        this.scheduledSensorReadingAdditionService = scheduledSensorReadingAdditionService;
        this.sensorEventPublisher = sensorEventPublisher;
    }

    @Transactional
    @Override
    public Sensor create(Sensor sensor) {
        SensorEntity result = this.sensorDAO.addSensor(sensor);
        Optional.ofNullable(sensor.getScheduledSensorReadings())
                .ifPresent(readings -> readings
                        .forEach((ScheduledSensorReading scheduledSensorReading)
                                -> scheduledSensorReading.setSensorId(result.getId())));
        this.scheduledSensorReadingAdditionService.updateList(sensor.getScheduledSensorReadings());
        this.sensorEventPublisher.publishSensorCreateEvent(result.getId());
        return this.sensorConverter.toModel(result);
    }

    @Transactional
    @Override
    public void delete(long sensorId) {
        SensorEntity sensorEntity = this.sensorDAO.getSensor(sensorId);
        this.sensorEventPublisher.publishSensorDeleteEvent(sensorEntity.getId());
        this.sensorDAO.delete(sensorId);
    }

    @Override
    @Transactional
    public Sensor update(long sensorId, Sensor sensor) {
        sensor.setId(sensorId);
        SensorEntity sensorEntity = this.sensorDAO.updateSensor(sensor);
        Optional.ofNullable(sensor.getScheduledSensorReadings())
                .ifPresent(readings -> readings
                        .forEach((ScheduledSensorReading scheduledSensorReading)
                                -> scheduledSensorReading.setSensorId(sensorEntity.getId())));
        this.scheduledSensorReadingAdditionService.updateList(sensor.getScheduledSensorReadings());
        Sensor result = this.sensorConverter.toModel(sensorEntity);
        return result;
    }

    @Override
    @Transactional
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
    @Transactional
    public ScheduledSensorReading addScheduledReading(long sensorId, ScheduledSensorReading scheduledSensorReading) {
        scheduledSensorReading.setSensorId(sensorId);
        return this.scheduledSensorReadingAdditionService.create(scheduledSensorReading);
    }
}