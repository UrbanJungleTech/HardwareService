package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.converter.SensorReadingAlertConverter;
import urbanjungletech.hardwareservice.dao.SensorReadingAlertDAO;
import urbanjungletech.hardwareservice.entity.SensorReadingAlertEntity;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;
import urbanjungletech.hardwareservice.service.query.SensorReadingAlertQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorReadingAlertQueryServiceImpl implements SensorReadingAlertQueryService {

    private SensorReadingAlertDAO sensorReadingAlertDAO;
    private SensorReadingAlertConverter sensorReadingAlertConverter;

    public SensorReadingAlertQueryServiceImpl(SensorReadingAlertDAO sensorReadingAlertDAO,
                                              SensorReadingAlertConverter sensorReadingAlertConverter){
        this.sensorReadingAlertDAO = sensorReadingAlertDAO;
        this.sensorReadingAlertConverter = sensorReadingAlertConverter;
    }

    @Override
    public SensorReadingAlert getSensorReadingAlert(long id) {
        SensorReadingAlertEntity sensorReadingAlertEntities = this.sensorReadingAlertDAO.getSensorReadingAlert(id);
        return this.sensorReadingAlertConverter.toModel(sensorReadingAlertEntities);
    }

    @Override
    public List<SensorReadingAlert> getAllSensorReadingAlerts() {
        List<SensorReadingAlertEntity> sensorReadingAlertEntities = this.sensorReadingAlertDAO.getSensorReadingAlerts();
        return sensorReadingAlertEntities.stream().map(this.sensorReadingAlertConverter::toModel).toList();
    }
}
