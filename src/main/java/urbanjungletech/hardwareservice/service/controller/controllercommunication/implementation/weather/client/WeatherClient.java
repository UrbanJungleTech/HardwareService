package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather.client;


public interface WeatherClient {
    double readData(String weatherSensorType, String longitude, String latitude, String apiKey, String baseUrl);
}
