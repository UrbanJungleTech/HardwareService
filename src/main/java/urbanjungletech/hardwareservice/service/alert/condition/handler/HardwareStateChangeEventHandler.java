package urbanjungletech.hardwareservice.service.alert.condition.handler;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import urbanjungletech.hardwareservice.addition.AlertConditionAdditionService;
import urbanjungletech.hardwareservice.entity.alert.condition.HardwareStateChangeAlertConditionEntity;
import urbanjungletech.hardwareservice.event.hardwarestate.HardwareStateUpdateEvent;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.model.alert.condition.HardwareStateChangeAlertCondition;
import urbanjungletech.hardwareservice.repository.HardwareStateChangeAlertConditionRepository;
import urbanjungletech.hardwareservice.service.alert.action.ActionExecutionService;
import urbanjungletech.hardwareservice.service.query.AlertQueryService;
import urbanjungletech.hardwareservice.service.query.HardwareStateChangeAlertConditionQueryService;
import urbanjungletech.hardwareservice.service.query.HardwareStateQueryService;

import java.util.List;
import java.util.Map;

@Service
public class HardwareStateChangeEventHandler {

    private final HardwareStateQueryService hardwareStateQueryService;
    private final AlertQueryService alertQueryService;
    private final HardwareStateChangeAlertConditionQueryService hardwareStateChangeAlertConditionQueryService;
    private final Map<AlertCondition, Alert> alertMap;
    private final AlertConditionAdditionService alertConditionAdditionService;
    private final ActionExecutionService actionExecutionService;

    public HardwareStateChangeEventHandler(HardwareStateChangeAlertConditionQueryService hardwareStateChangeAlertConditionQueryService,
                                           HardwareStateQueryService hardwareStateQueryService,
                                           AlertQueryService alertQueryService,
                                           Map<AlertCondition, Alert> alertMap,
                                           AlertConditionAdditionService alertConditionAdditionService,
                                           ActionExecutionService actionExecutionService) {
        this.hardwareStateChangeAlertConditionQueryService = hardwareStateChangeAlertConditionQueryService;
        this.hardwareStateQueryService = hardwareStateQueryService;
        this.alertQueryService = alertQueryService;
        this.alertMap = alertMap;
        this.alertConditionAdditionService = alertConditionAdditionService;
        this.actionExecutionService = actionExecutionService;
    }
    @Async
    @TransactionalEventListener
    @Transactional
    public void handleCreateEvent(HardwareStateUpdateEvent hardwareStateChangeEvent) {
        List<HardwareStateChangeAlertCondition> conditions = this.hardwareStateChangeAlertConditionQueryService
                .findByHardwareStateId(hardwareStateChangeEvent.getHardwareStateId());
        conditions.stream().forEach(condition -> {
            HardwareState hardwareState = this.hardwareStateQueryService.getHardwareStateById(hardwareStateChangeEvent.getHardwareStateId());
            if(condition.getState() == hardwareState.getState()) {
                Alert alert = this.alertQueryService.getSensorReadingAlert(condition.getAlertId());
                if(alert.getConditions().getInactiveConditions().contains(condition)){
                    alert.getConditions().getInactiveConditions().remove(condition);
                    alert.getConditions().getActiveConditions().add(condition);
                    condition.setActive(true);
                    this.alertConditionAdditionService.update(condition.getId(), condition);
                    if(alert.getConditions().getInactiveConditions().isEmpty()) {
                        alert.getActions().stream().forEach(action -> {
                            this.actionExecutionService.execute(action);
                        });
                    }
                }
            }
        });
    }
}
