package urbanjungletech.hardwareservice.service.alert.action.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.ScheduledHardware;
import urbanjungletech.hardwareservice.model.alert.action.CancelNextScheduledHardwareAlertAction;
import urbanjungletech.hardwareservice.service.alert.action.SpecificActionExecutionService;
import urbanjungletech.hardwareservice.service.query.ScheduledHardwareQueryService;

@Service
public class CancelNextScheduledHardwareAlertActionService implements SpecificActionExecutionService<CancelNextScheduledHardwareAlertAction> {

    private final TimerService timerService;
    public CancelNextScheduledHardwareAlertActionService(ScheduledHardwareQueryService scheduledHardwareQueryService) {
        this.scheduledHardwareQueryService = scheduledHardwareQueryService;
    }
    @Override
    public void execute(CancelNextScheduledHardwareAlertAction action) {
        ScheduledHardware scheduledHardware = this.scheduledHardwareQueryService.getById(action.getScheduledHardwareId());

    }
}
