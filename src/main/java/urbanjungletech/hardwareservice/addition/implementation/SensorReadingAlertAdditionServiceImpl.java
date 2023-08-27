package urbanjungletech.hardwareservice.addition.implementation;

import urbanjungletech.hardwareservice.model.Action;
import urbanjungletech.hardwareservice.addition.ActionAdditionService;
import urbanjungletech.hardwareservice.addition.SensorReadingAlertAdditionService;
import urbanjungletech.hardwareservice.converter.SensorReadingAlertConverter;
import urbanjungletech.hardwareservice.dao.SensorReadingAlertDAO;
import urbanjungletech.hardwareservice.entity.SensorReadingAlertEntity;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SensorReadingAlertAdditionServiceImpl implements SensorReadingAlertAdditionService {

    private SensorReadingAlertDAO sensorReadingAlertDAO;
    private SensorReadingAlertConverter sensorReadingAlertConverter;
    private ActionAdditionService actionAdditionService;
    public SensorReadingAlertAdditionServiceImpl(SensorReadingAlertDAO sensorReadingAlertDAO,
                                                  SensorReadingAlertConverter sensorReadingAlertConverter,
                                                  ActionAdditionService actionAdditionService){
        this.sensorReadingAlertDAO = sensorReadingAlertDAO;
        this.sensorReadingAlertConverter = sensorReadingAlertConverter;
        this.actionAdditionService = actionAdditionService;
    }

    @Transactional
    @Override
    public SensorReadingAlert create(SensorReadingAlert sensorReadingAlert) {
        SensorReadingAlertEntity result = this.sensorReadingAlertDAO.createSensorReadingAlert(sensorReadingAlert);
        for(Action action : sensorReadingAlert.getActions()){
            action.setSensorReadingAlertId(result.getId());
            this.actionAdditionService.create(action);
        }
        return this.sensorReadingAlertConverter.toModel(result);
    }

    @Transactional
    @Override
    public void delete(long sensorReadingAlertId) {
        this.sensorReadingAlertDAO.deleteSensorReadingAlert(sensorReadingAlertId);
    }

    @Transactional
    @Override
    public SensorReadingAlert update(long id, SensorReadingAlert sensorReadingAlert) {
        sensorReadingAlert.setId(id);
        SensorReadingAlertEntity result = this.sensorReadingAlertDAO.updateSensorReadingAlert(sensorReadingAlert);
        return this.sensorReadingAlertConverter.toModel(result);
    }

    @Transactional
    @Override
    public List<SensorReadingAlert> updateList(List<SensorReadingAlert> sensorReadingAlerts) {
        List<SensorReadingAlert> result = new ArrayList<>();
        for(SensorReadingAlert sensorReadingAlert : sensorReadingAlerts){
            SensorReadingAlert sensorReadingAlertResult = null;
            if(sensorReadingAlert.getId() == null){
                sensorReadingAlertResult = this.create(sensorReadingAlert);
            } else {
                sensorReadingAlertResult = this.update(sensorReadingAlert.getId(), sensorReadingAlert);
            }
            result.add(sensorReadingAlertResult);
        }
        return result;
    }

    @Transactional
    @Override
    public Action addAction(long sensorReadingAlertId, Action action) {
        action.setSensorReadingAlertId(sensorReadingAlertId);
        Action result = this.actionAdditionService.create(action);
        return result;
    }
}
