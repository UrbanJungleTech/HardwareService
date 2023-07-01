package frentz.daniel.hardwareservice.addition.implementation;

import frentz.daniel.hardwareservice.addition.ScheduledSensorReadingAdditionService;
import frentz.daniel.hardwareservice.converter.ScheduledSensorReadingConverter;
import frentz.daniel.hardwareservice.dao.ScheduledSensorReadingDAO;
import frentz.daniel.hardwareservice.entity.ScheduledSensorReadingEntity;
import frentz.daniel.hardwareservice.event.scheduledreading.ScheduledReadingEventPublisher;
import frentz.daniel.hardwareservice.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.schedule.sensor.SensorScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduledSensorReadingAdditionServiceImpl implements ScheduledSensorReadingAdditionService {

    private ScheduledSensorReadingDAO scheduledSensorReadingDAO;
    private SensorScheduleService sensorScheduleService;
    private ScheduledSensorReadingConverter scheduledSensorReadingConverter;
    private ScheduledReadingEventPublisher scheduledReadingEventPublisher;

    public ScheduledSensorReadingAdditionServiceImpl(ScheduledSensorReadingDAO scheduledSensorReadingDAO,
                                                     SensorScheduleService sensorScheduleService,
                                                     ScheduledSensorReadingConverter scheduledSensorReadingConverter,
                                                     ScheduledReadingEventPublisher scheduledReadingEventPublisher){
        this.scheduledSensorReadingDAO = scheduledSensorReadingDAO;
        this.sensorScheduleService = sensorScheduleService;
        this.scheduledSensorReadingConverter = scheduledSensorReadingConverter;
        this.scheduledReadingEventPublisher = scheduledReadingEventPublisher;

    }

    @Override
    @Transactional
    public ScheduledSensorReading create(ScheduledSensorReading scheduledSensorReading) {
        ScheduledSensorReadingEntity scheduledSensorReadingEntity = this.scheduledSensorReadingDAO.create(scheduledSensorReading);
        this.scheduledReadingEventPublisher.publishScheduledReadingCreateEvent(scheduledSensorReadingEntity.getId());
        return this.scheduledSensorReadingConverter.toModel(scheduledSensorReadingEntity);
    }

    @Override
    @Transactional
    public void delete(long scheduledSensorReadingId) {
        this.sensorScheduleService.delete(scheduledSensorReadingId);
        this.scheduledSensorReadingDAO.delete(scheduledSensorReadingId);
    }

    @Override
    //TODO: Implement this method
    public ScheduledSensorReading update(long scheduledSensorReadingId,
                                         ScheduledSensorReading scheduledSensorReading) {
        return null;
    }

    @Override
    @Transactional
    public List<ScheduledSensorReading> updateList(List<ScheduledSensorReading> models) {
        List<ScheduledSensorReading> result = new ArrayList<>();
        Optional.ofNullable(models).ifPresent((Empty) -> models.forEach((ScheduledSensorReading scheduledSensorReading) -> {
            if(scheduledSensorReading.getId() == null){
                result.add(this.create(scheduledSensorReading));
            }
            else{
                result.add(this.update(scheduledSensorReading.getSensorId(), scheduledSensorReading));
            }
        }));
        return result;
    }
}