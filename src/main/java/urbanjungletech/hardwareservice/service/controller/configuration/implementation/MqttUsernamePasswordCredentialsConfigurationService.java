package urbanjungletech.hardwareservice.service.controller.configuration.implementation;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.credentials.UsernamePasswordCredentials;
import urbanjungletech.hardwareservice.model.hardwarecontroller.MqttHardwareController;
import urbanjungletech.hardwareservice.service.credentials.retrieval.CredentialsRetrievalService;

@Service
public class MqttUsernamePasswordCredentialsConfigurationService implements SpecificMqttCredentialsConfigurationService<UsernamePasswordCredentials> {

    private CredentialsRetrievalService credentialsRetrievalService;

    public MqttUsernamePasswordCredentialsConfigurationService(CredentialsRetrievalService credentialsRetrievalService) {
        this.credentialsRetrievalService = credentialsRetrievalService;
    }
    @Override
    public void configureCredentials(MqttHardwareController hardwareController, MqttConnectOptions options) {
        UsernamePasswordCredentials credentials = (UsernamePasswordCredentials)this.credentialsRetrievalService.getCredentials(hardwareController.getHardwareMqttClient().getCredentials());
        options.setUserName(credentials.getUsername());
        options.setPassword(credentials.getPassword().toCharArray());
    }
}
