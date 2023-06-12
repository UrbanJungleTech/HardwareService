package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.client.model.HardwareState;
import frentz.daniel.hardwareservice.client.model.ONOFF;
import frentz.daniel.hardwareservice.client.model.ScheduledHardware;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduledHardwareJobConverterImpl implements ScheduledHardwareJobConverter{

    private final HardwareStateConverter hardwareStateConverter;

    public ScheduledHardwareJobConverterImpl(HardwareStateConverter hardwareStateConverter){
        this.hardwareStateConverter = hardwareStateConverter;
    }

    @Override
    public ScheduledHardware toModel(ScheduledHardwareEntity scheduledHardwareEntity) {
        ScheduledHardware result = new ScheduledHardware();

//        result.setPort(scheduledHardwareEntity.getPort());
//        result.setCronString(scheduledHardwareEntity.getCronString());
//        result.setHardwareState(this.hardwareStateConverter.toModel(scheduledHardwareEntity.getHardwareState()));
//        result.setId(scheduledHardwareEntity.getId());
//        result.setHardwareControllerSerialNumber(scheduledHardwareEntity.getHardwareControllerSerialNumber());
//        result.setHardwareId(scheduledHardwareEntity.getHardwareId());
        return result;
    }

    @Override
    public List<ScheduledHardware> toModels(List<ScheduledHardwareEntity> scheduledHardwareJobEntities) {
        return scheduledHardwareJobEntities.stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public void fillEntity(ScheduledHardwareEntity scheduledHardwareEntity, ScheduledHardware scheduledHardware) {
        scheduledHardwareEntity.setCronString(scheduledHardware.getCronString());
        HardwareState onState = new HardwareState();
        onState.setState(ONOFF.ON);
    }
}
