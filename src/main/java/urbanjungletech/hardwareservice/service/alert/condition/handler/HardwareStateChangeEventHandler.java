package urbanjungletech.hardwareservice.service.alert.condition.handler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import urbanjungletech.hardwareservice.event.hardwarestate.HardwareStateUpdateEvent;
import urbanjungletech.hardwareservice.event.sensorreading.SensorReadingCreateEvent;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.model.alert.condition.HardwareStateChangeAlertCondition;
import urbanjungletech.hardwareservice.model.alert.condition.SensorReadingAlertCondition;
import urbanjungletech.hardwareservice.service.alert.condition.service.HardwareConditionTriggerService;
import urbanjungletech.hardwareservice.service.alert.condition.service.SpecificConditionTriggerService;

import java.util.Map;

@Service
public class HardwareStateChangeEventHandler {

    private final HardwareConditionTriggerService hardwareConditionTriggerService;

    public HardwareStateChangeEventHandler(HardwareConditionTriggerService hardwareConditionTriggerService) {
        this.hardwareConditionTriggerService = hardwareConditionTriggerService;
    }

    @Async
    @TransactionalEventListener
    @Transactional
    public void handleHardwareStateChangeEvent(HardwareStateUpdateEvent hardwareStateChangeEvent) {
        this.hardwareConditionTriggerService.trigger(hardwareStateChangeEvent.getHardwareStateId());
    }
}
