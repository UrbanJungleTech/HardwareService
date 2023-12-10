package urbanjungletech.hardwareservice.event.timer;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import urbanjungletech.hardwareservice.dao.TimerDAO;
import urbanjungletech.hardwareservice.entity.TimerEntity;
import urbanjungletech.hardwareservice.schedule.hardware.ScheduledHardwareScheduleService;

@Service
public class TimerEventListener {

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
    @EventListener
    public void onTimerDeleteEvent(TimerDeleteEvent timerDeleteEvent) {
        this.scheduledHardwareScheduleService.start(timerDeleteEvent.getTimerId());
    }

    @Async
    @EventListener
    public void onTimerUpdateEvent(TimerUpdateEvent timerUpdateEvent) {
        this.scheduledHardwareScheduleService.restartSchedule(timerUpdateEvent.getTimerId());
    }
}
