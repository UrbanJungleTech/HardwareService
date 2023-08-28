package urbanjungletech.hardwareservice.converter.implementation;

import urbanjungletech.hardwareservice.converter.ScheduledSensorReadingConverter;
import urbanjungletech.hardwareservice.converter.SensorConverter;
import urbanjungletech.hardwareservice.entity.SensorEntity;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.Sensor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorConverterImpl implements SensorConverter {

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
        result.setMetadata(sensorEntity.getMetadata());
        List<ScheduledSensorReading> scheduledSensorReadings = this.scheduledSensorReadingConverter.toModels(sensorEntity.getScheduledSensorReadings());
        result.setScheduledSensorReadings(scheduledSensorReadings);
        result.setConfiguration(sensorEntity.getConfiguration());
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
        sensorEntity.setMetadata(sensor.getMetadata());
        sensorEntity.setConfiguration(sensor.getConfiguration());
    }
}
