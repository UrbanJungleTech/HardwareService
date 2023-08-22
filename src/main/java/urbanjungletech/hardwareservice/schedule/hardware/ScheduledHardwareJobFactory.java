package urbanjungletech.hardwareservice.schedule.hardware;

import urbanjungletech.hardwareservice.addition.HardwareStateAdditionService;
import urbanjungletech.hardwareservice.model.ScheduledHardware;
import urbanjungletech.hardwareservice.service.HardwareService;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.stereotype.Service;

@Service("HardwareCronJobFactory")
public class ScheduledHardwareJobFactory extends SimpleJobFactory {

    private HardwareStateAdditionService hardwareStateAdditionService;
    private HardwareService hardwareService;

    public ScheduledHardwareJobFactory(HardwareStateAdditionService hardwareStateAdditionService,
                                       HardwareService hardwareService){
        this.hardwareStateAdditionService = hardwareStateAdditionService;
        this.hardwareService = hardwareService;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler){
        ScheduledHardware scheduledHardware = (ScheduledHardware) bundle.getJobDetail().getJobDataMap().get("scheduledHardware");
        ScheduledHardwareJob result = new ScheduledHardwareJob(scheduledHardware, hardwareStateAdditionService, hardwareService);
        return result;
    }
}
