package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.ControllerCommunicationServiceImplementation;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.HardwareControllerCommunicationService;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather.client.WeatherClient;
import urbanjungletech.hardwareservice.service.credentials.retrieval.CredentialsRetrievalService;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;

@Service
@HardwareControllerCommunicationService(type="weather")
public class WeatherCommunicationService extends ControllerCommunicationServiceImplementation {
    private WeatherClient weatherClient;
    private HardwareControllerQueryService hardwareControllerQueryService;
    private CredentialsRetrievalService credentialsRetrievalService;

    public WeatherCommunicationService(WeatherClient weatherClient,
                                       HardwareControllerQueryService hardwareControllerQueryService,
                                       CredentialsRetrievalService credentialsRetrievalService) {
        this.weatherClient = weatherClient;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
        this.credentialsRetrievalService = credentialsRetrievalService;
    }

    @Override
    public double getSensorReading(Sensor sensor) {
        HardwareController hardwareController = this.hardwareControllerQueryService.getHardwareController(sensor.getHardwareControllerId());
        TokenCredentials credentials = (TokenCredentials) hardwareController.getCredentials();
        credentials = (TokenCredentials) this.credentialsRetrievalService.getCredentials(credentials);
        String weatherSensorType = sensor.getConfiguration().get("sensorType");
        String longitude= sensor.getConfiguration().get("longitude");
        String latitude = sensor.getConfiguration().get("latitude");
        String apiKey = credentials.getTokenValue();
        String baseUrl = credentials.getUrl();
        return weatherClient.readData(weatherSensorType, longitude, latitude, apiKey, baseUrl);
    }

}
