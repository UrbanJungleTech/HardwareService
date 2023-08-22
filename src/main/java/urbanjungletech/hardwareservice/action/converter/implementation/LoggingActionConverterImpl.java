package urbanjungletech.hardwareservice.action.converter.implementation;

import urbanjungletech.hardwareservice.action.converter.SpecificActionConverter;
import urbanjungletech.hardwareservice.action.entity.LoggingActionEntity;
import urbanjungletech.hardwareservice.action.model.LoggingAction;
import org.springframework.stereotype.Service;

@Service
public class LoggingActionConverterImpl implements SpecificActionConverter<LoggingAction, LoggingActionEntity> {
    @Override
    public LoggingAction toModel(LoggingActionEntity loggingActionEntity) {
        LoggingAction result = new LoggingAction();
        result.setId(loggingActionEntity.getId());
        result.setType(loggingActionEntity.getType());
        return result;
    }

    @Override
    public void fillEntity(LoggingActionEntity loggingActionEntity, LoggingAction loggingAction) {
        loggingActionEntity.setType(loggingAction.getType());
    }

    @Override
    public LoggingActionEntity createEntity(LoggingAction loggingAction) {
        return new LoggingActionEntity();
    }
}
