package urbanjungletech.hardwareservice.addition.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urbanjungletech.hardwareservice.addition.SensorReadingAdditionService;
import urbanjungletech.hardwareservice.converter.SensorReadingConverter;
import urbanjungletech.hardwareservice.dao.SensorReadingDAO;
import urbanjungletech.hardwareservice.entity.SensorReadingEntity;
import urbanjungletech.hardwareservice.event.sensorreading.SensorReadingEventPublisher;
import urbanjungletech.hardwareservice.model.SensorReading;

import java.util.List;

@Service
public class SensorReadingAdditionServiceImpl implements SensorReadingAdditionService {

    private final SensorReadingDAO sensorReadingDAO;
    private final SensorReadingConverter sensorReadingConverter;
    private final SensorReadingEventPublisher sensorReadingEventPublisher;

    public SensorReadingAdditionServiceImpl(SensorReadingDAO sensorReadingDAO,
                                            SensorReadingConverter sensorReadingConverter,
                                            SensorReadingEventPublisher sensorReadingEventPublisher){
        this.sensorReadingDAO = sensorReadingDAO;
        this.sensorReadingConverter = sensorReadingConverter;
        this.sensorReadingEventPublisher = sensorReadingEventPublisher;
    }

    @Transactional
    @Override
    public SensorReading create(SensorReading sensorReading) {
        SensorReadingEntity result = this.sensorReadingDAO.createAndSave(sensorReading);
        this.sensorReadingEventPublisher.publishSensorReadingCreateEvent(result.getId());
        return this.sensorReadingConverter.toModel(result);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public SensorReading update(long id, SensorReading sensorReading) {
        return null;
    }

    @Override
    public List<SensorReading> updateList(List<SensorReading> models) {
        return null;
    }
}
