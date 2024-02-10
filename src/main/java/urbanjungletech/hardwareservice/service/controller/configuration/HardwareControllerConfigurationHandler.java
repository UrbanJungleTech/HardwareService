package urbanjungletech.hardwareservice.service.controller.configuration;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import urbanjungletech.hardwareservice.event.hardwarecontroller.HardwareControllerCreateEvent;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.service.controller.configuration.ControllerConfigurationService;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;

import java.util.Map;
import java.util.Optional;

@Service
public class HardwareControllerConfigurationHandler {
    private final Map<Class<? extends HardwareController>, ControllerConfigurationService> controllerConfigurationServiceImplementations;
    private final HardwareControllerQueryService hardwareControllerQueryService;

    public HardwareControllerConfigurationHandler(Map<Class<? extends HardwareController>, ControllerConfigurationService> controllerConfigurationServiceImplementations,
                                                  HardwareControllerQueryService hardwareControllerQueryService) {
        this.controllerConfigurationServiceImplementations = controllerConfigurationServiceImplementations;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
    }

    @TransactionalEventListener
    @Async
    public void handleHardwareCreateEvent(HardwareControllerCreateEvent hardwareControllerCreatedEvent) {
        HardwareController hardwareController = this.hardwareControllerQueryService.getHardwareController(hardwareControllerCreatedEvent.getId());
        ControllerConfigurationService controllerConfigurationServiceImplementation = this.controllerConfigurationServiceImplementations.get(hardwareController.getClass());
        Optional.ofNullable(controllerConfigurationServiceImplementation).ifPresent(controllerConfigurationService -> {
            controllerConfigurationService.configureController(hardwareController);
        });
    }
}
