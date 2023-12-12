package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urbanjungletech.hardwareservice.converter.SensorReadingConverter;
import urbanjungletech.hardwareservice.dao.SensorReadingDAO;
import urbanjungletech.hardwareservice.entity.SensorEntity;
import urbanjungletech.hardwareservice.entity.SensorReadingEntity;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.repository.SensorReadingRepository;
import urbanjungletech.hardwareservice.repository.SensorRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SensorReadingDAOImpl implements SensorReadingDAO {

    private SensorReadingRepository sensorReadingRepository;
    private SensorReadingConverter sensorReadingConverter;
    private SensorRepository sensorRepository;

    public SensorReadingDAOImpl(SensorReadingRepository sensorReadingRepository,
                                SensorReadingConverter sensorReadingConverter,
                                SensorRepository sensorRepository){
        this.sensorReadingRepository = sensorReadingRepository;
        this.sensorReadingConverter = sensorReadingConverter;
        this.sensorRepository = sensorRepository;
    }

    @Override
    public SensorReadingEntity createAndSave(SensorReading sensorReading) {
        SensorReadingEntity sensorReadingEntity = new SensorReadingEntity();
        this.sensorReadingConverter.fillEntity(sensorReadingEntity, sensorReading);
        sensorReadingEntity = sensorReadingRepository.save(sensorReadingEntity);
        SensorEntity sensorEntity = this.sensorRepository.findById(sensorReading.getSensorId()).get();
        sensorEntity.getReadings().add(sensorReadingEntity);
        this.sensorRepository.save(sensorEntity);
        return sensorReadingEntity;
    }

    @Override
    public List<SensorReading> getReadings(long sensorId, LocalDateTime startDate, LocalDateTime endDate) {
        List<SensorReadingEntity> readings = this.sensorReadingRepository.findByReadingTimeBetween(startDate, endDate);
        return this.sensorReadingConverter.toModels(readings);
    }
}
