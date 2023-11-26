package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.alert.action.AlertAction;

import java.util.List;

public interface ActionQueryService {
    AlertAction getAction(long actionId);
    List<AlertAction> getAllActions();
}
