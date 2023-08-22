package urbanjungletech.hardwareservice.converter.implementation;

import urbanjungletech.hardwareservice.model.ScheduledHardware;
import urbanjungletech.hardwareservice.model.Timer;
import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.converter.ScheduledHardwareJobConverter;
import urbanjungletech.hardwareservice.entity.ScheduledHardwareEntity;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.ONOFF;
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
        result.setLevel(timer.getOnLevel());
        result.setOnoff(onoff);
        result.setTimerId(timer.getId());
        return result;
    }

    @Override
    public ScheduledHardware toScheduledHardware(ScheduledHardwareEntity scheduledHardwareEntity){
        ScheduledHardware result = new ScheduledHardware();
        result.setCronString(scheduledHardwareEntity.getCronString());
        result.setTimerId(scheduledHardwareEntity.getTimerEntity().getId());
        result.setHardwareId(scheduledHardwareEntity.getTimerEntity().getHardware().getId());
        result.setOnoff(scheduledHardwareEntity.getOnoff());
        result.setLevel(scheduledHardwareEntity.getLevel());
        result.setId(scheduledHardwareEntity.getId());
        return result;
    }

    @Override
    public void fillEntity(ScheduledHardware scheduledHardware, ScheduledHardwareEntity scheduledHardwareEntity) {
        scheduledHardwareEntity.setCronString(scheduledHardware.getCronString());
        scheduledHardwareEntity.setOnoff(scheduledHardware.getOnoff());
        scheduledHardwareEntity.setLevel(scheduledHardware.getLevel());
    }
}
