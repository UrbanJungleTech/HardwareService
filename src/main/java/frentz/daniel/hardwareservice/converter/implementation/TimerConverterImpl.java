package frentz.daniel.hardwareservice.converter.implementation;

import frentz.daniel.hardwareservice.converter.TimerConverter;
import frentz.daniel.hardwareservice.entity.TimerEntity;
import frentz.daniel.hardwareservice.client.model.Timer;
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
        result.setOnLevel(timerEntity.getOnCronJob().getHardwareState().getLevel());
        return result;
    }

    @Override
    public List<Timer> toModels(List<TimerEntity> timerEntities){
        return timerEntities.stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public void fillEntity(TimerEntity timerEntity, Timer timer) {

    }
}
