package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.action.entity.ActionEntity;
import urbanjungletech.hardwareservice.action.model.Action;

import java.util.List;

public interface ActionDAO {
    ActionEntity create(Action action);
    void deleteAction(long actionId);
    Action updateAction(Action action);

    ActionEntity getAction(long actionId);

    List<ActionEntity> getAllActions();
}
