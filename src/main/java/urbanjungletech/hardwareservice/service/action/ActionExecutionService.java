package urbanjungletech.hardwareservice.service.action;

import urbanjungletech.hardwareservice.model.Action;

public interface ActionExecutionService<T extends Action> {
    void execute(T action);
}
