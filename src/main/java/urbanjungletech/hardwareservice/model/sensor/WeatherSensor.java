package urbanjungletech.hardwareservice.model.sensor;

import urbanjungletech.hardwareservice.model.validation.annotation.WeatherSensorTypeValidation;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather.model.WeatherSensorTypes;

@WeatherSensorTypeValidation
public class WeatherSensor extends Sensor{
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
