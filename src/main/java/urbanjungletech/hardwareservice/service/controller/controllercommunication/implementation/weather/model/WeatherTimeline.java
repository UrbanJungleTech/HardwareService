package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather.model;

import java.util.List;

public class WeatherTimeline {
    private String timestep;
    private String startTime;
    private String endTime;

    public List<WeatherInterval> getIntervals() {
        return intervals;
    }

    public void setIntervals(List<WeatherInterval> intervals) {
        this.intervals = intervals;
    }

    List<WeatherInterval> intervals;

    public String getTimestep() {
        return timestep;
    }

    public void setTimestep(String timestep) {
        this.timestep = timestep;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
