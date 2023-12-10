package urbanjungletech.hardwareservice.converter.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.SensorReadingConverter;
import urbanjungletech.hardwareservice.entity.SensorReadingEntity;
import urbanjungletech.hardwareservice.model.SensorReading;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorReadingConverterImpl implements SensorReadingConverter {
    @Override
    public SensorReading toModel(SensorReadingEntity sensorReadingEntity) {
        SensorReading result = new SensorReading();
        result.setId(sensorReadingEntity.getId());
        result.setReading(sensorReadingEntity.getReading());
        result.setReadingTime(sensorReadingEntity.getReadingTime());
        result.setSensorId(sensorReadingEntity.getSensorId());
        return result;
    }

    @Override
    public List<SensorReading> toModels(List<SensorReadingEntity> sensorReadingEntities) {
        return sensorReadingEntities.stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public void fillEntity(SensorReadingEntity sensorReadingEntity, SensorReading sensorReading) {
        sensorReadingEntity.setReading(sensorReading.getReading());
        sensorReadingEntity.setReadingTime(sensorReading.getReadingTime());
        sensorReadingEntity.setSensorId(sensorReading.getSensorId());
    }
}
