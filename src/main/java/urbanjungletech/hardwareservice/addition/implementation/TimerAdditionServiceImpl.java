package urbanjungletech.hardwareservice.addition.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urbanjungletech.hardwareservice.addition.TimerAdditionService;
import urbanjungletech.hardwareservice.converter.ScheduledHardwareJobConverter;
import urbanjungletech.hardwareservice.converter.TimerConverter;
import urbanjungletech.hardwareservice.dao.TimerDAO;
import urbanjungletech.hardwareservice.entity.TimerEntity;
import urbanjungletech.hardwareservice.event.timer.TimerEventPublisher;
import urbanjungletech.hardwareservice.model.ONOFF;
import urbanjungletech.hardwareservice.model.ScheduledHardware;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.service.query.ScheduledHardwareQueryService;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimerAdditionServiceImpl implements TimerAdditionService {
    private final TimerDAO timerDAO;
    private final TimerConverter timerConverter;
    private final TimerEventPublisher timerEventPublisher;
    private final ScheduledHardwareQueryService scheduledHardwareQueryService;
    private final ScheduledHardwareJobConverter scheduledHardwareJobConverter;

    public TimerAdditionServiceImpl(TimerDAO timerDAO,
                                    TimerConverter timerConverter,
                                    TimerEventPublisher timerEventPublisher,
                                    ScheduledHardwareQueryService scheduledHardwareQueryService,
                                    ScheduledHardwareJobConverter scheduledHardwareJobConverter) {
        this.timerDAO = timerDAO;
        this.timerConverter = timerConverter;
        this.timerEventPublisher = timerEventPublisher;
        this.scheduledHardwareQueryService = scheduledHardwareQueryService;
        this.scheduledHardwareJobConverter = scheduledHardwareJobConverter;
    }

    @Override
    @Transactional
    public Timer create(Timer timer) {
        TimerEntity timerEntity = this.timerDAO.addTimer(timer);
        timer.setId(timerEntity.getId());
        ScheduledHardware onCronJob = this.scheduledHardwareJobConverter.toScheduledHardware(timer, ONOFF.ON);
        this.scheduledHardwareQueryService.createScheduledHardware(onCronJob);

        ScheduledHardware offCronJob = this.scheduledHardwareJobConverter.toScheduledHardware(timer, ONOFF.OFF);
        this.scheduledHardwareQueryService.createScheduledHardware(offCronJob);
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
