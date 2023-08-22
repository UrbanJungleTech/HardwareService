package urbanjungletech.hardwareservice.action.converter;

import urbanjungletech.hardwareservice.action.entity.ActionEntity;
import urbanjungletech.hardwareservice.action.model.Action;

public interface ActionConverter {
    Action toModel(ActionEntity actionEntity);

    void fillEntity(ActionEntity actionEntity, Action action);

    ActionEntity createEntity(Action action);
}
