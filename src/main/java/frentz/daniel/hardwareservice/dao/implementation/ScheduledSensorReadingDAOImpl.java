package frentz.daniel.hardwareservice.dao.implementation;

import frentz.daniel.hardwareservice.converter.ScheduledSensorReadingConverter;
import frentz.daniel.hardwareservice.dao.ScheduledSensorReadingDAO;
import frentz.daniel.hardwareservice.entity.ScheduledSensorReadingEntity;
import frentz.daniel.hardwareservice.exception.StandardNotFoundException;
import frentz.daniel.hardwareservice.repository.ScheduledSensorReadingRepository;
import frentz.daniel.hardwareservice.entity.SensorEntity;
import frentz.daniel.hardwareservice.repository.SensorRepository;
import frentz.daniel.hardwareservice.client.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.service.ExceptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledSensorReadingDAOImpl implements ScheduledSensorReadingDAO {


    private ScheduledSensorReadingRepository scheduledSensorReadingRepository;
    private ScheduledSensorReadingConverter scheduledSensorReadingConverter;
    private SensorRepository sensorRepository;
    private ExceptionService exceptionService;

    public ScheduledSensorReadingDAOImpl(ScheduledSensorReadingRepository scheduledSensorReadingRepository,
                                         ScheduledSensorReadingConverter scheduledSensorReadingConverter,
                                         SensorRepository sensorRepository,
                                         ExceptionService exceptionService){
        this.scheduledSensorReadingRepository = scheduledSensorReadingRepository;
        this.scheduledSensorReadingConverter = scheduledSensorReadingConverter;
        this.sensorRepository = sensorRepository;
        this.exceptionService = exceptionService;
    }

    @Override
    public ScheduledSensorReadingEntity create(ScheduledSensorReading scheduledSensorReading) {
        ScheduledSensorReadingEntity scheduledSensorReadingEntity = new ScheduledSensorReadingEntity();
        SensorEntity sensorEntity = this.sensorRepository.findById(scheduledSensorReading.getSensorId()).orElseThrow(
                () -> this.exceptionService.createNotFoundException(SensorEntity.class, scheduledSensorReading.getSensorId())
        );
        scheduledSensorReadingEntity.setCronString(scheduledSensorReading.getCronString());
        scheduledSensorReadingEntity.setSensor(sensorEntity);
        scheduledSensorReadingEntity = this.scheduledSensorReadingRepository.save(scheduledSensorReadingEntity);
        sensorEntity.getScheduledSensorReadings().add(scheduledSensorReadingEntity);
        this.sensorRepository.save(sensorEntity);
        return scheduledSensorReadingEntity;
    }

    @Override
    public List<ScheduledSensorReadingEntity> getScheduledSensorReadings(){
        List<ScheduledSensorReadingEntity> readings = this.scheduledSensorReadingRepository.findAll();
        return readings;
    }

    @Override
    public void delete(long scheduledSensorReadingId) {
        ScheduledSensorReadingEntity scheduledSensorReadingEntity = this.scheduledSensorReadingRepository.findById(scheduledSensorReadingId).get();
        SensorEntity sensorEntity = scheduledSensorReadingEntity.getSensor();
        sensorEntity.getScheduledSensorReadings().removeIf((scheduledReading -> {
            return scheduledReading.getId() == scheduledSensorReadingId;
        }));
        sensorRepository.save(sensorEntity);
        this.scheduledSensorReadingRepository.deleteById(scheduledSensorReadingId);
    }
}
