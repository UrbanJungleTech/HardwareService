package urbanjungletech.hardwareservice.schedule.hardware;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.HardwareStateAdditionService;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;
import urbanjungletech.hardwareservice.service.query.TimerQueryService;

@Service("HardwareCronJobFactory")
public class ScheduledHardwareJobFactory extends SimpleJobFactory {

    private final HardwareStateAdditionService hardwareStateAdditionService;
    private final HardwareQueryService hardwareQueryService;
    private final TimerQueryService timerQueryService;

    public ScheduledHardwareJobFactory(HardwareStateAdditionService hardwareStateAdditionService,
                                       HardwareQueryService hardwareQueryService,
                                       TimerQueryService timerQueryService){
        this.hardwareStateAdditionService = hardwareStateAdditionService;
        this.hardwareQueryService = hardwareQueryService;
        this.timerQueryService = timerQueryService;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler){
        Long timerId = (Long) bundle.getJobDetail().getJobDataMap().get("timerId");
        ScheduledHardwareJob result = new ScheduledHardwareJob(timerId,
                hardwareStateAdditionService,
                hardwareQueryService,
                timerQueryService);
        return result;
    }
}
