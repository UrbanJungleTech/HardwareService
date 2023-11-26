package urbanjungletech.hardwareservice.converter.alert.action.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.action.SpecificAlertActionConverter;
import urbanjungletech.hardwareservice.entity.alert.action.LoggingAlertActionEntity;
import urbanjungletech.hardwareservice.model.alert.action.LoggingAlertAction;

@Service
public class LoggingAlertActionConverterImpl implements SpecificAlertActionConverter<LoggingAlertAction, LoggingAlertActionEntity> {
    @Override
    public LoggingAlertAction toModel(LoggingAlertActionEntity loggingActionEntity) {
        LoggingAlertAction result = new LoggingAlertAction();
        result.setId(loggingActionEntity.getId());
        result.setType(loggingActionEntity.getType());
        return result;
    }

    @Override
    public void fillEntity(LoggingAlertActionEntity loggingActionEntity, LoggingAlertAction loggingAction) {
        loggingActionEntity.setType(loggingAction.getType());
    }

    @Override
    public LoggingAlertActionEntity createEntity(LoggingAlertAction loggingAction) {
        LoggingAlertActionEntity result = new LoggingAlertActionEntity();
        return result;
    }
}
