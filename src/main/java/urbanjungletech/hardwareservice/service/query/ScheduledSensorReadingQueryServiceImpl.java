package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.converter.ScheduledSensorReadingConverter;
import urbanjungletech.hardwareservice.dao.ScheduledSensorReadingDAO;
import urbanjungletech.hardwareservice.service.query.ScheduledSensorReadingQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduledSensorReadingQueryServiceImpl implements ScheduledSensorReadingQueryService {

    private final ScheduledSensorReadingDAO scheduledSensorReadingDAO;
    private final ScheduledSensorReadingConverter scheduledSensorReadingConverter;
    public ScheduledSensorReadingQueryServiceImpl(ScheduledSensorReadingDAO scheduledSensorReadingDAO,
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
