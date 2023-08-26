package urbanjungletech.hardwareservice.addition.implementation;

import urbanjungletech.hardwareservice.addition.ScheduledSensorReadingAdditionService;
import urbanjungletech.hardwareservice.addition.SensorReadingAlertAdditionService;
import urbanjungletech.hardwareservice.converter.ScheduledSensorReadingConverter;
import urbanjungletech.hardwareservice.dao.ScheduledSensorReadingDAO;
import urbanjungletech.hardwareservice.entity.ScheduledSensorReadingEntity;
import urbanjungletech.hardwareservice.event.scheduledreading.ScheduledReadingEventPublisher;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;
import urbanjungletech.hardwareservice.schedule.sensor.SensorScheduleService;
import urbanjungletech.hardwareservice.service.query.ScheduledSensorReadingQueryService;
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
    private SensorReadingAlertAdditionService sensorReadingAlertAdditionService;
    private ScheduledSensorReadingQueryService scheduledSensorReadingQueryService;

    public ScheduledSensorReadingAdditionServiceImpl(ScheduledSensorReadingDAO scheduledSensorReadingDAO,
                                                     SensorScheduleService sensorScheduleService,
                                                     ScheduledSensorReadingConverter scheduledSensorReadingConverter,
                                                     ScheduledReadingEventPublisher scheduledReadingEventPublisher,
                                                     SensorReadingAlertAdditionService sensorReadingAlertAdditionService,
                                                     ScheduledSensorReadingQueryService scheduledSensorReadingQueryService){
        this.scheduledSensorReadingDAO = scheduledSensorReadingDAO;
        this.sensorScheduleService = sensorScheduleService;
        this.scheduledSensorReadingConverter = scheduledSensorReadingConverter;
        this.scheduledReadingEventPublisher = scheduledReadingEventPublisher;
        this.sensorReadingAlertAdditionService = sensorReadingAlertAdditionService;
        this.scheduledSensorReadingQueryService = scheduledSensorReadingQueryService;

    }

    @Override
    @Transactional
    public ScheduledSensorReading create(ScheduledSensorReading scheduledSensorReading) {
        ScheduledSensorReadingEntity scheduledSensorReadingEntity = this.scheduledSensorReadingDAO.create(scheduledSensorReading);
        Optional.ofNullable(scheduledSensorReading.getSensorReadingAlerts()).ifPresent((sensorReadingAlerts) -> {
                    for (SensorReadingAlert sensorReadingAlert : sensorReadingAlerts){
                        sensorReadingAlert.setScheduledSensorReadingId(scheduledSensorReadingEntity.getId());
                        this.sensorReadingAlertAdditionService.create(sensorReadingAlert);
                    }
                }
        );

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
        ScheduledSensorReadingEntity scheduledSensorReadingEntity = this.scheduledSensorReadingDAO.update(scheduledSensorReading);
        for(SensorReadingAlert sensorReadingAlert : scheduledSensorReading.getSensorReadingAlerts()){
            sensorReadingAlert.setScheduledSensorReadingId(scheduledSensorReadingEntity.getId());
        }
        this.sensorReadingAlertAdditionService.updateList(scheduledSensorReading.getSensorReadingAlerts());
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

    @Transactional
    public SensorReadingAlert addSensorReadingAlert(long scheduledSensorReadingId, SensorReadingAlert sensorReadingAlert){
        sensorReadingAlert.setScheduledSensorReadingId(scheduledSensorReadingId);
        SensorReadingAlert result = this.sensorReadingAlertAdditionService.create(sensorReadingAlert);
        return result;
    }
}
