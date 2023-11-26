package urbanjungletech.hardwareservice.addition.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urbanjungletech.hardwareservice.addition.ScheduledSensorReadingAdditionService;
import urbanjungletech.hardwareservice.addition.AlertAdditionService;
import urbanjungletech.hardwareservice.converter.ScheduledSensorReadingConverter;
import urbanjungletech.hardwareservice.dao.ScheduledSensorReadingDAO;
import urbanjungletech.hardwareservice.entity.ScheduledSensorReadingEntity;
import urbanjungletech.hardwareservice.event.scheduledreading.ScheduledReadingEventPublisher;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.schedule.sensor.SensorScheduleService;
import urbanjungletech.hardwareservice.service.query.ScheduledSensorReadingQueryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduledSensorReadingAdditionServiceImpl implements ScheduledSensorReadingAdditionService {

    private final ScheduledSensorReadingDAO scheduledSensorReadingDAO;
    private final SensorScheduleService sensorScheduleService;
    private final ScheduledSensorReadingConverter scheduledSensorReadingConverter;
    private final ScheduledReadingEventPublisher scheduledReadingEventPublisher;
    private final AlertAdditionService alertAdditionService;
    private final ScheduledSensorReadingQueryService scheduledSensorReadingQueryService;

    public ScheduledSensorReadingAdditionServiceImpl(ScheduledSensorReadingDAO scheduledSensorReadingDAO,
                                                     SensorScheduleService sensorScheduleService,
                                                     ScheduledSensorReadingConverter scheduledSensorReadingConverter,
                                                     ScheduledReadingEventPublisher scheduledReadingEventPublisher,
                                                     AlertAdditionService alertAdditionService,
                                                     ScheduledSensorReadingQueryService scheduledSensorReadingQueryService){
        this.scheduledSensorReadingDAO = scheduledSensorReadingDAO;
        this.sensorScheduleService = sensorScheduleService;
        this.scheduledSensorReadingConverter = scheduledSensorReadingConverter;
        this.scheduledReadingEventPublisher = scheduledReadingEventPublisher;
        this.alertAdditionService = alertAdditionService;
        this.scheduledSensorReadingQueryService = scheduledSensorReadingQueryService;

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
    public ScheduledSensorReading update(long scheduledSensorReadingId,
                                         ScheduledSensorReading scheduledSensorReading) {
        scheduledSensorReading.setId(scheduledSensorReadingId);
        return this.scheduledSensorReadingQueryService.getScheduledSensorReading(scheduledSensorReadingId);
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
