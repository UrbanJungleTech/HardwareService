package urbanjungletech.hardwareservice.converter.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.ScheduledSensorReadingConverter;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SensorReadingRouterConverter;
import urbanjungletech.hardwareservice.entity.ScheduledSensorReadingEntity;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduledSensorReadingConverterImpl implements ScheduledSensorReadingConverter {
    private final SensorReadingRouterConverter sensorReadingRouterConverter;

    public ScheduledSensorReadingConverterImpl(SensorReadingRouterConverter sensorReadingRouterConverter) {
        this.sensorReadingRouterConverter = sensorReadingRouterConverter;
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
        return readings.stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public void fillEntity(ScheduledSensorReadingEntity scheduledSensorReadingEntity, ScheduledSensorReading scheduledSensorReading) {
        scheduledSensorReadingEntity.setCronString(scheduledSensorReading.getCronString());
    }
}
