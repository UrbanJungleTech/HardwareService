package urbanjungletech.hardwareservice.service.alert.action.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.HardwareStateAdditionService;
import urbanjungletech.hardwareservice.model.alert.action.HardwareStateChangeAlertAction;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.service.alert.action.SpecificActionExecutionService;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;

@Service
public class HardwareStateChangeActionExecutionService implements SpecificActionExecutionService<HardwareStateChangeAlertAction> {
    private final HardwareQueryService hardwareQueryService;
    private final HardwareStateAdditionService hardwareStateAdditionService;

    public HardwareStateChangeActionExecutionService(HardwareQueryService hardwareQueryService,
                                                      HardwareStateAdditionService hardwareStateAdditionService){
        this.hardwareQueryService = hardwareQueryService;
        this.hardwareStateAdditionService = hardwareStateAdditionService;
    }

    @Override
    public void execute(HardwareStateChangeAlertAction action) {
        Hardware hardware = this.hardwareQueryService.getHardware(action.getHardwareId());
        hardware.getDesiredState().setLevel(action.getLevel());
        hardware.getDesiredState().setState(action.getState());
        this.hardwareStateAdditionService.update(hardware.getDesiredState().getId(), hardware.getDesiredState());
    }
}
