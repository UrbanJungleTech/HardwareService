package frentz.daniel.hardwareservice.converter.implementation;

import frentz.daniel.hardwareservice.converter.SensorReadingConverter;
import frentz.daniel.hardwareservice.entity.SensorReadingEntity;
import frentz.daniel.hardwareservice.model.SensorReading;
import org.springframework.stereotype.Service;

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
    }
}
