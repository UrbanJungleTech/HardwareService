package frentz.daniel.hardwareservice.service.implementation;

import frentz.daniel.hardwareservice.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.converter.ScheduledSensorReadingConverter;
import frentz.daniel.hardwareservice.dao.ScheduledSensorReadingDAO;
import frentz.daniel.hardwareservice.service.ScheduledSensorReadingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduledSensorReadingServiceImpl implements ScheduledSensorReadingService {

    private final ScheduledSensorReadingDAO scheduledSensorReadingDAO;
    private final ScheduledSensorReadingConverter scheduledSensorReadingConverter;
    public ScheduledSensorReadingServiceImpl(ScheduledSensorReadingDAO scheduledSensorReadingDAO,
                                             ScheduledSensorReadingConverter scheduledSensorReadingConverter){
        this.scheduledSensorReadingDAO = scheduledSensorReadingDAO;
        this.scheduledSensorReadingConverter = scheduledSensorReadingConverter;
    }
    @Override
    public ScheduledSensorReading getScheduledSensorReading(long scheduledSensorReadingId) {
        this.scheduledSensorReadingDAO.getScheduledSensorReading(scheduledSensorReadingId);
        ScheduledSensorReading scheduledSensorReading = this.scheduledSensorReadingConverter.toModel(
                this.scheduledSensorReadingDAO.getScheduledSensorReading(scheduledSensorReadingId)
        );
        return scheduledSensorReading;
    }

    @Override
    public List<ScheduledSensorReading> getScheduledSensorReadings() {
        return this.scheduledSensorReadingDAO.getScheduledSensorReadings().stream().map(
                (scheduledSensorReadingEntity) -> {
                    return this.scheduledSensorReadingConverter.toModel(scheduledSensorReadingEntity);
                }
        ).collect(Collectors.toList());
    }
}
