package urbanjungletech.hardwareservice.converter.sensor.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.sensor.SpecificSensorConverter;
import urbanjungletech.hardwareservice.entity.sensor.MqttSensorEntity;
import urbanjungletech.hardwareservice.entity.sensor.SensorEntity;
import urbanjungletech.hardwareservice.model.sensor.MqttSensor;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

@Service
public class MqttSensorConverter implements SpecificSensorConverter<MqttSensor, MqttSensorEntity> {

    @Override
    public MqttSensor toModel(MqttSensorEntity sensor) {
        MqttSensor result = new MqttSensor();
        return result;
    }

    @Override
    public MqttSensorEntity createEntity(MqttSensor sensor) {
        MqttSensorEntity result = new MqttSensorEntity();
        return result;
    }

    @Override
    public void fillEntity(MqttSensorEntity sensorEntity, MqttSensor sensor) {

    }
}
