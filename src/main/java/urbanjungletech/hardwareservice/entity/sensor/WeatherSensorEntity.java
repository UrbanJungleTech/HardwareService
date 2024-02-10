package urbanjungletech.hardwareservice.entity.sensor;

import jakarta.persistence.Entity;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.weather.model.WeatherSensorTypes;

@Entity
public class WeatherSensorEntity extends SensorEntity{
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
