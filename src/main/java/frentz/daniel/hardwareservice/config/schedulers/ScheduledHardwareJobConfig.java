package frentz.daniel.hardwareservice.config.schedulers;

import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.schedule.service.ScheduledHardwareScheduleService;
import frentz.daniel.hardwareservice.dao.ScheduledHardwareDAO;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class ScheduledHardwareJobConfig implements ApplicationListener<ContextRefreshedEvent> {

    private ScheduledHardwareDAO scheduledHardwareDAO;
    private ScheduledHardwareScheduleService scheduledHardwareScheduleService;
    public ScheduledHardwareJobConfig(ScheduledHardwareDAO scheduledHardwareDAO,
                                      ScheduledHardwareScheduleService scheduledHardwareScheduleService){
        this.scheduledHardwareDAO = scheduledHardwareDAO;
        this.scheduledHardwareScheduleService = scheduledHardwareScheduleService;

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.scheduledHardwareDAO.getAll().forEach((ScheduledHardwareEntity job) -> {
            this.scheduledHardwareScheduleService.start(job);
        });
    }
}
