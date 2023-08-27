package urbanjungletech.hardwareservice.service.action.implementation;

import urbanjungletech.hardwareservice.model.HardwareStateChangeAction;
import urbanjungletech.hardwareservice.service.action.ActionExecutionService;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;
import org.springframework.stereotype.Service;

@Service
public class HardwareStateChangeActionExecutionService implements ActionExecutionService<HardwareStateChangeAction> {
    private HardwareQueryService hardwareQueryService;
    @Override
    public void execute(HardwareStateChangeAction action) {

    }
}
