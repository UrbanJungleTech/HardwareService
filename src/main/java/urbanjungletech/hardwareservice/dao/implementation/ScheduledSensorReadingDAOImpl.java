package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.ScheduledSensorReadingConverter;
import urbanjungletech.hardwareservice.dao.ScheduledSensorReadingDAO;
import urbanjungletech.hardwareservice.entity.ScheduledSensorReadingEntity;
import urbanjungletech.hardwareservice.entity.SensorEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.repository.ScheduledSensorReadingRepository;
import urbanjungletech.hardwareservice.repository.SensorRepository;

import java.util.List;

@Service
public class ScheduledSensorReadingDAOImpl implements ScheduledSensorReadingDAO {
    private final ScheduledSensorReadingRepository scheduledSensorReadingRepository;
    private final ScheduledSensorReadingConverter scheduledSensorReadingConverter;
    private final SensorRepository sensorRepository;
    private final ExceptionService exceptionService;

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
    public ScheduledSensorReadingEntity getScheduledSensorReading(long scheduledSensorReadingId) {
        return this.scheduledSensorReadingRepository.findById(scheduledSensorReadingId).orElseThrow(
                () -> this.exceptionService.createNotFoundException(ScheduledSensorReadingEntity.class, scheduledSensorReadingId));
    }

    @Override
    public void delete(long scheduledSensorReadingId) {
        ScheduledSensorReadingEntity scheduledSensorReadingEntity = this.scheduledSensorReadingRepository.findById(scheduledSensorReadingId)
                .orElseThrow(
                        () -> this.exceptionService.createNotFoundException(ScheduledSensorReadingEntity.class, scheduledSensorReadingId));
        SensorEntity sensorEntity = scheduledSensorReadingEntity.getSensor();
        sensorEntity.getScheduledSensorReadings().removeIf((scheduledReading -> scheduledReading.getId() == scheduledSensorReadingId));
        sensorRepository.save(sensorEntity);
        this.scheduledSensorReadingRepository.deleteById(scheduledSensorReadingId);
    }

    @Override
    public ScheduledSensorReadingEntity update(ScheduledSensorReading scheduledSensorReading) {
        ScheduledSensorReadingEntity scheduledSensorReadingEntity = this.scheduledSensorReadingRepository.findById(scheduledSensorReading.getId())
                .orElseThrow(
                        () -> this.exceptionService.createNotFoundException(ScheduledSensorReadingEntity.class, scheduledSensorReading.getId()));
        this.scheduledSensorReadingConverter.fillEntity(scheduledSensorReadingEntity, scheduledSensorReading);
        scheduledSensorReadingEntity = this.scheduledSensorReadingRepository.save(scheduledSensorReadingEntity);
        return scheduledSensorReadingEntity;
    }
}
