package urbanjungletech.hardwareservice.converter.implementation;

import urbanjungletech.hardwareservice.converter.SpecificActionConverter;
import urbanjungletech.hardwareservice.entity.LoggingActionEntity;
import urbanjungletech.hardwareservice.model.LoggingAction;
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
