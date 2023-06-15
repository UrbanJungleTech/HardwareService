package frentz.daniel.hardwareservice.dao.implementation;

import frentz.daniel.hardwareservice.converter.SensorReadingConverter;
import frentz.daniel.hardwareservice.dao.SensorReadingDAO;
import frentz.daniel.hardwareservice.entity.SensorEntity;
import frentz.daniel.hardwareservice.entity.SensorReadingEntity;
import frentz.daniel.hardwareservice.repository.SensorReadingRepository;
import frentz.daniel.hardwareservice.repository.SensorRepository;
import frentz.daniel.hardwareservice.client.model.SensorReading;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
