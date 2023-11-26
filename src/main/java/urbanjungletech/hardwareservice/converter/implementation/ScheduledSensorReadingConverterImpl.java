package urbanjungletech.hardwareservice.converter.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.ScheduledSensorReadingConverter;
import urbanjungletech.hardwareservice.converter.alert.AlertConverter;
import urbanjungletech.hardwareservice.entity.ScheduledSensorReadingEntity;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduledSensorReadingConverterImpl implements ScheduledSensorReadingConverter {
    private final AlertConverter alertConverter;

    public ScheduledSensorReadingConverterImpl(AlertConverter alertConverter){
        this.alertConverter = alertConverter;
    }

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

    @Override
    public void fillEntity(ScheduledSensorReadingEntity scheduledSensorReadingEntity, ScheduledSensorReading scheduledSensorReading) {
        scheduledSensorReadingEntity.setCronString(scheduledSensorReading.getCronString());
    }
}
