package urbanjungletech.hardwareservice.action.converter;

import urbanjungletech.hardwareservice.action.entity.ActionEntity;
import urbanjungletech.hardwareservice.action.model.Action;

public interface SpecificActionConverter<Model extends Action, Entity extends ActionEntity> {
    Action toModel(Entity actionEntity);
    void fillEntity(Entity entity, Model model);
    Entity createEntity(Model model);
}
