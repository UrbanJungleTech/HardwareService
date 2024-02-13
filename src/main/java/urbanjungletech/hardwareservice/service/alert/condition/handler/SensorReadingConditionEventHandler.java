package urbanjungletech.hardwareservice.service.alert.condition.handler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import urbanjungletech.hardwareservice.event.sensorreading.SensorReadingCreateEvent;
import urbanjungletech.hardwareservice.service.alert.condition.service.SensorReadingConditionTriggerService;

@Service
public class SensorReadingConditionEventHandler {

    private final SensorReadingConditionTriggerService sensorReadingConditionTriggerService;

    public SensorReadingConditionEventHandler(SensorReadingConditionTriggerService sensorReadingConditionTriggerService) {
        this.sensorReadingConditionTriggerService = sensorReadingConditionTriggerService;
    }
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSensorReadingEvent(SensorReadingCreateEvent sensorReadingCreateEvent) {
        this.sensorReadingConditionTriggerService.trigger(sensorReadingCreateEvent.getId());
    }
}
