package urbanjungletech.hardwareservice.schedule.hardware;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.HardwareStateAdditionService;
import urbanjungletech.hardwareservice.dao.ScheduledHardwareDAO;
import urbanjungletech.hardwareservice.model.ScheduledHardware;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;
import urbanjungletech.hardwareservice.service.query.ScheduledHardwareQueryService;

@Service("HardwareCronJobFactory")
public class ScheduledHardwareJobFactory extends SimpleJobFactory {

    private final HardwareStateAdditionService hardwareStateAdditionService;
    private final HardwareQueryService hardwareQueryService;
    private final ScheduledHardwareDAO scheduledHardwareDAO;
    private final ScheduledHardwareQueryService scheduledHardwareQueryService;

    public ScheduledHardwareJobFactory(HardwareStateAdditionService hardwareStateAdditionService,
                                       HardwareQueryService hardwareQueryService,
                                       ScheduledHardwareDAO scheduledHardwareDAO,
                                       ScheduledHardwareQueryService scheduledHardwareQueryService){
        this.hardwareStateAdditionService = hardwareStateAdditionService;
        this.hardwareQueryService = hardwareQueryService;
        this.scheduledHardwareDAO = scheduledHardwareDAO;
        this.scheduledHardwareQueryService = scheduledHardwareQueryService;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler){
        Long scheduledHardwareId = (Long) bundle.getJobDetail().getJobDataMap().get("scheduledHardwareId");
        ScheduledHardwareJob result = new ScheduledHardwareJob(scheduledHardwareId,
                hardwareStateAdditionService,
                hardwareQueryService,
                scheduledHardwareDAO,
                scheduledHardwareQueryService);
        return result;
    }
}
