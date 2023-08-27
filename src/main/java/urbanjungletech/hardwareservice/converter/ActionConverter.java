package urbanjungletech.hardwareservice.converter;

import urbanjungletech.hardwareservice.entity.ActionEntity;
import urbanjungletech.hardwareservice.model.Action;

public interface ActionConverter {
    Action toModel(ActionEntity actionEntity);

    void fillEntity(ActionEntity actionEntity, Action action);

    ActionEntity createEntity(Action action);
}
