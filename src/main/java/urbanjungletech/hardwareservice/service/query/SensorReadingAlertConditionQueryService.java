package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;

import java.util.List;

public interface SensorReadingAlertConditionQueryService {
    List<AlertCondition> findSensorReadingAlertConditionBySensorId(Long sensorId);
}
