package urbanjungletech.hardwareservice.service.controller.configuration.implementation;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.exception.MqttCredentialsServiceNotFoundException;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.hardwarecontroller.MqttHardwareController;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;

import java.util.Map;
import java.util.Optional;

@Service
public class MqttCredentialsConfigurationServiceImpl implements MqttCredentialsConfigurationService{
    private final Map<Class <? extends Credentials>, SpecificMqttCredentialsConfigurationService> credentialsConfigurationServices;
    private final HardwareControllerQueryService hardwareControllerQueryService;
    public MqttCredentialsConfigurationServiceImpl(Map<Class<? extends Credentials>, SpecificMqttCredentialsConfigurationService> credentialsConfigurationServices,
                                                   HardwareControllerQueryService hardwareControllerQueryService) {
        this.credentialsConfigurationServices = credentialsConfigurationServices;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
    }

    @Override
    public void configureCredentials(Long hardwareControllerId, MqttConnectOptions options) {
        MqttHardwareController hardwareController = (MqttHardwareController) this.hardwareControllerQueryService.getHardwareController(hardwareControllerId);
        Optional.ofNullable(hardwareController.getHardwareMqttClient().getCredentials()).ifPresent(credentials -> {
            SpecificMqttCredentialsConfigurationService specificMqttCredentialsConfigurationService =
                    Optional.ofNullable(this.credentialsConfigurationServices.get(credentials.getClass())).orElseThrow(() -> {
                        return new MqttCredentialsServiceNotFoundException(hardwareController);
                    });
            specificMqttCredentialsConfigurationService.configureCredentials(hardwareController, options);
            });
    }
}
