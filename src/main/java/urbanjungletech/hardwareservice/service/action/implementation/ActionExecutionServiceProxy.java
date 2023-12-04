package urbanjungletech.hardwareservice.service.action.implementation;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;
import urbanjungletech.hardwareservice.service.action.ActionExecutionService;
import urbanjungletech.hardwareservice.service.action.SpecificActionExecutionService;

import java.util.HashMap;

@Service
@Primary
public class ActionExecutionServiceProxy implements ActionExecutionService {
    private final HashMap<Class<? extends AlertAction>, SpecificActionExecutionService> actionExecutionServices;

    public ActionExecutionServiceProxy(HashMap<Class<? extends AlertAction>, SpecificActionExecutionService> actionExecutionServices) {
        this.actionExecutionServices = actionExecutionServices;
    }

    @Override
    public void execute(AlertAction alertAction) {
        actionExecutionServices.get(alertAction.getClass()).execute(alertAction);
    }
}
