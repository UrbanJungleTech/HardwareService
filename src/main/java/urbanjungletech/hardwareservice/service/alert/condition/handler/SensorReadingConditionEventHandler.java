package urbanjungletech.hardwareservice.service.alert.condition.handler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import urbanjungletech.hardwareservice.event.sensorreading.SensorReadingCreateEvent;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.model.alert.condition.SensorReadingAlertCondition;
import urbanjungletech.hardwareservice.service.alert.condition.service.SensorReadingConditionTriggerService;
import urbanjungletech.hardwareservice.service.alert.condition.service.SpecificConditionTriggerService;

import java.util.Map;

@Service
public class SensorReadingConditionEventHandler {

    private final SensorReadingConditionTriggerService sensorReadingConditionTriggerService;

    public SensorReadingConditionEventHandler(SensorReadingConditionTriggerService sensorReadingConditionTriggerService) {
        this.sensorReadingConditionTriggerService = sensorReadingConditionTriggerService;
    }
    @Async
    @TransactionalEventListener
    @Transactional
    public void handleSensorReadingEvent(SensorReadingCreateEvent sensorReadingCreateEvent) {
        this.sensorReadingConditionTriggerService.trigger(sensorReadingCreateEvent.getSensorId());
    }
}
