package urbanjungletech.hardwareservice.service.alert.condition.handler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import urbanjungletech.hardwareservice.event.condition.ConditionActiveEvent;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.service.alert.action.ActionExecutionService;
import urbanjungletech.hardwareservice.service.query.AlertConditionQueryService;
import urbanjungletech.hardwareservice.service.query.AlertQueryService;

@Service
public class ConditionActiveChangeEventHandler {

    private final ActionExecutionService actionExecutionService;
    private final AlertQueryService alertQueryService;
    private final AlertConditionQueryService alertConditionQueryService;
    public ConditionActiveChangeEventHandler(ActionExecutionService actionExecutionService,
                                             AlertQueryService alertQueryService,
                                             AlertConditionQueryService alertConditionQueryService){
        this.actionExecutionService = actionExecutionService;
        this.alertQueryService = alertQueryService;
        this.alertConditionQueryService = alertConditionQueryService;
    }
    @Async
    @TransactionalEventListener
    @Transactional
    public void handleConditionActiveChangeEvent(ConditionActiveEvent conditionActiveEvent){
        AlertCondition alertCondition = this.alertConditionQueryService.getAlertCondition(conditionActiveEvent.getConditionId());
        Alert alert = this.alertQueryService.getSensorReadingAlert(alertCondition.getAlertId());

        if(alert.getConditions().getInactiveConditions().isEmpty()){
            alert.getActions().forEach(this.actionExecutionService::executeAction);
        }
    }
}
