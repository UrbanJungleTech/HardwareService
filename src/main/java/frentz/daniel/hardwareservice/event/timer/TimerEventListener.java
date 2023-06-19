package frentz.daniel.hardwareservice.event.timer;

import frentz.daniel.hardwareservice.dao.TimerDAO;
import frentz.daniel.hardwareservice.entity.TimerEntity;
import frentz.daniel.hardwareservice.schedule.service.ScheduledHardwareScheduleService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
    @EventListener
    public void onTimerDeleteEvent(TimerCreateEvent timerCreateEvent) {
        TimerEntity timerEntity = this.timerDAO.getTimer(timerCreateEvent.getTimerId());
        this.scheduledHardwareScheduleService.start(timerEntity.getOnCronJob());
        this.scheduledHardwareScheduleService.start(timerEntity.getOffCronJob());
    }

    @Async
    @EventListener
    public void onTimerDeleteEvent(TimerDeleteEvent timerDeleteEvent) {
        TimerEntity timerEntity = this.timerDAO.getTimer(timerDeleteEvent.getTimerId());
        this.scheduledHardwareScheduleService.deleteSchedule(timerEntity.getOnCronJob().getId());
        this.scheduledHardwareScheduleService.deleteSchedule(timerEntity.getOffCronJob().getId());
    }
}
