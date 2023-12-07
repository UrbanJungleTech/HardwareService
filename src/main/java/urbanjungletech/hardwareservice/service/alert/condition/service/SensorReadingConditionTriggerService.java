package urbanjungletech.hardwareservice.service.alert.condition.service;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.AlertConditionAdditionService;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.model.alert.condition.HardwareStateChangeAlertCondition;
import urbanjungletech.hardwareservice.model.alert.condition.SensorReadingAlertCondition;
import urbanjungletech.hardwareservice.model.alert.condition.ThresholdType;
import urbanjungletech.hardwareservice.service.alert.action.ActionExecutionService;
import urbanjungletech.hardwareservice.service.query.*;

import java.util.List;
import java.util.Map;

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
        conditions.stream().forEach(condition -> {
            if(condition.getThresholdType() == ThresholdType.ABOVE && sensorReading.getReading() > condition.getThreshold()){
                condition.setActive(true);
            }
            else{
                condition.setActive(false);
            }
            this.alertConditionAdditionService.update(condition.getId(), condition);
        });
    }
}
