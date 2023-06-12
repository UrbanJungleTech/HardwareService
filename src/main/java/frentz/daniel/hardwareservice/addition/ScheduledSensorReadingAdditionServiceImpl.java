package frentz.daniel.hardwareservice.addition;

import frentz.daniel.hardwareservice.converter.ScheduledSensorReadingConverter;
import frentz.daniel.hardwareservice.dao.ScheduledSensorReadingDAO;
import frentz.daniel.hardwareservice.entity.ScheduledSensorReadingEntity;
import frentz.daniel.hardwareservice.schedule.service.SensorScheduleService;
import frentz.daniel.hardwareservice.client.model.ScheduledSensorReading;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduledSensorReadingAdditionServiceImpl implements ScheduledSensorReadingAdditionService{

    private ScheduledSensorReadingDAO scheduledSensorReadingDAO;
    private SensorScheduleService sensorScheduleService;
    private ScheduledSensorReadingConverter scheduledSensorReadingConverter;

    public ScheduledSensorReadingAdditionServiceImpl(ScheduledSensorReadingDAO scheduledSensorReadingDAO,
                                                     SensorScheduleService sensorScheduleService,
                                                     ScheduledSensorReadingConverter scheduledSensorReadingConverter){
        this.scheduledSensorReadingDAO = scheduledSensorReadingDAO;
        this.sensorScheduleService = sensorScheduleService;
        this.scheduledSensorReadingConverter = scheduledSensorReadingConverter;

    }

    @Override
    public ScheduledSensorReading create(ScheduledSensorReading scheduledSensorReading) {
        ScheduledSensorReadingEntity scheduledSensorReadingEntity = this.scheduledSensorReadingDAO.create(scheduledSensorReading);
        this.sensorScheduleService.start(scheduledSensorReadingEntity);
        return this.scheduledSensorReadingConverter.toModel(scheduledSensorReadingEntity);
    }

    @Override
    public void delete(long scheduledSensorReadingId) {

        this.sensorScheduleService.delete(scheduledSensorReadingId);
        this.scheduledSensorReadingDAO.delete(scheduledSensorReadingId);
    }

    @Override
    public ScheduledSensorReading update(long scheduledSensorReadingId,
                                         ScheduledSensorReading scheduledSensorReading) {
        return null;
    }

    @Override
    public List<ScheduledSensorReading> updateList(List<ScheduledSensorReading> models) {
        List<ScheduledSensorReading> result = new ArrayList<>();
        models.forEach((ScheduledSensorReading scheduledSensorReading) -> {
            if(scheduledSensorReading.getId() == null){
                result.add(this.create(scheduledSensorReading));
            }
            else{
                result.add(this.update(scheduledSensorReading.getSensorId(), scheduledSensorReading));
            }
        });
        return result;
    }
}