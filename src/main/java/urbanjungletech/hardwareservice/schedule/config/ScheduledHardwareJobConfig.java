package urbanjungletech.hardwareservice.schedule.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.schedule.hardware.ScheduledHardwareScheduleService;
import urbanjungletech.hardwareservice.service.query.TimerQueryService;

@Configuration
public class ScheduledHardwareJobConfig implements ApplicationListener<ContextRefreshedEvent> {


    private final TimerQueryService timerQueryService;
    private final ScheduledHardwareScheduleService scheduledHardwareScheduleService;
    public ScheduledHardwareJobConfig(TimerQueryService timerQueryService,
                                      ScheduledHardwareScheduleService scheduledHardwareScheduleService){
        this.timerQueryService = timerQueryService;
        this.scheduledHardwareScheduleService = scheduledHardwareScheduleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.timerQueryService.getTimers().forEach((Timer timer) -> {
            this.scheduledHardwareScheduleService.start(timer.getId());
        });
    }
}
