package urbanjungletech.hardwareservice.service.controllercommunication.implementation;

public class WeatherResponse {
    private WeatherResponseCurrent current;

    public WeatherResponseCurrent getCurrent() {
        return current;
    }

    public void setCurrent(WeatherResponseCurrent current) {
        this.current = current;
    }
}
