package urbanjungletech.hardwareservice.service.action.implementation;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;
import urbanjungletech.hardwareservice.service.action.ActionExecutionService;
import urbanjungletech.hardwareservice.service.action.SpecificActionExecutionService;

import java.util.HashMap;

@Service
@Primary
public class ActionExecutionServiceProxy implements ActionExecutionService<AlertAction> {
    private HashMap<Class, SpecificActionExecutionService> actionExecutionServices;

    public ActionExecutionServiceProxy(HashMap<Class, SpecificActionExecutionService> actionExecutionServices) {
        this.actionExecutionServices = actionExecutionServices;
    }

    @Override
    public void execute(AlertAction alertAction) {
        actionExecutionServices.get(alertAction.getClass()).execute(alertAction);
    }
}
