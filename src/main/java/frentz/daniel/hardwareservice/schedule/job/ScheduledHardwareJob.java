package frentz.daniel.hardwareservice.schedule.job;

import frentz.daniel.hardwareservice.addition.HardwareAdditionService;
import frentz.daniel.hardwareservice.client.model.HardwareState;
import frentz.daniel.hardwareservice.converter.HardwareConverter;
import frentz.daniel.hardwareservice.converter.HardwareStateConverter;
import frentz.daniel.hardwareservice.dao.HardwareDAO;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.service.HardwareQueueService;
import frentz.daniel.hardwareservice.client.model.Hardware;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public class ScheduledHardwareJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledHardwareJob.class);
    private ScheduledHardwareEntity scheduledHardware;
    private HardwareAdditionService hardwareAdditionService;
    private HardwareConverter hardwareConverter;

    public ScheduledHardwareJob(ScheduledHardwareEntity scheduledHardware,
                                HardwareAdditionService hardwareAdditionService,
                                HardwareConverter hardwareConverter){
        this.scheduledHardware = scheduledHardware;
        this.hardwareAdditionService = hardwareAdditionService;
        this.hardwareConverter = hardwareConverter;
    }

    @Transactional
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.debug("Executing hardware scheduled job with state {}", scheduledHardware.getHardwareState().getState());
        HardwareEntity hardware = scheduledHardware.getTimerEntity().getHardware();
        Hardware hardwareModel = hardwareConverter.toModel(hardware);
        HardwareState hardwareState = new HardwareState();
        hardwareState.setState(scheduledHardware.getHardwareState().getState());
        hardwareState.setLevel(scheduledHardware.getHardwareState().getLevel());
        hardwareModel.setDesiredState(hardwareState);
        this.hardwareAdditionService.update(hardware.getId(), hardwareModel);
    }
}
