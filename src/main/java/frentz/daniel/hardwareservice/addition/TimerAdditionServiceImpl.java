package frentz.daniel.hardwareservice.addition;

import frentz.daniel.hardwareservice.client.model.ScheduledHardware;
import frentz.daniel.hardwareservice.converter.TimerConverter;
import frentz.daniel.hardwareservice.dao.TimerDAO;
import frentz.daniel.hardwareservice.entity.TimerEntity;
import frentz.daniel.hardwareservice.event.timer.TimerEventPublisher;
import frentz.daniel.hardwareservice.client.model.Timer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimerAdditionServiceImpl implements TimerAdditionService {
    private TimerDAO timerDAO;
    private TimerConverter timerConverter;
    private TimerEventPublisher timerEventPublisher;

    public TimerAdditionServiceImpl(TimerDAO timerDAO,
                                    TimerConverter timerConverter,
                                    TimerEventPublisher timerEventPublisher) {
        this.timerDAO = timerDAO;
        this.timerConverter = timerConverter;
        this.timerEventPublisher = timerEventPublisher;
    }

    @Override
    public Timer create(Timer timer) {
        TimerEntity timerEntity = this.timerDAO.addTimer(timer);
        this.timerEventPublisher.publishCreateTimerEvent(timerEntity.getId());
        return this.timerConverter.toModel(timerEntity);
    }

    @Override
    public void delete(long timerId) {
        this.timerEventPublisher.publishDeleteTimerEvent(timerId);
        this.timerDAO.delete(timerId);
    }

    @Override
    public Timer update(long timerId, Timer timer) {
        timer.setId(timerId);
        TimerEntity timerEntity = this.timerDAO.updateTimer(timer);
        if(!timer.getOnCronString().equals(timerEntity.getOnCronJob().getCronString()) || !timer.getOffCronString().equals(timerEntity.getOffCronJob().getCronString())) {
            TimerEntity result = this.timerDAO.updateTimer(timer);
            //TODO: add events for this. Perhaps a "RestartTimerEvent"?
//        this.scheduledHardwareScheduleService.deleteSchedule(result.getOnCronJob().getId());
//            this.scheduledHardwareScheduleService.start(result.getOnCronJob());
//        this.scheduledHardwareScheduleService.deleteSchedule(result.getOffCronJob().getId());
//            this.scheduledHardwareScheduleService.start(result.getOffCronJob());
//            return this.timerConverter.toModel(result);
        }
        return timer;
    }

    @Override
    public List<Timer> updateList(List<Timer> timers) {
        List<Timer> result = new ArrayList<>();
        for(Timer timer : timers){
            Timer timerResult = null;
            if(timer.getId() == null){
                timerResult = this.create(timer);
            }
            else{
                timerResult = this.update(timer.getId(), timer);
            }
            result.add(timerResult);
        }
        return result;
    }
}
