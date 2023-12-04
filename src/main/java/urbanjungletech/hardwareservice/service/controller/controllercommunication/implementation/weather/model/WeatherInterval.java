package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather.model;

import java.util.Map;

public class WeatherInterval {
    private String startTime;
    private Map<String, Double> values;



    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Map<String, Double> getValues() {
        return values;
    }

    public void setValues(Map<String, Double> values) {
        this.values = values;
    }
}
