package frentz.daniel.hardwareservice.config.schedulers;

import frentz.daniel.hardwareservice.schedule.service.RegulatorScheduleService;
import frentz.daniel.hardwareservice.dao.RegulatorDAO;
import frentz.daniel.hardwareservice.client.model.Regulator;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

public class ScheduledRegulatorConfig implements ApplicationListener<ContextRefreshedEvent> {

    private RegulatorDAO regulatorDAO;
    private RegulatorScheduleService regulatorScheduleService;

    public ScheduledRegulatorConfig(RegulatorDAO regulatorDAO,
                                    RegulatorScheduleService regulatorScheduleService){
        this.regulatorDAO = regulatorDAO;
        this.regulatorScheduleService = regulatorScheduleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<Regulator> regulators = this.regulatorDAO.getAll();
        regulators.forEach((Regulator regulator) -> {
            this.regulatorScheduleService.start(regulator);
        });
    }
}
