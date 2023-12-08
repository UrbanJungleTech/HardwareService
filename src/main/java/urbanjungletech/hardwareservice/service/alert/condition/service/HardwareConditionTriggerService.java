package urbanjungletech.hardwareservice.service.alert.condition.service;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.AlertConditionAdditionService;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.model.alert.condition.HardwareStateChangeAlertCondition;
import urbanjungletech.hardwareservice.service.alert.action.ActionExecutionService;
import urbanjungletech.hardwareservice.service.query.AlertQueryService;
import urbanjungletech.hardwareservice.service.query.HardwareStateChangeAlertConditionQueryService;
import urbanjungletech.hardwareservice.service.query.HardwareStateQueryService;

import java.util.List;
import java.util.Map;

@Service
public class HardwareConditionTriggerService implements SpecificConditionTriggerService<HardwareStateChangeAlertCondition> {
    private final HardwareStateQueryService hardwareStateQueryService;
    private final AlertQueryService alertQueryService;
    private final HardwareStateChangeAlertConditionQueryService hardwareStateChangeAlertConditionQueryService;
    private final AlertConditionAdditionService alertConditionAdditionService;
    private final ActionExecutionService actionExecutionService;


    public HardwareConditionTriggerService(HardwareStateQueryService hardwareStateQueryService,
                                           AlertQueryService alertQueryService,
                                           HardwareStateChangeAlertConditionQueryService hardwareStateChangeAlertConditionQueryService,
                                           AlertConditionAdditionService alertConditionAdditionService,
                                           ActionExecutionService actionExecutionService) {
        this.hardwareStateQueryService = hardwareStateQueryService;
        this.alertQueryService = alertQueryService;
        this.hardwareStateChangeAlertConditionQueryService = hardwareStateChangeAlertConditionQueryService;
        this.alertConditionAdditionService = alertConditionAdditionService;
        this.actionExecutionService = actionExecutionService;
    }

    @Override
    public void trigger(Long hardwareStateChangeId) {
        List<HardwareStateChangeAlertCondition> conditions = this.hardwareStateChangeAlertConditionQueryService
                .findByHardwareStateId(hardwareStateChangeId);
        conditions.stream().forEach(condition -> {
            HardwareState hardwareState = this.hardwareStateQueryService.getHardwareStateById(hardwareStateChangeId);
            if (condition.getState() == hardwareState.getState()) {
                condition.setActive(true);
            }
            else{
                condition.setActive(false);
            }
            this.alertConditionAdditionService.update(condition.getId(), condition);
    });
}
}
