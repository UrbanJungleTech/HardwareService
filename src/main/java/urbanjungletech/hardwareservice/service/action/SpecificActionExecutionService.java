package urbanjungletech.hardwareservice.service.action;

import urbanjungletech.hardwareservice.model.alert.action.AlertAction;

public interface SpecificActionExecutionService<T extends AlertAction> {
    void execute(T action);
}
