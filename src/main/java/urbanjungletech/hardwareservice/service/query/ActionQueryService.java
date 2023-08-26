package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.Action;

import java.util.List;

public interface ActionQueryService {
    Action getAction(long actionId);
    List<Action> getAllActions();
}
