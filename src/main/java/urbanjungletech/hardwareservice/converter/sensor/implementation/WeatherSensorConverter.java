package urbanjungletech.hardwareservice.converter.sensor.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.sensor.SpecificSensorConverter;
import urbanjungletech.hardwareservice.entity.sensor.WeatherSensorEntity;
import urbanjungletech.hardwareservice.model.sensor.WeatherSensor;

@Service
public class WeatherSensorConverter implements SpecificSensorConverter<WeatherSensor, WeatherSensorEntity> {
    @Override
    public WeatherSensor toModel(WeatherSensorEntity sensor) {
        WeatherSensor result = new WeatherSensor();
        result.setLatitude(sensor.getLatitude());
        result.setLongitude(sensor.getLongitude());
        result.setSensorType(sensor.getSensorType());
        return result;
    }

    @Override
    public WeatherSensorEntity createEntity(WeatherSensor sensor) {
        WeatherSensorEntity result = new WeatherSensorEntity();
        result.setLatitude(sensor.getLatitude());
        result.setLongitude(sensor.getLongitude());
        result.setSensorType(sensor.getSensorType());
        return result;
    }

    @Override
    public void fillEntity(WeatherSensorEntity sensorEntity, WeatherSensor sensor) {
        sensorEntity.setLatitude(sensor.getLatitude());
        sensorEntity.setLongitude(sensor.getLongitude());
        sensorEntity.setSensorType(sensor.getSensorType());
    }
}
