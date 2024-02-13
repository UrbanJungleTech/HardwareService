package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.alert.condition.SensorReadingAlertCondition;

import java.util.List;

public interface SensorReadingAlertConditionQueryService {
    List<SensorReadingAlertCondition> findSensorReadingAlertConditionBySensorId(Long sensorId);
}
