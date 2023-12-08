package urbanjungletech.hardwareservice.service.query.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.AlertConditionsConverter;
import urbanjungletech.hardwareservice.entity.alert.AlertConditionsEntity;
import urbanjungletech.hardwareservice.model.alert.AlertConditions;
import urbanjungletech.hardwareservice.repository.AlertConditionsRepository;
import urbanjungletech.hardwareservice.service.query.AlertConditionsQueryService;

@Service
public class AlertConditionsQueryServiceImpl implements AlertConditionsQueryService {

    private final AlertConditionsRepository alertConditionsRepository;
    private final AlertConditionsConverter alertConditionsConverter;
    public AlertConditionsQueryServiceImpl(AlertConditionsRepository alertConditionsRepository,
                                           AlertConditionsConverter alertConditionsConverter) {
        this.alertConditionsRepository = alertConditionsRepository;
        this.alertConditionsConverter = alertConditionsConverter;
    }
    public AlertConditions findByAlertId(Long alertId) {
        AlertConditionsEntity alertConditionsEntity = this.alertConditionsRepository.findByAlertId(alertId);
        return this.alertConditionsConverter.toModel(alertConditionsEntity);
    }
}
