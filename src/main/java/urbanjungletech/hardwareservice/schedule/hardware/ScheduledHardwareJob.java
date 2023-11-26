package urbanjungletech.hardwareservice.schedule.hardware;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import urbanjungletech.hardwareservice.addition.HardwareStateAdditionService;
import urbanjungletech.hardwareservice.dao.ScheduledHardwareDAO;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.ScheduledHardware;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;
import urbanjungletech.hardwareservice.service.query.ScheduledHardwareQueryService;

import java.util.Optional;

public class ScheduledHardwareJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledHardwareJob.class);
    private final Long scheduledHardwareId;
    private final HardwareStateAdditionService hardwareStateAdditionService;
    private final HardwareQueryService hardwareQueryService;
    private final ScheduledHardwareDAO scheduledHardwareDAO;
    private final ScheduledHardwareQueryService scheduledHardwareQueryService;

    public ScheduledHardwareJob(Long scheduledHardwareId,
                                HardwareStateAdditionService hardwareStateAdditionService,
                                HardwareQueryService hardwareQueryService,
                                ScheduledHardwareDAO scheduledHardwareDAO,
                                ScheduledHardwareQueryService scheduledHardwareQueryService){
        this.scheduledHardwareId = scheduledHardwareId;
        this.hardwareStateAdditionService = hardwareStateAdditionService;
        this.hardwareQueryService = hardwareQueryService;
        this.scheduledHardwareDAO = scheduledHardwareDAO;
        this.scheduledHardwareQueryService = scheduledHardwareQueryService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ScheduledHardware scheduledHardware = this.scheduledHardwareQueryService.getById(scheduledHardwareId);
        logger.debug("Executing hardware scheduled job with state {}", scheduledHardware.getOnoff());
        if(Optional.ofNullable(scheduledHardware.getSkipNext()).isPresent() && scheduledHardware.getSkipNext() == true){
            logger.debug("Skipping scheduled hardware job with id {}", scheduledHardware.getId());
            scheduledHardware.setSkipNext(false);
            this.scheduledHardwareDAO.updateScheduledHardwareJob(scheduledHardware);
        }
        else {
            HardwareState hardwareState = this.hardwareQueryService.getHardware(scheduledHardware.getHardwareId()).getDesiredState();
            hardwareState.setState(scheduledHardware.getOnoff());
            hardwareState.setLevel(scheduledHardware.getLevel());
            this.hardwareStateAdditionService.update(hardwareState.getId(), hardwareState);
        }
    }
}
