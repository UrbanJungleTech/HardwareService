package frentz.daniel.hardwareservice.event.regulator;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class RegulatorEventPublisher {
    private ApplicationEventPublisher applicationEventPublisher;

    public RegulatorEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishCreateEvent(long regulatorId) {
        RegulatorCreateEvent regulatorCreateEvent = new RegulatorCreateEvent();
        regulatorCreateEvent.setRegulatorId(regulatorId);
        applicationEventPublisher.publishEvent(regulatorCreateEvent);
    }

    public void publishDeleteEvent(long regulatorId) {
        RegulatorDeleteEvent regulatorDeleteEvent = new RegulatorDeleteEvent();
        regulatorDeleteEvent.setRegulatorId(regulatorId);
        applicationEventPublisher.publishEvent(regulatorDeleteEvent);
    }
}
