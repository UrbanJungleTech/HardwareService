package urbanjungletech.hardwareservice.converter.alert.action;

import urbanjungletech.hardwareservice.entity.alert.action.AlertActionEntity;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;

public interface SpecificAlertActionConverter<Model extends AlertAction, Entity extends AlertActionEntity> {
    Model toModel(Entity actionEntity);
    void fillEntity(Entity entity, Model model);
    Entity createEntity(Model model);
}
