package urbanjungletech.hardwareservice.service.alert.action.implementation;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;
import urbanjungletech.hardwareservice.service.alert.action.ActionExecutionService;
import urbanjungletech.hardwareservice.service.alert.action.SpecificActionExecutionService;

import java.util.HashMap;
import java.util.Map;

@Service
@Primary
public class ActionExecutionServiceProxy implements ActionExecutionService {
    private final Map<Class<? extends AlertAction>, SpecificActionExecutionService> actionExecutionServices;

    public ActionExecutionServiceProxy(Map<Class<? extends AlertAction>, SpecificActionExecutionService> actionExecutionServices) {
        this.actionExecutionServices = actionExecutionServices;
    }

    @Override
    public void executeAction(AlertAction alertAction) {
        actionExecutionServices.get(alertAction.getClass()).execute(alertAction);
    }
}
