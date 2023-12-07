package urbanjungletech.hardwareservice.service.query.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.model.alert.condition.SensorReadingAlertCondition;
import urbanjungletech.hardwareservice.repository.AlertConditionRepository;
import urbanjungletech.hardwareservice.repository.SensorReadingAlertConditionRepository;
import urbanjungletech.hardwareservice.service.query.SensorReadingAlertConditionQueryService;

import java.util.List;

@Service
public class SensorReadingAlertConditionQueryServiceImpl implements SensorReadingAlertConditionQueryService {
    private final SensorReadingAlertConditionRepository sensorReadingAlertConditionRepository;

    public SensorReadingAlertConditionQueryServiceImpl(SensorReadingAlertConditionRepository sensorReadingAlertConditionRepository) {
        this.sensorReadingAlertConditionRepository = sensorReadingAlertConditionRepository;
    }

    @Override
    public List<SensorReadingAlertCondition> findSensorReadingAlertConditionBySensorId(Long sensorId) {
        return this.sensorReadingAlertConditionRepository.findBySensorId(sensorId);
    }
}
