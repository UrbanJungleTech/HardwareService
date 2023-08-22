package urbanjungletech.hardwareservice.converter.implementation;

import urbanjungletech.hardwareservice.converter.TimerConverter;
import urbanjungletech.hardwareservice.entity.TimerEntity;
import urbanjungletech.hardwareservice.model.ONOFF;
import urbanjungletech.hardwareservice.model.ScheduledHardware;
import urbanjungletech.hardwareservice.model.Timer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimerConverterImpl implements TimerConverter {

    private final ScheduledHardwareJobConverterImpl scheduledHardwareJobConverter;

    public TimerConverterImpl(ScheduledHardwareJobConverterImpl scheduledHardwareJobConverter){
        this.scheduledHardwareJobConverter = scheduledHardwareJobConverter;
    }

    @Override
    public Timer toModel(TimerEntity timerEntity) {
        Timer result = new Timer();
        result.setId(timerEntity.getId());
        result.setHardwareId(timerEntity.getHardware().getId());
        result.setOnCronString(timerEntity.getOnCronJob().getCronString());
        result.setOffCronString(timerEntity.getOffCronJob().getCronString());
        result.setOnLevel(timerEntity.getOnCronJob().getLevel());
        return result;
    }

    @Override
    public List<Timer> toModels(List<TimerEntity> timerEntities){
        return timerEntities.stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public void fillEntity(TimerEntity timerEntity, Timer timer) {
        ScheduledHardware onScheduledHardware = this.scheduledHardwareJobConverter.toScheduledHardware(timer, ONOFF.ON);
        this.scheduledHardwareJobConverter.fillEntity(onScheduledHardware, timerEntity.getOnCronJob());
        ScheduledHardware offScheduledHardware = this.scheduledHardwareJobConverter.toScheduledHardware(timer, ONOFF.OFF);
        this.scheduledHardwareJobConverter.fillEntity(offScheduledHardware, timerEntity.getOffCronJob());
    }
}
