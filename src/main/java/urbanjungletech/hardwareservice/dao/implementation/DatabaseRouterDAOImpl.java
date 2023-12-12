package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import urbanjungletech.hardwareservice.converter.SensorReadingConverter;
import urbanjungletech.hardwareservice.dao.DatabaseRouterDAO;
import urbanjungletech.hardwareservice.entity.SensorReadingEntity;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;
import urbanjungletech.hardwareservice.repository.SensorReadingRepository;
import urbanjungletech.hardwareservice.service.credentials.generator.implementation.DatasourceClientGenerator;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabaseRouterDAOImpl implements DatabaseRouterDAO {
    private final SensorReadingConverter sensorReadingConverter;
    private final SensorReadingRepository sensorReadingRepository;
    public DatabaseRouterDAOImpl(SensorReadingConverter sensorReadingConverter,
                                 SensorReadingRepository sensorReadingRepository) {
        this.sensorReadingConverter = sensorReadingConverter;
        this.sensorReadingRepository = sensorReadingRepository;
    }

    @Override
    public SensorReadingEntity create(SensorReading sensorReading) {
        SensorReadingEntity sensorReadingEntity = new SensorReadingEntity();
        this.sensorReadingConverter.fillEntity(sensorReadingEntity, sensorReading);
        SensorReadingEntity result = this.sensorReadingRepository.save(sensorReadingEntity);
        return result;
    }

    @Override
    public List<SensorReading> getAll(DatabaseSensorReadingRouter sensorReadingRouter) {
        List<SensorReadingEntity> sensorReadingEntities = this.sensorReadingRepository.findAll();
        return sensorReadingEntities.stream().map(sensorReadingEntity -> {
            SensorReading sensorReading = new SensorReading();
            this.sensorReadingConverter.toModel(sensorReadingEntity);
            return sensorReading;
        }).collect(Collectors.toList());
    }
}
