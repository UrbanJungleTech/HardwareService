package urbanjungletech.hardwareservice.event.scheduledreading;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ScheduledReadingEventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    public ScheduledReadingEventPublisher(ApplicationEventPublisher applicationEventPublisher){
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishScheduledReadingCreateEvent(long scheduledReadingId){
        ScheduledReadingCreateEvent scheduledReadingCreateEvent = new ScheduledReadingCreateEvent(scheduledReadingId);
        this.applicationEventPublisher.publishEvent(scheduledReadingCreateEvent);
    }
}
