package urbanjungletech.hardwareservice.action.service;

import urbanjungletech.hardwareservice.action.model.Action;

public interface ActionExecutionService<T extends Action> {
    void execute(T action);
}
