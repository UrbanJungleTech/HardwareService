package urbanjungletech.hardwareservice.converter;

import urbanjungletech.hardwareservice.entity.ActionEntity;
import urbanjungletech.hardwareservice.model.Action;

public interface SpecificActionConverter<Model extends Action, Entity extends ActionEntity> {
    Action toModel(Entity actionEntity);
    void fillEntity(Entity entity, Model model);
    Entity createEntity(Model model);
}
