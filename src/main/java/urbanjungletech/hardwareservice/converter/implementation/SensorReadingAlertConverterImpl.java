package urbanjungletech.hardwareservice.converter.implementation;

import urbanjungletech.hardwareservice.action.converter.ActionConverter;
import urbanjungletech.hardwareservice.action.model.Action;
import urbanjungletech.hardwareservice.converter.SensorReadingAlertConverter;
import urbanjungletech.hardwareservice.entity.SensorReadingAlertEntity;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SensorReadingAlertConverterImpl implements SensorReadingAlertConverter {

    private ActionConverter actionConverter;

    public SensorReadingAlertConverterImpl(ActionConverter actionConverter){
        this.actionConverter = actionConverter;
    }
    @Override
    public SensorReadingAlert toModel(SensorReadingAlertEntity sensorReadingAlertEntity) {
        SensorReadingAlert result = new SensorReadingAlert();
        result.setId(sensorReadingAlertEntity.getId());
        result.setAlertType(sensorReadingAlertEntity.getAlertType());
        result.setThreshold(sensorReadingAlertEntity.getThreshold());
        result.setName(sensorReadingAlertEntity.getName());
        result.setScheduledSensorReadingId(sensorReadingAlertEntity.getScheduledSensorReading().getId());
        Optional.ofNullable(sensorReadingAlertEntity.getActions()).ifPresent(actions -> {
            List<Action> actionList = actions.stream()
                    .map(actionConverter::toModel)
                    .collect(Collectors.toList());
            result.setActions(actionList);
        });
        return result;
    }

    @Override
    public void fillEntity(SensorReadingAlertEntity sensorReadingAlertEntity, SensorReadingAlert sensorReadingAlert) {
        sensorReadingAlertEntity.setAlertType(sensorReadingAlert.getAlertType());
        sensorReadingAlertEntity.setThreshold(sensorReadingAlert.getThreshold());
        sensorReadingAlertEntity.setName(sensorReadingAlert.getName());
    }
}
