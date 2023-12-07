package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.alert.AlertConditionsEntity;
import urbanjungletech.hardwareservice.model.alert.AlertConditions;

public interface AlertConditionsDAO {
    AlertConditionsEntity createAlertConditions(AlertConditions alertConditions);
    AlertConditionsEntity update(AlertConditions alertConditions);
    void delete(long id);
}
