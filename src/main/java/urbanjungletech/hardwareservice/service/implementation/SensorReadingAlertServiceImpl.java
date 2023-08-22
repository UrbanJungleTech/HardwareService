package urbanjungletech.hardwareservice.service.implementation;

import urbanjungletech.hardwareservice.converter.SensorReadingAlertConverter;
import urbanjungletech.hardwareservice.dao.SensorReadingAlertDAO;
import urbanjungletech.hardwareservice.entity.SensorReadingAlertEntity;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;
import urbanjungletech.hardwareservice.service.SensorReadingAlertService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorReadingAlertServiceImpl implements SensorReadingAlertService {

    private SensorReadingAlertDAO sensorReadingAlertDAO;
    private SensorReadingAlertConverter sensorReadingAlertConverter;

    public SensorReadingAlertServiceImpl(SensorReadingAlertDAO sensorReadingAlertDAO,
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
