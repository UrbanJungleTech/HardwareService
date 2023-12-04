package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather.model;

import java.util.List;

public class WeatherData {
    public List<WeatherTimeline> getTimelines() {
        return timelines;
    }

    public void setTimelines(List<WeatherTimeline> timelines) {
        this.timelines = timelines;
    }

    List<WeatherTimeline> timelines;
}
