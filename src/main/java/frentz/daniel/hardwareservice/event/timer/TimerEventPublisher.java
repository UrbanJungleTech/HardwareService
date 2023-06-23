package frentz.daniel.hardwareservice.event.timer;

import frentz.daniel.hardwareservice.event.hardware.HardwareCreateEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class TimerEventPublisher {
    private ApplicationEventPublisher applicationEventPublisher;
    public TimerEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishCreateTimerEvent(long timerId){
                TimerCreateEvent timerCreateEvent = new TimerCreateEvent(timerId);
                applicationEventPublisher.publishEvent(timerCreateEvent);
    }

    public void publishDeleteTimerEvent(long timerId){
        TimerDeleteEvent timerDeleteEvent = new TimerDeleteEvent(timerId);
        this.applicationEventPublisher.publishEvent(timerDeleteEvent);
    }
}
