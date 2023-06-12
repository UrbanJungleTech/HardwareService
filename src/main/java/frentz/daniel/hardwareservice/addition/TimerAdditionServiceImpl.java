package frentz.daniel.hardwareservice.addition;

import frentz.daniel.hardwareservice.converter.TimerConverter;
import frentz.daniel.hardwareservice.dao.TimerDAO;
import frentz.daniel.hardwareservice.entity.TimerEntity;
import frentz.daniel.hardwareservice.schedule.service.ScheduledHardwareScheduleService;
import frentz.daniel.hardwareservice.client.model.Timer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimerAdditionServiceImpl implements TimerAdditionService {

    private ScheduledHardwareScheduleService scheduledHardwareScheduleService;
    private TimerDAO timerDAO;
    private TimerConverter timerConverter;
    private ScheduledHardwareJobAdditionService scheduledHardwareJobAdditionService;

    public TimerAdditionServiceImpl(TimerDAO timerDAO,
                                    ScheduledHardwareScheduleService scheduledHardwareScheduleService,
                                    TimerConverter timerConverter,
                                    ScheduledHardwareJobAdditionService scheduledHardwareJobAdditionService) {
        this.timerDAO = timerDAO;
        this.scheduledHardwareScheduleService = scheduledHardwareScheduleService;
        this.timerConverter = timerConverter;
        this.scheduledHardwareJobAdditionService = scheduledHardwareJobAdditionService;
    }

    @Override
    public Timer create(Timer timer) {
        TimerEntity timerEntity = this.timerDAO.addTimer(timer);
        this.scheduledHardwareScheduleService.start(timerEntity.getOnCronJob());
        this.scheduledHardwareScheduleService.start(timerEntity.getOffCronJob());
        return this.timerConverter.toModel(timerEntity);
    }

    @Override
    public void delete(long timerId) {
        TimerEntity timer = this.timerDAO.getTimer(timerId);
        this.scheduledHardwareScheduleService.deleteSchedule(timer.getOnCronJob().getId());
        this.scheduledHardwareScheduleService.deleteSchedule(timer.getOffCronJob().getId());
        this.timerDAO.delete(timerId);
    }

    @Override
    public Timer update(long timerId, Timer timer) {
        timer.setId(timerId);
        TimerEntity timerEntity = this.timerDAO.getTimer(timer.getId());
        if(!timer.getOnCronString().equals(timerEntity.getOnCronJob().getCronString()) || !timer.getOffCronString().equals(timerEntity.getOffCronJob().getCronString())) {
            TimerEntity result = this.timerDAO.updateTimer(timer);
        this.scheduledHardwareScheduleService.deleteSchedule(result.getOnCronJob().getId());
            this.scheduledHardwareScheduleService.start(result.getOnCronJob());
        this.scheduledHardwareScheduleService.deleteSchedule(result.getOffCronJob().getId());
            this.scheduledHardwareScheduleService.start(result.getOffCronJob());
            return this.timerConverter.toModel(result);
        }
        return timer;
    }

    @Override
    public List<Timer> updateList(List<Timer> models) {
        return null;
    }
}
