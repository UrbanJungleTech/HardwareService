package frentz.daniel.hardwareservice.schedule.hardware;

import frentz.daniel.hardwareservice.addition.HardwareAdditionService;
import frentz.daniel.hardwareservice.model.HardwareState;
import frentz.daniel.hardwareservice.converter.HardwareConverter;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.model.ScheduledHardware;
import frentz.daniel.hardwareservice.service.HardwareService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledHardwareJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledHardwareJob.class);
    private ScheduledHardware scheduledHardware;
    private HardwareAdditionService hardwareAdditionService;
    private HardwareService hardwareService;

    public ScheduledHardwareJob(ScheduledHardware scheduledHardware,
                                HardwareAdditionService hardwareAdditionService,
                                HardwareService hardwareService){
        this.scheduledHardware = scheduledHardware;
        this.hardwareAdditionService = hardwareAdditionService;
        this.hardwareService = hardwareService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.debug("Executing hardware scheduled job with state {}", scheduledHardware.getHardwareState().getState());
        Hardware hardware = hardwareService.getHardware(scheduledHardware.getHardwareId());
        hardware.setDesiredState(scheduledHardware.getHardwareState());
        this.hardwareAdditionService.update(hardware.getId(), hardware);
    }
}
