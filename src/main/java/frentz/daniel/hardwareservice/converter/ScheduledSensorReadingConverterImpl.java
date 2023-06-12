package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.entity.ScheduledSensorReadingEntity;
import frentz.daniel.hardwareservice.client.model.ScheduledSensorReading;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduledSensorReadingConverterImpl implements ScheduledSensorReadingConverter{
    @Override
    public ScheduledSensorReading toModel(ScheduledSensorReadingEntity scheduledSensorReadingEntity) {
        ScheduledSensorReading result = new ScheduledSensorReading();
        result.setCronString(scheduledSensorReadingEntity.getCronString());
        result.setSensorId(scheduledSensorReadingEntity.getSensor().getId());
        result.setId(scheduledSensorReadingEntity.getId());
        return result;
    }

    @Override
    public List<ScheduledSensorReading> toModels(List<ScheduledSensorReadingEntity> readings) {
        return readings.stream().map((ScheduledSensorReadingEntity reading) -> {
            return this.toModel(reading);
        }).collect(Collectors.toList());
    }
}
