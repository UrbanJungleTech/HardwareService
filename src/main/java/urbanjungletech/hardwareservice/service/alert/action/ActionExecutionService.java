package urbanjungletech.hardwareservice.service.alert.action;

import urbanjungletech.hardwareservice.model.alert.action.AlertAction;

public interface ActionExecutionService {
    void executeAction(AlertAction action);
}
