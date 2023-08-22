package urbanjungletech.hardwareservice.config;

import java.util.Map;

public class ControllerClientConfiguration {
    private Map<String, MqttClientConfiguration> mqtt;
    private Map<String, OpenWeatherClientConfiguration> openWeather;

    public Map<String, MqttClientConfiguration> getMqtt() {
        return mqtt;
    }

    public void setMqtt(Map<String, MqttClientConfiguration> mqtt) {
        this.mqtt = mqtt;
    }

    public Map<String, OpenWeatherClientConfiguration> getOpenWeather() {
        return openWeather;
    }

    public void setOpenWeather(Map<String, OpenWeatherClientConfiguration> openWeather) {
        this.openWeather = openWeather;
    }
}
