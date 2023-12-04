package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather.client.implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather.config.WeatherApiProperties;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather.client.WeatherClient;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather.model.WeatherResponse;

@Service
public class WeatherClientImpl implements WeatherClient {
    private final WebClient webClient;
    private final WeatherApiProperties weatherApiProperties;

    public WeatherClientImpl(WebClient webClient,
                             WeatherApiProperties weatherApiProperties) {
        this.webClient = webClient;
        this.weatherApiProperties = weatherApiProperties;
    }


    @Override
    public double readData(String weatherSensorType, String longitude, String latitude, String apiKey, String baseUrl) {
        String location = latitude + "," + longitude;
        UriComponents uri =UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam(weatherApiProperties.getApiKeyParamName(), apiKey)
                .queryParam(weatherApiProperties.getTargetFieldParamName(), weatherSensorType)
                .queryParam(weatherApiProperties.getLocationParamName(), location)
                .build();
        WeatherResponse response = webClient.get().uri(uri.toUriString())
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .block();
        return response.getData().getTimelines().get(0).getIntervals().get(0).getValues().get(weatherSensorType);
    }
}
