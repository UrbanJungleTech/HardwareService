package urbanjungletech.hardwareservice.addition.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urbanjungletech.hardwareservice.addition.AlertActionAdditionService;
import urbanjungletech.hardwareservice.addition.AlertAdditionService;
import urbanjungletech.hardwareservice.addition.AlertConditionAdditionService;
import urbanjungletech.hardwareservice.converter.alert.AlertConverter;
import urbanjungletech.hardwareservice.dao.AlertDAO;
import urbanjungletech.hardwareservice.entity.alert.AlertEntity;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlertAdditionServiceImpl implements AlertAdditionService {

    private final AlertDAO alertDAO;
    private final AlertConverter alertConverter;
    private final AlertActionAdditionService alertActionAdditionService;
    private final AlertConditionAdditionService alertConditionAdditionService;
    public AlertAdditionServiceImpl(AlertDAO alertDAO,
                                    AlertConverter alertConverter,
                                    AlertActionAdditionService alertActionAdditionService,
                                    AlertConditionAdditionService alertConditionAdditionService){
        this.alertDAO = alertDAO;
        this.alertConverter = alertConverter;
        this.alertActionAdditionService = alertActionAdditionService;
        this.alertConditionAdditionService = alertConditionAdditionService;
    }

    @Transactional
    @Override
    public Alert create(Alert alert) {
        AlertEntity result = this.alertDAO.createAlert(alert);
        Optional.ofNullable(alert.getActions()).ifPresent((actions) -> {
            actions.forEach((action) -> {
                action.setAlertId(result.getId());
                this.alertActionAdditionService.create(action);
            });
        });
        Optional.ofNullable(alert.getConditions()).ifPresent((conditions) -> {
            conditions.forEach((condition) -> {
                condition.setAlertId(result.getId());
                this.alertConditionAdditionService.create(condition);
            });
        });
        return this.alertConverter.toModel(result);
    }

    @Transactional
    @Override
    public void delete(long sensorReadingAlertId) {
        this.alertDAO.deleteSensorReadingAlert(sensorReadingAlertId);
    }

    @Transactional
    @Override
    public Alert update(long id, Alert alert) {
        alert.setId(id);
        AlertEntity result = this.alertDAO.updateSensorReadingAlert(alert);
        return this.alertConverter.toModel(result);
    }

    @Transactional
    @Override
    public List<Alert> updateList(List<Alert> alerts) {
        List<Alert> result = new ArrayList<>();
        for(Alert alert : alerts){
            Alert alertResult = null;
            if(alert.getId() == null){
                alertResult = this.create(alert);
            } else {
                alertResult = this.update(alert.getId(), alert);
            }
            result.add(alertResult);
        }
        return result;
    }

    @Transactional
    @Override
    public AlertAction addAction(long sensorReadingAlertId, AlertAction alertAction) {
        AlertAction result = this.alertActionAdditionService.create(alertAction);
        return result;
    }
}
