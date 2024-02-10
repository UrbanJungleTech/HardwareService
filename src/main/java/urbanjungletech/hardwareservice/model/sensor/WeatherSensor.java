package urbanjungletech.hardwareservice.model.sensor;

import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather.model.WeatherSensorTypes;

public class WeatherSensor extends Sensor{
    private WeatherSensorTypes sensorType;
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

    public WeatherSensorTypes getSensorType() {
        return sensorType;
    }

    public void setSensorType(WeatherSensorTypes sensorType) {
        this.sensorType = sensorType;
    }
}
