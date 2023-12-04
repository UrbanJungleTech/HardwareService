package urbanjungletech.hardwareservice.converter.alert.action.implementation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.action.AlertActionConverter;
import urbanjungletech.hardwareservice.converter.alert.action.SpecificAlertActionConverter;
import urbanjungletech.hardwareservice.entity.alert.action.AlertActionEntity;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;

import java.util.Map;

@Service
public class AlertActionConverterImpl implements AlertActionConverter {

    private final Map<Class, SpecificAlertActionConverter> actionConverterMap;

    public AlertActionConverterImpl(Map<Class, SpecificAlertActionConverter> actionConverterMap) {
        this.actionConverterMap = actionConverterMap;
    }

    @Override
    public AlertAction toModel(AlertActionEntity alertActionEntity) {
        AlertAction result = this.actionConverterMap.get(alertActionEntity.getClass()).toModel(alertActionEntity);
        result.setId(alertActionEntity.getId());
        result.setType(alertActionEntity.getType());
        result.setAlertId(alertActionEntity.getAlert().getId());
        return result;
    }

    @Override
    public void fillEntity(AlertActionEntity alertActionEntity, AlertAction alertAction) {
        this.actionConverterMap.get(alertAction.getClass()).fillEntity(alertActionEntity, alertAction);
        alertActionEntity.setType(alertAction.getType());
    }

    @Override
    public AlertActionEntity createEntity(AlertAction alertAction) {
        AlertActionEntity result = this.actionConverterMap.get(alertAction.getClass()).createEntity(alertAction);
        this.fillEntity(result, alertAction);
        return result;
    }
}
