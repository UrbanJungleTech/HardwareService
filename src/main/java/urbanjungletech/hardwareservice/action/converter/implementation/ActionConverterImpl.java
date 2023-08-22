package urbanjungletech.hardwareservice.action.converter.implementation;

import urbanjungletech.hardwareservice.action.converter.ActionConverter;
import urbanjungletech.hardwareservice.action.converter.SpecificActionConverter;
import urbanjungletech.hardwareservice.action.entity.ActionEntity;
import urbanjungletech.hardwareservice.action.model.Action;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ActionConverterImpl implements ActionConverter {

    private Map<Class, SpecificActionConverter> actionConverterMap;

    public ActionConverterImpl(@Qualifier("actionConverterMappings") Map<Class, SpecificActionConverter> actionConverterMap) {
        this.actionConverterMap = actionConverterMap;
    }

    @Override
    public Action toModel(ActionEntity actionEntity) {
        Action result = this.actionConverterMap.get(actionEntity.getClass()).toModel(actionEntity);
        result.setId(actionEntity.getId());
        result.setType(actionEntity.getType());
        result.setSensorReadingAlertId(actionEntity.getSensorReadingAlert().getId());
        return result;
    }

    @Override
    public void fillEntity(ActionEntity actionEntity, Action action) {
        this.actionConverterMap.get(action.getClass()).fillEntity(actionEntity, action);
        actionEntity.setType(action.getType());
    }

    @Override
    public ActionEntity createEntity(Action action) {
        ActionEntity result = this.actionConverterMap.get(action.getClass()).createEntity(action);
        this.fillEntity(result, action);
        return result;
    }
}
