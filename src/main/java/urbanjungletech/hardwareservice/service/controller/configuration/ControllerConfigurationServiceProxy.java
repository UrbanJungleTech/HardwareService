package urbanjungletech.hardwareservice.service.controller.configuration;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.HardwareController;

import java.util.Map;
import java.util.Optional;

@Service
@Primary
public class ControllerConfigurationServiceProxy implements ControllerConfigurationService{

    private Map<String, ControllerConfigurationServiceImplementation> controllerConfigurationServiceImplementations;

    public ControllerConfigurationServiceProxy(@Qualifier("HardwareControllerConfigurationServices") Map<String, ControllerConfigurationServiceImplementation> controllerConfigurationServiceImplementations) {
        this.controllerConfigurationServiceImplementations = controllerConfigurationServiceImplementations;
    }

    @Override
    public void configureController(HardwareController hardwareController) {
        String type = hardwareController.getType();
        ControllerConfigurationServiceImplementation controllerConfigurationServiceImplementation = controllerConfigurationServiceImplementations.get(type);
        Optional.ofNullable(controllerConfigurationServiceImplementation).ifPresent(configurationService -> {
            configurationService.configureController(hardwareController);
        });
    }
}
