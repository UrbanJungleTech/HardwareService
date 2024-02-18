package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.hardwarecontroller.WeatherHardwareController;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.model.sensor.WeatherSensor;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.SpecificControllerCommunicationService;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather.client.WeatherClient;
import urbanjungletech.hardwareservice.service.credentials.retrieval.CredentialsRetrievalService;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;

@Service
public class WeatherCommunicationServiceSpecific implements SpecificControllerCommunicationService<WeatherHardwareController> {
    private final WeatherClient weatherClient;
    private final HardwareControllerQueryService hardwareControllerQueryService;
    private final CredentialsRetrievalService credentialsRetrievalService;

    public WeatherCommunicationServiceSpecific(WeatherClient weatherClient,
                                               HardwareControllerQueryService hardwareControllerQueryService,
                                               CredentialsRetrievalService credentialsRetrievalService) {
        this.weatherClient = weatherClient;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
        this.credentialsRetrievalService = credentialsRetrievalService;
    }

    @Override
    public void sendStateToController(Hardware hardware) {

    }

    @Override
    public void sendInitialState(long hardwareControllerId) {

    }

    @Override
    public double getSensorReading(Sensor sensor) {
        WeatherSensor weatherSensor = (WeatherSensor) sensor;
        WeatherHardwareController hardwareController = (WeatherHardwareController) this.hardwareControllerQueryService.getHardwareController(sensor.getHardwareControllerId());
        Credentials credentials = hardwareController.getConnectionDetails().getCredentials();
        TokenCredentials tokenCredentials = (TokenCredentials)this.credentialsRetrievalService.getCredentials(credentials);
        String weatherSensorType = weatherSensor.getPort().toLowerCase();
        String longitude= String.valueOf(weatherSensor.getLongitude());
        String latitude = String.valueOf(weatherSensor.getLatitude());
        String apiKey = tokenCredentials.getTokenValue();
        String baseUrl = hardwareController.getConnectionDetails().getUrl();
        return weatherClient.readData(weatherSensorType, longitude, latitude, apiKey, baseUrl);
    }

    @Override
    public double getAverageSensorReading(String[] sensorPorts) {
        return 0;
    }

    @Override
    public void registerHardware(Hardware hardware) {

    }

    @Override
    public void registerSensor(Sensor sensor) {

    }

    @Override
    public void deregisterHardware(Hardware hardware) {

    }

    @Override
    public void deregisterSensor(Sensor sensor) {

    }

}
