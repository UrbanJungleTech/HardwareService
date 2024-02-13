package urbanjungletech.hardwareservice.event.condition;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ConditionEventPublisher {
    private ApplicationEventPublisher applicationEventPublisher;

    public ConditionEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishUpdateConditionEvent(long conditionId){
        ConditionUpdateEvent conditionUpdateEvent = new ConditionUpdateEvent(conditionId);
        this.applicationEventPublisher.publishEvent(conditionUpdateEvent);
    }


    public void publishConditionActiveEvent(Long id) {
        ConditionActiveEvent conditionActiveEvent = new ConditionActiveEvent(id);
        this.applicationEventPublisher.publishEvent(conditionActiveEvent);
    }
}
