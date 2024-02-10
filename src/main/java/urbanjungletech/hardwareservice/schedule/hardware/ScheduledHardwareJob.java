package urbanjungletech.hardwareservice.schedule.hardware;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import urbanjungletech.hardwareservice.addition.HardwareStateAdditionService;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;
import urbanjungletech.hardwareservice.service.query.TimerQueryService;

public class ScheduledHardwareJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledHardwareJob.class);
    private final Long timerId;
    private final HardwareStateAdditionService hardwareStateAdditionService;
    private final HardwareQueryService hardwareQueryService;
    private final TimerQueryService timerQueryService;

    public ScheduledHardwareJob(Long timerId,
                                HardwareStateAdditionService hardwareStateAdditionService,
                                HardwareQueryService hardwareQueryService,
                                TimerQueryService timerQueryService){
        this.timerId = timerId;
        this.hardwareStateAdditionService = hardwareStateAdditionService;
        this.hardwareQueryService = hardwareQueryService;
        this.timerQueryService = timerQueryService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Timer timer = this.timerQueryService.getTimer(this.timerId);
        Hardware hardware = this.hardwareQueryService.getHardware(timer.getHardwareId());
        hardware.getDesiredState().setState(timer.getState());
        hardware.getDesiredState().setLevel(timer.getLevel());
        this.hardwareStateAdditionService.update(hardware.getDesiredState().getId(), hardware.getDesiredState());
    }
}
