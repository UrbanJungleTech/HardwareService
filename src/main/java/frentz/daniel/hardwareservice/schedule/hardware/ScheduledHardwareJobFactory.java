package frentz.daniel.hardwareservice.schedule.hardware;

import frentz.daniel.hardwareservice.addition.HardwareAdditionService;
import frentz.daniel.hardwareservice.converter.HardwareConverter;
import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.schedule.hardware.ScheduledHardwareJob;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.stereotype.Service;

@Service("HardwareCronJobFactory")
public class ScheduledHardwareJobFactory extends SimpleJobFactory {

    private HardwareAdditionService hardwareAdditionService;
    private HardwareConverter hardwareConverter;

    public ScheduledHardwareJobFactory(HardwareAdditionService hardwareAdditionService,
                                       HardwareConverter hardwareConverter){
        this.hardwareAdditionService = hardwareAdditionService;
        this.hardwareConverter = hardwareConverter;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler){
        ScheduledHardwareEntity scheduledHardware = (ScheduledHardwareEntity) bundle.getJobDetail().getJobDataMap().get("scheduledHardware");
        ScheduledHardwareJob result = new ScheduledHardwareJob(scheduledHardware, hardwareAdditionService, hardwareConverter);
        return result;
    }
}
