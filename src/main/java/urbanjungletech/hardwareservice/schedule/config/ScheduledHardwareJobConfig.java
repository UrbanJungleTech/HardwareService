package urbanjungletech.hardwareservice.schedule.config;

import urbanjungletech.hardwareservice.entity.ScheduledHardwareEntity;
import urbanjungletech.hardwareservice.schedule.hardware.ScheduledHardwareScheduleService;
import urbanjungletech.hardwareservice.dao.ScheduledHardwareDAO;
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
            this.scheduledHardwareScheduleService.start(job.getId());
        });
    }
}
