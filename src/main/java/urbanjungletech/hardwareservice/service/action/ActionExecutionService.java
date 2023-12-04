package urbanjungletech.hardwareservice.service.action;

import urbanjungletech.hardwareservice.model.alert.action.AlertAction;

public interface ActionExecutionService {
    void execute(AlertAction action);
}
