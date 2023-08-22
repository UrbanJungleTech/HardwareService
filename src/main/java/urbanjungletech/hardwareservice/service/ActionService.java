package urbanjungletech.hardwareservice.service;

import urbanjungletech.hardwareservice.action.model.Action;

import java.util.List;

public interface ActionService {
    Action getAction(long actionId);
    List<Action> getAllActions();
}
