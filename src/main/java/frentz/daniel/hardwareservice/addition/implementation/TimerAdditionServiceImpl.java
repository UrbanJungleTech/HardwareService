package frentz.daniel.hardwareservice.addition.implementation;

import frentz.daniel.hardwareservice.addition.TimerAdditionService;
import frentz.daniel.hardwareservice.converter.ScheduledHardwareJobConverter;
import frentz.daniel.hardwareservice.model.ONOFF;
import frentz.daniel.hardwareservice.model.ScheduledHardware;
import frentz.daniel.hardwareservice.converter.TimerConverter;
import frentz.daniel.hardwareservice.dao.TimerDAO;
import frentz.daniel.hardwareservice.entity.TimerEntity;
import frentz.daniel.hardwareservice.event.timer.TimerEventPublisher;
import frentz.daniel.hardwareservice.model.Timer;
import frentz.daniel.hardwareservice.service.ScheduledHardwareService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimerAdditionServiceImpl implements TimerAdditionService {
    private TimerDAO timerDAO;
    private TimerConverter timerConverter;
    private TimerEventPublisher timerEventPublisher;
    private ScheduledHardwareService scheduledHardwareService;
    private ScheduledHardwareJobConverter scheduledHardwareJobConverter;

    public TimerAdditionServiceImpl(TimerDAO timerDAO,
                                    TimerConverter timerConverter,
                                    TimerEventPublisher timerEventPublisher,
                                    ScheduledHardwareService scheduledHardwareService,
                                    ScheduledHardwareJobConverter scheduledHardwareJobConverter) {
        this.timerDAO = timerDAO;
        this.timerConverter = timerConverter;
        this.timerEventPublisher = timerEventPublisher;
        this.scheduledHardwareService = scheduledHardwareService;
        this.scheduledHardwareJobConverter = scheduledHardwareJobConverter;
    }

    @Override
    @Transactional
    public Timer create(Timer timer) {
        TimerEntity timerEntity = this.timerDAO.addTimer(timer);
        timer.setId(timerEntity.getId());
        ScheduledHardware onCronJob = this.scheduledHardwareJobConverter.toScheduledHardware(timer, ONOFF.ON);
        this.scheduledHardwareService.createScheduledHardware(onCronJob);

        ScheduledHardware offCronJob = this.scheduledHardwareJobConverter.toScheduledHardware(timer, ONOFF.OFF);
        this.scheduledHardwareService.createScheduledHardware(offCronJob);
        this.timerEventPublisher.publishCreateTimerEvent(timerEntity.getId());
        return this.timerConverter.toModel(timerEntity);
    }

    @Override
    @Transactional
    public void delete(long timerId) {
        this.timerEventPublisher.publishDeleteTimerEvent(timerId);
        this.timerDAO.delete(timerId);
    }

    @Override
    @Transactional
    public Timer update(long timerId, Timer timer) {
        timer.setId(timerId);
        TimerEntity timerEntity = this.timerDAO.updateTimer(timer);
        if(!timer.getOnCronString().equals(timerEntity.getOnCronJob().getCronString()) || !timer.getOffCronString().equals(timerEntity.getOffCronJob().getCronString())) {
            TimerEntity result = this.timerDAO.updateTimer(timer);
            this.timerEventPublisher.publishUpdateTimerEvent(result.getId());
        }
        return timer;
    }

    @Override
    @Transactional
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
