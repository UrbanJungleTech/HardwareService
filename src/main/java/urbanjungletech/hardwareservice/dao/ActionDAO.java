package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.ActionEntity;
import urbanjungletech.hardwareservice.model.Action;

import java.util.List;

public interface ActionDAO {
    ActionEntity create(Action action);
    void deleteAction(long actionId);
    Action updateAction(Action action);

    ActionEntity getAction(long actionId);

    List<ActionEntity> getAllActions();
}
