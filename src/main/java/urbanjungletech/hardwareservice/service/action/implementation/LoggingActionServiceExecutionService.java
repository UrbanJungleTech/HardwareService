package urbanjungletech.hardwareservice.service.action.implementation;

import urbanjungletech.hardwareservice.model.LoggingAction;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.service.action.ActionExecutionService;

@Service
public class LoggingActionServiceExecutionService implements ActionExecutionService<LoggingAction> {
    @Override
    public void execute(LoggingAction action) {

    }
}
