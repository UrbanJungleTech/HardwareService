package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.entity.SensorEntity;
import frentz.daniel.hardwareservice.client.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.client.model.Sensor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorConverterImpl implements SensorConverter{

    private final ScheduledSensorReadingConverter scheduledSensorReadingConverter;

    public SensorConverterImpl(ScheduledSensorReadingConverter scheduledSensorReadingConverter){
        this.scheduledSensorReadingConverter = scheduledSensorReadingConverter;
    }

    @Override
    public Sensor toModel(SensorEntity sensorEntity) {
        Sensor result = new Sensor();
        result.setId(sensorEntity.getId());
        result.setName(sensorEntity.getName());
        result.setPort(sensorEntity.getPort());
        result.setSensorType(sensorEntity.getSensorType());
        result.setHardwareControllerId(sensorEntity.getHardwareController().getId());
        List<ScheduledSensorReading> scheduledSensorReadings = this.scheduledSensorReadingConverter.toModels(sensorEntity.getScheduledSensorReadings());
        result.setScheduledSensorReadings(scheduledSensorReadings);
        return result;
    }

    @Override
    public List<Sensor> toModels(List<SensorEntity> sensorEntities) {
        if(sensorEntities != null) {
            return sensorEntities.stream().map((this::toModel)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void fillEntity(SensorEntity sensorEntity, Sensor sensor) {
        sensorEntity.setSensorType(sensor.getSensorType());
        sensorEntity.setPort(sensor.getPort());
        sensorEntity.setName(sensor.getName());
    }
}
