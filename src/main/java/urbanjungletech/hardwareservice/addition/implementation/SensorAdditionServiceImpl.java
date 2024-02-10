package urbanjungletech.hardwareservice.addition.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urbanjungletech.hardwareservice.addition.ScheduledSensorReadingAdditionService;
import urbanjungletech.hardwareservice.addition.SensorAdditionService;
import urbanjungletech.hardwareservice.addition.SensorReadingRouterAdditionService;
import urbanjungletech.hardwareservice.converter.sensor.SensorConverter;
import urbanjungletech.hardwareservice.dao.SensorDAO;
import urbanjungletech.hardwareservice.entity.sensor.SensorEntity;
import urbanjungletech.hardwareservice.event.sensor.SensorEventPublisher;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;
import urbanjungletech.hardwareservice.service.controller.validation.sensor.SensorValidationService;
import urbanjungletech.hardwareservice.service.query.implementation.SensorQueryServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SensorAdditionServiceImpl implements SensorAdditionService {

    private final SensorDAO sensorDAO;
    private final SensorConverter sensorConverter;
    private final ScheduledSensorReadingAdditionService scheduledSensorReadingAdditionService;
    private final SensorEventPublisher sensorEventPublisher;
    private final SensorValidationService sensorValidationService;
    private final SensorQueryServiceImpl sensorQueryService;
    private final SensorReadingRouterAdditionService sensorReadingRouterAdditionService;

    public SensorAdditionServiceImpl(SensorDAO sensorDAO,
                                     SensorConverter sensorConverter,
                                     ScheduledSensorReadingAdditionService scheduledSensorReadingAdditionService,
                                     SensorEventPublisher sensorEventPublisher,
                                     SensorValidationService sensorValidationService,
                                     SensorQueryServiceImpl sensorQueryService,
                                     SensorReadingRouterAdditionService sensorReadingRouterAdditionService){
        this.sensorDAO = sensorDAO;
        this.sensorConverter = sensorConverter;
        this.scheduledSensorReadingAdditionService = scheduledSensorReadingAdditionService;
        this.sensorEventPublisher = sensorEventPublisher;
        this.sensorValidationService = sensorValidationService;
        this.sensorQueryService = sensorQueryService;
        this.sensorReadingRouterAdditionService = sensorReadingRouterAdditionService;
    }

    @Transactional
    @Override
    public Sensor create(Sensor sensor) {
        List<SensorReadingRouter> updatedRouters = this.sensorReadingRouterAdditionService.updateList(sensor.getSensorReadingRouters());
        sensor.setSensorReadingRouters(updatedRouters);
        SensorEntity result = this.sensorDAO.createSensor(sensor);
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
        Sensor sensor = this.sensorQueryService.getSensor(sensorId);
        sensor.getScheduledSensorReadings().forEach((ScheduledSensorReading scheduledSensorReading) -> {
            this.scheduledSensorReadingAdditionService.delete(scheduledSensorReading.getId());
        });
        this.sensorEventPublisher.publishSensorDeleteEvent(sensorId);
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
