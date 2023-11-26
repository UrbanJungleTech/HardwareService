package urbanjungletech.hardwareservice.schedule.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import urbanjungletech.hardwareservice.dao.ScheduledHardwareDAO;
import urbanjungletech.hardwareservice.entity.ScheduledHardwareEntity;
import urbanjungletech.hardwareservice.schedule.hardware.ScheduledHardwareScheduleService;

@Configuration
public class ScheduledHardwareJobConfig implements ApplicationListener<ContextRefreshedEvent> {

    private final ScheduledHardwareDAO scheduledHardwareDAO;
    private final ScheduledHardwareScheduleService scheduledHardwareScheduleService;
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
