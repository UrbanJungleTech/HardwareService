package urbanjungletech.hardwareservice.service.alert.action.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.alert.action.LoggingAlertAction;
import urbanjungletech.hardwareservice.service.alert.action.SpecificActionExecutionService;

@Service
public class LoggingActionServiceExecutionService implements SpecificActionExecutionService<LoggingAlertAction> {
    @Override
    public void execute(LoggingAlertAction action) {
        System.out.println("Logging action executed");
    }
}
