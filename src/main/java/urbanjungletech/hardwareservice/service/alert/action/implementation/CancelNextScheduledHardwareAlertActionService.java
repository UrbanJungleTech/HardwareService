package urbanjungletech.hardwareservice.service.alert.action.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.TimerAdditionService;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.model.alert.action.CancelNextScheduledHardwareAlertAction;
import urbanjungletech.hardwareservice.service.alert.action.SpecificActionExecutionService;
import urbanjungletech.hardwareservice.service.query.TimerQueryService;

@Service
public class CancelNextScheduledHardwareAlertActionService implements SpecificActionExecutionService<CancelNextScheduledHardwareAlertAction> {

    private TimerQueryService timerQueryService;
    private TimerAdditionService timerAdditionService;

    public CancelNextScheduledHardwareAlertActionService(TimerQueryService timerQueryService,
                                                         TimerAdditionService timerAdditionService) {
        this.timerQueryService = timerQueryService;
        this.timerAdditionService = timerAdditionService;
    }
    @Override
    public void execute(CancelNextScheduledHardwareAlertAction action) {
        Timer timer = this.timerQueryService.getTimer(action.getScheduledHardwareId());
        timer.setSkipNext(true);
        this.timerAdditionService.update(timer.getId(), timer);
    }
}
