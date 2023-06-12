package frentz.daniel.hardwareservice.schedule.factory;

import frentz.daniel.hardwareservice.converter.HardwareConverter;
import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.schedule.job.ScheduledHardwareJob;
import frentz.daniel.hardwareservice.dao.HardwareDAO;
import frentz.daniel.hardwareservice.service.HardwareQueueService;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.stereotype.Service;

@Service("HardwareCronJobFactory")
public class ScheduledHardwareJobFactory extends SimpleJobFactory {

    private HardwareDAO hardwareDAO;
    private HardwareQueueService hardwareQueueService;
    private HardwareConverter hardwareConverter;

    public ScheduledHardwareJobFactory(HardwareQueueService hardwareQueueService,
                                       HardwareDAO hardwareDAO,
                                       HardwareConverter hardwareConverter){
        this.hardwareQueueService = hardwareQueueService;
        this.hardwareDAO = hardwareDAO;
        this.hardwareConverter = hardwareConverter;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler){
        ScheduledHardwareEntity scheduledHardware = (ScheduledHardwareEntity) bundle.getJobDetail().getJobDataMap().get("scheduledHardware");
        ScheduledHardwareJob result = new ScheduledHardwareJob(scheduledHardware, this.hardwareDAO, this.hardwareQueueService, this.hardwareConverter);
        return result;
    }
}
