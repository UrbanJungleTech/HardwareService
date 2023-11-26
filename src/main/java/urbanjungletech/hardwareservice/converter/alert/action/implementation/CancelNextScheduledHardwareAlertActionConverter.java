package urbanjungletech.hardwareservice.converter.alert.action.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.action.SpecificAlertActionConverter;
import urbanjungletech.hardwareservice.entity.alert.action.CancelNextScheduledHardwareAlertActionEntity;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;
import urbanjungletech.hardwareservice.model.alert.action.CancelNextScheduledHardwareAlertAction;

@Service
public class CancelNextScheduledHardwareAlertActionConverter implements SpecificAlertActionConverter<CancelNextScheduledHardwareAlertAction, CancelNextScheduledHardwareAlertActionEntity> {
    @Override
    public AlertAction toModel(CancelNextScheduledHardwareAlertActionEntity actionEntity) {
        CancelNextScheduledHardwareAlertAction result = new CancelNextScheduledHardwareAlertAction("cancelNextScheduledHardware");
        result.setScheduledHardwareId(actionEntity.getScheduledHardwareId());
        return result;
    }

    @Override
    public void fillEntity(CancelNextScheduledHardwareAlertActionEntity cancelNextScheduledHardwareAlertActionEntity, CancelNextScheduledHardwareAlertAction cancelNextScheduledHardwareAlertAction) {
        cancelNextScheduledHardwareAlertActionEntity.setScheduledHardwareId(cancelNextScheduledHardwareAlertAction.getScheduledHardwareId());
    }

    @Override
    public CancelNextScheduledHardwareAlertActionEntity createEntity(CancelNextScheduledHardwareAlertAction cancelNextScheduledHardwareAlertAction) {
        CancelNextScheduledHardwareAlertActionEntity result = new CancelNextScheduledHardwareAlertActionEntity();
        return result;
    }
}
