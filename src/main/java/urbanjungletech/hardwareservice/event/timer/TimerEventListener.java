package urbanjungletech.hardwareservice.event.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import urbanjungletech.hardwareservice.dao.TimerDAO;
import urbanjungletech.hardwareservice.entity.TimerEntity;
import urbanjungletech.hardwareservice.schedule.hardware.ScheduledHardwareScheduleService;

@Service
public class TimerEventListener {

    private static final Logger logger = LoggerFactory.getLogger(TimerEventListener.class);
    private ScheduledHardwareScheduleService scheduledHardwareScheduleService;
    private TimerDAO timerDAO;
    public TimerEventListener(ScheduledHardwareScheduleService scheduledHardwareScheduleService,
                              TimerDAO timerDAO) {
        this.scheduledHardwareScheduleService = scheduledHardwareScheduleService;
        this.timerDAO = timerDAO;
    }
    @Async
    @TransactionalEventListener
    public void onTimerCreateEvent(TimerCreateEvent timerCreateEvent) throws InterruptedException {
        this.scheduledHardwareScheduleService.start(timerCreateEvent.getTimerId());
    }

    @Async
    @TransactionalEventListener
    public void onTimerDeleteEvent(TimerDeleteEvent timerDeleteEvent) {
        this.scheduledHardwareScheduleService.deleteSchedule(timerDeleteEvent.getTimerId());
    }

    @Async
    @TransactionalEventListener
    public void onTimerUpdateEvent(TimerUpdateEvent timerUpdateEvent) {
        logger.info("Timer update event received");
        this.scheduledHardwareScheduleService.restartSchedule(timerUpdateEvent.getTimerId());
    }
}
