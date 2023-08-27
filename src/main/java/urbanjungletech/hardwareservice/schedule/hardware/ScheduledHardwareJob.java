package urbanjungletech.hardwareservice.schedule.hardware;

import urbanjungletech.hardwareservice.addition.HardwareStateAdditionService;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.ScheduledHardware;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledHardwareJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledHardwareJob.class);
    private ScheduledHardware scheduledHardware;
    private HardwareStateAdditionService hardwareStateAdditionService;
    private HardwareQueryService hardwareQueryService;

    public ScheduledHardwareJob(ScheduledHardware scheduledHardware,
                                HardwareStateAdditionService hardwareStateAdditionService,
                                HardwareQueryService hardwareQueryService){
        this.scheduledHardware = scheduledHardware;
        this.hardwareStateAdditionService = hardwareStateAdditionService;
        this.hardwareQueryService = hardwareQueryService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.debug("Executing hardware scheduled job with state {}", scheduledHardware.getOnoff());
        long hardwareStateId = this.hardwareQueryService.getHardware(scheduledHardware.getHardwareId()).getDesiredState().getId();
        HardwareState hardwareState = new HardwareState();
        hardwareState.setState(scheduledHardware.getOnoff());
        hardwareState.setLevel(scheduledHardware.getLevel());
        hardwareState.setHardwareId(scheduledHardware.getHardwareId());
        this.hardwareStateAdditionService.update(hardwareStateId, hardwareState);
    }
}
