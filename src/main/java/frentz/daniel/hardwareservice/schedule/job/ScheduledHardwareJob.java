package frentz.daniel.hardwareservice.schedule.job;

import frentz.daniel.hardwareservice.converter.HardwareConverter;
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

public class ScheduledHardwareJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledHardwareJob.class);
    private HardwareDAO hardwareDAO;
    private HardwareQueueService hardwareQueueService;
    private ScheduledHardwareEntity scheduledHardware;
    private HardwareConverter hardwareConverter;
    public ScheduledHardwareJob(ScheduledHardwareEntity scheduledHardware,
                                HardwareDAO hardwareDAO,
                                HardwareQueueService hardwareQueueService,
                                HardwareConverter hardwareConverter){
        this.hardwareDAO = hardwareDAO;
        this.hardwareQueueService = hardwareQueueService;
        this.scheduledHardware = scheduledHardware;
        this.hardwareConverter = hardwareConverter;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.debug("Executing hardware scheduled job");
        HardwareEntity hardware = scheduledHardware.getTimerEntity().getHardware();
        hardware.setDesiredState(scheduledHardware.getHardwareState());
        Hardware updateHardware = this.hardwareConverter.toModel(hardware);
        this.hardwareDAO.updateHardware(updateHardware);
        this.hardwareQueueService.sendStateToController(hardware);
    }
}
