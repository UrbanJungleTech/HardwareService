package urbanjungletech.hardwareservice.schedule.hardware;

import urbanjungletech.hardwareservice.addition.HardwareStateAdditionService;
import urbanjungletech.hardwareservice.model.ScheduledHardware;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.stereotype.Service;

@Service("HardwareCronJobFactory")
public class ScheduledHardwareJobFactory extends SimpleJobFactory {

    private HardwareStateAdditionService hardwareStateAdditionService;
    private HardwareQueryService hardwareQueryService;

    public ScheduledHardwareJobFactory(HardwareStateAdditionService hardwareStateAdditionService,
                                       HardwareQueryService hardwareQueryService){
        this.hardwareStateAdditionService = hardwareStateAdditionService;
        this.hardwareQueryService = hardwareQueryService;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler){
        ScheduledHardware scheduledHardware = (ScheduledHardware) bundle.getJobDetail().getJobDataMap().get("scheduledHardware");
        ScheduledHardwareJob result = new ScheduledHardwareJob(scheduledHardware, hardwareStateAdditionService, hardwareQueryService);
        return result;
    }
}
