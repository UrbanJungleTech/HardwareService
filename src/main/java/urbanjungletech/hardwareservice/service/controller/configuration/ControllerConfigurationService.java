package urbanjungletech.hardwareservice.service.controller.configuration;

import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;

public interface ControllerConfigurationService<HardwareControllerType extends HardwareController> {
    void configureController(HardwareController hardwareController);
}
