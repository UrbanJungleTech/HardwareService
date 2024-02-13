package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.alert.AlertConditions;

public interface AlertConditionsQueryService {
    AlertConditions findByAlertId(Long alertId);
}
