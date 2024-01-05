package urbanjungletech.hardwareservice.event.hardware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.schedule.hardware.ScheduledHardwareScheduleService;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.ControllerCommunicationService;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;

/**
 * Listens for CRUD events related to the Hardware entity and calls the hardware communication service
 * to execute appropriate actions based on the event.
 */
@Service
public class HardwareControllerHardwareEventListener {
    private static final Logger logger = LoggerFactory.getLogger(HardwareControllerHardwareEventListener.class);
    private final ControllerCommunicationService controllerCommunicationService;
    private final HardwareQueryService hardwareQueryService;
    private final ScheduledHardwareScheduleService scheduledHardwareScheduleService;

    public HardwareControllerHardwareEventListener(ControllerCommunicationService controllerCommunicationService,
                                                   HardwareQueryService hardwareQueryService,
                                                   ScheduledHardwareScheduleService scheduledHardwareScheduleService){
        this.controllerCommunicationService = controllerCommunicationService;
        this.hardwareQueryService = hardwareQueryService;
        this.scheduledHardwareScheduleService = scheduledHardwareScheduleService;
    }

    @Async
    @TransactionalEventListener
    public void handleHardwareCreateEvent(HardwareCreateEvent HardwareCreateEvent){
        logger.debug("Sending hardware create event to hardware controller.");
        Hardware hardware = this.hardwareQueryService.getHardware(HardwareCreateEvent.getHardwareId());
        this.controllerCommunicationService.registerHardware(hardware);
    }

    @Async
    @EventListener
    public void handleHardwareDeleteEvent(HardwareDeleteEvent hardwareDeleteEvent){
        Hardware hardware = this.hardwareQueryService.getHardware(hardwareDeleteEvent.getHardwareId());
        this.controllerCommunicationService.deregisterHardware(hardware);
        this.scheduledHardwareScheduleService.deleteSchedule(hardwareDeleteEvent.getHardwareId());
    }

}
