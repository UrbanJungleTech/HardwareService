package frentz.daniel.hardwareservice.config.schedulers;

import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.schedule.service.ScheduledHardwareScheduleService;
import frentz.daniel.hardwareservice.dao.ScheduledHardwareJobDAO;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class ScheduledHardwareJobConfig implements ApplicationListener<ContextRefreshedEvent> {

    private ScheduledHardwareJobDAO scheduledHardwareJobDAO;
    private ScheduledHardwareScheduleService scheduledHardwareScheduleService;
    public ScheduledHardwareJobConfig(ScheduledHardwareJobDAO scheduledHardwareJobDAO,
                                      ScheduledHardwareScheduleService scheduledHardwareScheduleService){
        this.scheduledHardwareJobDAO = scheduledHardwareJobDAO;
        this.scheduledHardwareScheduleService = scheduledHardwareScheduleService;

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.scheduledHardwareJobDAO.getAll().forEach((ScheduledHardwareEntity job) -> {
            this.scheduledHardwareScheduleService.start(job);
        });
    }
}
