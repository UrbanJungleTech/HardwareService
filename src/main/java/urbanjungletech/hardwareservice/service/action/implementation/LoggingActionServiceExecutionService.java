package urbanjungletech.hardwareservice.service.action.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.alert.action.LoggingAlertAction;
import urbanjungletech.hardwareservice.service.action.SpecificActionExecutionService;

@Service
public class LoggingActionServiceExecutionService implements SpecificActionExecutionService<LoggingAlertAction> {
    @Override
    public void execute(LoggingAlertAction action) {
    }
}
