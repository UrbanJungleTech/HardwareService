package urbanjungletech.hardwareservice.action.service;

import urbanjungletech.hardwareservice.action.model.HardwareStateChangeAction;
import urbanjungletech.hardwareservice.service.HardwareService;
import org.springframework.stereotype.Service;

@Service
public class HardwareStateChangeActionExecutionService implements ActionExecutionService<HardwareStateChangeAction> {
    private HardwareService hardwareService;
    @Override
    public void execute(HardwareStateChangeAction action) {

    }
}
