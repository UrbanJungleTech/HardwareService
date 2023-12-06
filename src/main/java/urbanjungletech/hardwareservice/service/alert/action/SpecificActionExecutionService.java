package urbanjungletech.hardwareservice.service.alert.action;

import urbanjungletech.hardwareservice.model.alert.action.AlertAction;

public interface SpecificActionExecutionService<T extends AlertAction> {
    void execute(T action);
}
