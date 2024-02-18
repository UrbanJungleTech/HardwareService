package urbanjungletech.hardwareservice.helpers;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.helpers.services.config.WeatherProperties;
import urbanjungletech.hardwareservice.model.connectiondetails.WeatherConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;
import urbanjungletech.hardwareservice.model.hardwarecontroller.WeatherHardwareController;
import urbanjungletech.hardwareservice.model.sensor.WeatherSensor;

@Service
public class WeatherTestService {

    private final WeatherProperties weatherProperties;

    public WeatherTestService(WeatherProperties weatherProperties) {
        this.weatherProperties = weatherProperties;
    }
    public WeatherHardwareController createWeatherHardwareController() {
        WeatherHardwareController hardwareController = new WeatherHardwareController();
        hardwareController.setName("Weather Controller");
        TokenCredentials tokenCredentials = new TokenCredentials();
        tokenCredentials.setTokenValue(weatherProperties.getApikey());
        WeatherConnectionDetails connectionDetails = new WeatherConnectionDetails();
        connectionDetails.setUrl(weatherProperties.getUrl());
        TokenCredentials credentials = new TokenCredentials();
        credentials.setTokenValue(weatherProperties.getApikey());
        connectionDetails.setCredentials(credentials);
        hardwareController.setConnectionDetails(connectionDetails);
        return hardwareController;
    }

    public WeatherSensor createWeatherSensor() {
        WeatherSensor temperatureSensor = new WeatherSensor();
        temperatureSensor.setName("Temperature Sensor");
        temperatureSensor.setPort(null);
        temperatureSensor.setLatitude(43.64493);
        temperatureSensor.setLongitude(-79.39076);
        return temperatureSensor;
    }
}
