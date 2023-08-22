package urbanjungletech.hardwareservice.dao.implementation;

import urbanjungletech.hardwareservice.converter.SensorReadingAlertConverter;
import urbanjungletech.hardwareservice.dao.SensorReadingAlertDAO;
import urbanjungletech.hardwareservice.entity.ScheduledSensorReadingEntity;
import urbanjungletech.hardwareservice.entity.SensorReadingAlertEntity;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;
import urbanjungletech.hardwareservice.repository.ScheduledSensorReadingRepository;
import urbanjungletech.hardwareservice.repository.SensorReadingAlertRepository;
import urbanjungletech.hardwareservice.service.ScheduledSensorReadingService;
import urbanjungletech.hardwareservice.service.exception.ExceptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorReadingAlertDAOImpl implements SensorReadingAlertDAO {

    private SensorReadingAlertRepository sensorReadingAlertRepository;
    private SensorReadingAlertConverter sensorReadingAlertConverter;
    private ExceptionService exceptionService;
    private ScheduledSensorReadingRepository scheduledSensorReadingRepository;

    public SensorReadingAlertDAOImpl(SensorReadingAlertRepository sensorReadingAlertRepository,
                                     SensorReadingAlertConverter sensorReadingAlertConverter,
                                     ExceptionService exceptionService,
                                     ScheduledSensorReadingRepository scheduledSensorReadingRepository){
        this.sensorReadingAlertRepository = sensorReadingAlertRepository;
        this.sensorReadingAlertConverter = sensorReadingAlertConverter;
        this.exceptionService = exceptionService;
        this.scheduledSensorReadingRepository = scheduledSensorReadingRepository;
    }

    @Override
    public SensorReadingAlertEntity createSensorReadingAlert(SensorReadingAlert sensorReadingAlert) {
        SensorReadingAlertEntity sensorReadingAlertEntity = new SensorReadingAlertEntity();
        this.sensorReadingAlertConverter.fillEntity(sensorReadingAlertEntity, sensorReadingAlert);
        SensorReadingAlertEntity result = this.sensorReadingAlertRepository.save(sensorReadingAlertEntity);
        ScheduledSensorReadingEntity scheduledSensorReadingEntity = this.scheduledSensorReadingRepository.findById(sensorReadingAlert.getScheduledSensorReadingId())
                .orElseThrow(() -> this.exceptionService.createNotFoundException(ScheduledSensorReadingService.class, sensorReadingAlert.getScheduledSensorReadingId()));
        result.setScheduledSensorReading(scheduledSensorReadingEntity);
        scheduledSensorReadingEntity.getSensorReadingAlerts().add(result);
        this.scheduledSensorReadingRepository.save(scheduledSensorReadingEntity);
        this.sensorReadingAlertRepository.save(result);
        return result;
    }

    @Override
    public SensorReadingAlertEntity getSensorReadingAlert(long sensorReadingAlertId) {
        SensorReadingAlertEntity result = this.sensorReadingAlertRepository.findById(sensorReadingAlertId)
                .orElseThrow(() -> this.exceptionService.createNotFoundException(SensorReadingAlertEntity.class, sensorReadingAlertId));
        return result;
    }

    @Override
    public List<SensorReadingAlertEntity> getSensorReadingAlerts() {
        return this.sensorReadingAlertRepository.findAll();
    }

    @Override
    public SensorReadingAlertEntity updateSensorReadingAlert(SensorReadingAlert sensorReadingAlert) {
        SensorReadingAlertEntity sensorReadingAlertEntity = this.getSensorReadingAlert(sensorReadingAlert.getId());
        this.sensorReadingAlertConverter.fillEntity(sensorReadingAlertEntity, sensorReadingAlert);
        SensorReadingAlertEntity result = this.sensorReadingAlertRepository.save(sensorReadingAlertEntity);
        return result;
    }

    @Override
    public void deleteSensorReadingAlert(long sensorReadingAlertId) {
        SensorReadingAlertEntity sensorReadingAlertEntity = this.getSensorReadingAlert(sensorReadingAlertId);
        this.sensorReadingAlertRepository.delete(sensorReadingAlertEntity);
    }
}
