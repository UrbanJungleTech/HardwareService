package frentz.daniel.hardwareservice.schedule.hardware;

import frentz.daniel.hardwareservice.addition.HardwareAdditionService;
import frentz.daniel.hardwareservice.addition.HardwareStateAdditionService;
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
    private HardwareStateAdditionService hardwareStateAdditionService;
    private HardwareService hardwareService;

    public ScheduledHardwareJob(ScheduledHardware scheduledHardware,
                                HardwareStateAdditionService hardwareStateAdditionService,
                                HardwareService hardwareService){
        this.scheduledHardware = scheduledHardware;
        this.hardwareStateAdditionService = hardwareStateAdditionService;
        this.hardwareService = hardwareService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.debug("Executing hardware scheduled job with state {}", scheduledHardware.getOnoff());
        long hardwareStateId = this.hardwareService.getHardware(scheduledHardware.getHardwareId()).getDesiredState().getId();
        HardwareState hardwareState = new HardwareState();
        hardwareState.setState(scheduledHardware.getOnoff());
        hardwareState.setLevel(scheduledHardware.getLevel());
        hardwareState.setHardwareId(scheduledHardware.getHardwareId());
        this.hardwareStateAdditionService.update(hardwareStateId, hardwareState);
    }
}
