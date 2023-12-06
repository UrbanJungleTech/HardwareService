package urbanjungletech.hardwareservice.service.query.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.repository.AlertConditionRepository;
import urbanjungletech.hardwareservice.service.query.SensorReadingAlertConditionQueryService;

import java.util.List;

@Service
public class SensorReadingAlertConditionQueryServiceImpl implements SensorReadingAlertConditionQueryService {
    private final AlertConditionRepository alertConditionRepository;

    public SensorReadingAlertConditionQueryServiceImpl(AlertConditionRepository alertConditionRepository) {
        this.alertConditionRepository = alertConditionRepository;
    }

    @Override
    public List<AlertCondition> findSensorReadingAlertConditionBySensorId(Long sensorId) {
        //return this.alertConditionRepository.findBySensorId(sensorId);
        return null;
    }
}
