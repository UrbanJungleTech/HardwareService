package frentz.daniel.hardwareservice.event.regulator;

import frentz.daniel.hardwareservice.model.Regulator;
import frentz.daniel.hardwareservice.schedule.regulator.RegulatorScheduleService;
import frentz.daniel.hardwareservice.service.RegulatorService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

public class RegulatorEventListener {
    private RegulatorScheduleService regulatorScheduleService;
    private RegulatorService regulatorService;

    public RegulatorEventListener(RegulatorScheduleService regulatorScheduleService,
                                  RegulatorService regulatorService) {
        this.regulatorScheduleService = regulatorScheduleService;
        this.regulatorService = regulatorService;
    }

    @Async
    @TransactionalEventListener
    public void onRegulatorCreateEvent(RegulatorCreateEvent regulatorCreateEvent) {
        Regulator regulator = regulatorService.get(regulatorCreateEvent.getRegulatorId());
        this.regulatorScheduleService.start(regulator);
    }

    @Async
    @TransactionalEventListener
    public void onRegulatorDeleteEvent(RegulatorDeleteEvent regulatorDeleteEvent) {
        this.regulatorScheduleService.stop(regulatorDeleteEvent.getRegulatorId());
    }
}
