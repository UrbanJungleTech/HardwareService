package frentz.daniel.hardwareservice.converter.implementation;

import frentz.daniel.hardwareservice.model.Timer;
import frentz.daniel.hardwareservice.converter.HardwareStateConverter;
import frentz.daniel.hardwareservice.converter.ScheduledHardwareJobConverter;
import frentz.daniel.hardwareservice.entity.HardwareStateEntity;
import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.model.HardwareState;
import frentz.daniel.hardwareservice.model.ONOFF;
import frentz.daniel.hardwareservice.model.ScheduledHardware;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduledHardwareJobConverterImpl implements ScheduledHardwareJobConverter {

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
    public void fillOnEntity(Timer timer, ScheduledHardwareEntity scheduledHardwareEntity) {
        scheduledHardwareEntity.setCronString(timer.getOnCronString());
        HardwareState hardwareState = new HardwareState();
        hardwareState.setState(ONOFF.ON);
        hardwareState.setLevel(timer.getOnLevel());
        this.hardwareStateConverter.fillEntity(scheduledHardwareEntity.getHardwareState(), hardwareState);
    }

    @Override
    public void fillOffEntity(Timer timer, ScheduledHardwareEntity scheduledHardwareEntity) {
        scheduledHardwareEntity.setCronString(timer.getOffCronString());
        HardwareState hardwareState = new HardwareState();
        hardwareState.setState(ONOFF.OFF);
        this.hardwareStateConverter.fillEntity(scheduledHardwareEntity.getHardwareState(), hardwareState);
    }
}
