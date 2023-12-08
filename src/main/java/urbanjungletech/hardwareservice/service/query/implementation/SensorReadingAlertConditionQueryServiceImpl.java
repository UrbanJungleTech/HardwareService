package urbanjungletech.hardwareservice.service.query.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.condition.AlertConditionConverter;
import urbanjungletech.hardwareservice.converter.alert.condition.implementation.SensorReadingAlertConditionConverter;
import urbanjungletech.hardwareservice.entity.alert.condition.SensorReadingAlertConditionEntity;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.model.alert.condition.SensorReadingAlertCondition;
import urbanjungletech.hardwareservice.repository.AlertConditionRepository;
import urbanjungletech.hardwareservice.repository.SensorReadingAlertConditionRepository;
import urbanjungletech.hardwareservice.service.query.SensorReadingAlertConditionQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorReadingAlertConditionQueryServiceImpl implements SensorReadingAlertConditionQueryService {
    private final SensorReadingAlertConditionRepository sensorReadingAlertConditionRepository;
    private final SensorReadingAlertConditionConverter sensorReadingAlertConditionConverter;
    public SensorReadingAlertConditionQueryServiceImpl(SensorReadingAlertConditionRepository sensorReadingAlertConditionRepository,
                                                       SensorReadingAlertConditionConverter sensorReadingAlertConditionConverter) {
        this.sensorReadingAlertConditionRepository = sensorReadingAlertConditionRepository;
        this.sensorReadingAlertConditionConverter = sensorReadingAlertConditionConverter;
    }

    @Override
    public List<SensorReadingAlertCondition> findSensorReadingAlertConditionBySensorId(Long sensorId) {
        List<SensorReadingAlertConditionEntity> entities = this.sensorReadingAlertConditionRepository.findBySensorId(sensorId);
        return entities.stream().map(entity -> this.sensorReadingAlertConditionConverter.toModel(entity)).collect(Collectors.toList());
    }
}
