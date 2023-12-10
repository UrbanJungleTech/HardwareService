package urbanjungletech.hardwareservice.service.alert.condition.service;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.AlertConditionAdditionService;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.alert.condition.SensorReadingAlertCondition;
import urbanjungletech.hardwareservice.model.alert.condition.ThresholdType;
import urbanjungletech.hardwareservice.service.query.SensorReadingAlertConditionQueryService;
import urbanjungletech.hardwareservice.service.query.SensorReadingQueryService;

import java.util.List;

@Service
public class SensorReadingConditionTriggerService implements SpecificConditionTriggerService<SensorReadingAlertCondition>{

    private final AlertConditionAdditionService alertConditionAdditionService;
    private final SensorReadingAlertConditionQueryService sensorReadingAlertConditionQueryService;
    private final SensorReadingQueryService sensorReadingQueryService;

    public SensorReadingConditionTriggerService(AlertConditionAdditionService alertConditionAdditionService,
                                                SensorReadingAlertConditionQueryService sensorReadingAlertConditionQueryService,
                                                SensorReadingQueryService sensorReadingQueryService){
        this.alertConditionAdditionService = alertConditionAdditionService;
        this.sensorReadingAlertConditionQueryService = sensorReadingAlertConditionQueryService;
        this.sensorReadingQueryService = sensorReadingQueryService;
    }

    @Override
    public void trigger(Long sensorReadingId) {
        SensorReading sensorReading = this.sensorReadingQueryService.findSensorReadingBySensorId(sensorReadingId);
        List<SensorReadingAlertCondition> conditions = this.sensorReadingAlertConditionQueryService.findSensorReadingAlertConditionBySensorId(sensorReading.getSensorId());
        conditions.forEach(condition -> {
            condition.setActive(condition.getThresholdType() == ThresholdType.ABOVE && sensorReading.getReading() > condition.getThreshold()
            || condition.getThresholdType() == ThresholdType.BELOW && sensorReading.getReading() < condition.getThreshold());
            this.alertConditionAdditionService.update(condition.getId(), condition);
        });
    }
}
