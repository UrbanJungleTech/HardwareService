package urbanjungletech.hardwareservice.converter.implementation;

import urbanjungletech.hardwareservice.converter.ScheduledSensorReadingConverter;
import urbanjungletech.hardwareservice.converter.SensorReadingAlertConverter;
import urbanjungletech.hardwareservice.entity.ScheduledSensorReadingEntity;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduledSensorReadingConverterImpl implements ScheduledSensorReadingConverter {
    private SensorReadingAlertConverter sensorReadingAlertConverter;

    public ScheduledSensorReadingConverterImpl(SensorReadingAlertConverter sensorReadingAlertConverter){
        this.sensorReadingAlertConverter = sensorReadingAlertConverter;
    }

    @Override
    public ScheduledSensorReading toModel(ScheduledSensorReadingEntity scheduledSensorReadingEntity) {
        ScheduledSensorReading result = new ScheduledSensorReading();
        result.setCronString(scheduledSensorReadingEntity.getCronString());
        result.setSensorId(scheduledSensorReadingEntity.getSensor().getId());
        result.setId(scheduledSensorReadingEntity.getId());
        List<SensorReadingAlert> sensorReadingAlerts = scheduledSensorReadingEntity.getSensorReadingAlerts().stream().map((sensorReadingAlertEntity) -> {
            return this.sensorReadingAlertConverter.toModel(sensorReadingAlertEntity);
        }).collect(Collectors.toList());
        result.setSensorReadingAlerts(sensorReadingAlerts);
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
