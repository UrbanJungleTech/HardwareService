package urbanjungletech.hardwareservice.service.alert.action.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.ScheduledHardware;
import urbanjungletech.hardwareservice.model.alert.action.CancelNextScheduledHardwareAlertAction;
import urbanjungletech.hardwareservice.service.alert.action.SpecificActionExecutionService;
import urbanjungletech.hardwareservice.service.query.ScheduledHardwareQueryService;

@Service
public class CancelNextScheduledHardwareAlertActionService implements SpecificActionExecutionService<CancelNextScheduledHardwareAlertAction> {

    @Override
    public void execute(CancelNextScheduledHardwareAlertAction action) {

    }
}
