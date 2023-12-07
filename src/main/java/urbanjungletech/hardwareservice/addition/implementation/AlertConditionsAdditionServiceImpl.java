package urbanjungletech.hardwareservice.addition.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.AlertConditionAdditionService;
import urbanjungletech.hardwareservice.addition.AlertConditionsAdditionService;
import urbanjungletech.hardwareservice.converter.alert.AlertConditionsConverter;
import urbanjungletech.hardwareservice.dao.AlertConditionsDAO;
import urbanjungletech.hardwareservice.entity.alert.AlertConditionsEntity;
import urbanjungletech.hardwareservice.model.alert.AlertConditions;

import java.util.List;
import java.util.Optional;

@Service
public class AlertConditionsAdditionServiceImpl implements AlertConditionsAdditionService {
    private final AlertConditionsDAO alertConditionsDAO;
    private final AlertConditionsConverter alertConditionsConverter;
    private final AlertConditionAdditionService alertConditionAdditionService;
    public AlertConditionsAdditionServiceImpl(AlertConditionsDAO alertConditionsDAO,
                                              AlertConditionsConverter alertConditionsConverter,
                                              AlertConditionAdditionService alertConditionAdditionService) {
        this.alertConditionsDAO = alertConditionsDAO;
        this.alertConditionsConverter = alertConditionsConverter;
        this.alertConditionAdditionService = alertConditionAdditionService;
    }
    @Override
    public AlertConditions create(AlertConditions alertConditions) {
        AlertConditionsEntity alertConditionsEntity = this.alertConditionsDAO.createAlertConditions(alertConditions);
        Optional.ofNullable(alertConditions.getConditions()).ifPresent(
                conditions -> conditions.forEach(
                        condition -> {
                            condition.setAlertId(alertConditionsEntity.getAlert().getId());
                            this.alertConditionAdditionService.create(condition);
                        }
                )
        );
        return this.alertConditionsConverter.toModel(alertConditionsEntity);
    }

    @Override
    public void delete(long id) {
        this.alertConditionsDAO.delete(id);
    }

    @Override
    public AlertConditions update(long id, AlertConditions alertConditions) {
        alertConditions.setId(id);
        AlertConditionsEntity alertConditionsEntity = this.alertConditionsDAO.update(alertConditions);
        return this.alertConditionsConverter.toModel(alertConditionsEntity);
    }

    @Override
    public List<AlertConditions> updateList(List<AlertConditions> models) {
        return null;
    }
}
