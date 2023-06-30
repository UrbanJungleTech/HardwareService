package frentz.daniel.hardwareservice.converter.implementation;

import frentz.daniel.hardwareservice.model.ScheduledHardware;
import frentz.daniel.hardwareservice.model.Timer;
import frentz.daniel.hardwareservice.converter.HardwareStateConverter;
import frentz.daniel.hardwareservice.converter.ScheduledHardwareJobConverter;
import frentz.daniel.hardwareservice.entity.ScheduledHardwareEntity;
import frentz.daniel.hardwareservice.model.HardwareState;
import frentz.daniel.hardwareservice.model.ONOFF;
import org.springframework.stereotype.Service;

@Service
public class ScheduledHardwareJobConverterImpl implements ScheduledHardwareJobConverter {

    private final HardwareStateConverter hardwareStateConverter;

    public ScheduledHardwareJobConverterImpl(HardwareStateConverter hardwareStateConverter){
        this.hardwareStateConverter = hardwareStateConverter;
    }

    @Override
    public ScheduledHardware toScheduledHardware(Timer timer, ONOFF onoff) {
        ScheduledHardware result = new ScheduledHardware();
        result.setCronString(onoff == ONOFF.ON ? timer.getOnCronString() : timer.getOffCronString());
        HardwareState hardwareState = new HardwareState();
        hardwareState.setState(onoff);
        hardwareState.setLevel(timer.getOnLevel());
        result.setHardwareState(hardwareState);
        result.setTimerId(timer.getId());
        return result;
    }

    @Override
    public ScheduledHardware toScheduledHardware(ScheduledHardwareEntity scheduledHardwareEntity){
        ScheduledHardware result = new ScheduledHardware();
        result.setCronString(scheduledHardwareEntity.getCronString());
        result.setTimerId(scheduledHardwareEntity.getTimerEntity().getId());
        result.setHardwareId(scheduledHardwareEntity.getTimerEntity().getHardware().getId());
        result.setHardwareState(this.hardwareStateConverter.toModel(scheduledHardwareEntity.getHardwareState()));
        result.setId(scheduledHardwareEntity.getId());
        return result;
    }

    @Override
    public void fillEntity(ScheduledHardware scheduledHardware, ScheduledHardwareEntity scheduledHardwareEntity) {
        scheduledHardwareEntity.setCronString(scheduledHardware.getCronString());
        this.hardwareStateConverter.fillEntity(scheduledHardwareEntity.getHardwareState(), scheduledHardware.getHardwareState());
    }
}
