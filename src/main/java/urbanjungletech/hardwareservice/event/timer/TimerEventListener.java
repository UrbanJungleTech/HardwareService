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
        TimerEntity timerEntity = this.timerDAO.getTimer(timerCreateEvent.getTimerId());
        this.scheduledHardwareScheduleService.start(timerEntity.getOnCronJob().getId());
        this.scheduledHardwareScheduleService.start(timerEntity.getOffCronJob().getId());
    }

    @Async
    @EventListener
    public void onTimerDeleteEvent(TimerDeleteEvent timerDeleteEvent) {
        TimerEntity timerEntity = this.timerDAO.getTimer(timerDeleteEvent.getTimerId());
        this.scheduledHardwareScheduleService.deleteSchedule(timerEntity.getOnCronJob().getId());
        this.scheduledHardwareScheduleService.deleteSchedule(timerEntity.getOffCronJob().getId());
    }

    @Async
    @EventListener
    public void onTimerUpdateEvent(TimerUpdateEvent timerUpdateEvent) {
        TimerEntity timerEntity = this.timerDAO.getTimer(timerUpdateEvent.getTimerId());
        this.scheduledHardwareScheduleService.restartSchedule(timerEntity.getOnCronJob().getId());
        this.scheduledHardwareScheduleService.restartSchedule(timerEntity.getOffCronJob().getId());
    }
}
