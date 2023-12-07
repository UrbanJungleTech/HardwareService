package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.alert.AlertConditions;
import urbanjungletech.hardwareservice.repository.AlertConditionsRepository;

public interface AlertConditionsQueryService {
    AlertConditions findByAlertId(Long alertId);
}
