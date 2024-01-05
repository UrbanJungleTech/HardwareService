package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "weather")
public class WeatherApiProperties {
    private String baseUrl;
    private String apiKeyParamName;
    private String targetFieldParamName;
    private String locationParamName;


    public String getApiKeyParamName() {
        return apiKeyParamName;
    }

    public void setApiKeyParamName(String apiKeyParamName) {
        this.apiKeyParamName = apiKeyParamName;
    }

    public String getTargetFieldParamName() {
        return targetFieldParamName;
    }

    public void setTargetFieldParamName(String targetFieldParamName) {
        this.targetFieldParamName = targetFieldParamName;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getLocationParamName() {
        return locationParamName;
    }

    public void setLocationParamName(String locationParamName) {
        this.locationParamName = locationParamName;
    }
}
