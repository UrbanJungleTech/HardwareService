package urbanjungletech.hardwareservice.converter.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.TimerConverter;
import urbanjungletech.hardwareservice.entity.TimerEntity;
import urbanjungletech.hardwareservice.model.Timer;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimerConverterImpl implements TimerConverter {

    @Override
    public Timer toModel(TimerEntity timerEntity) {
        Timer result = new Timer();
        result.setId(timerEntity.getId());
        result.setHardwareId(timerEntity.getHardware().getId());
        result.setCronString(timerEntity.getCronString());
        result.setSkipNext(timerEntity.isSkipNext());
        result.setLevel(timerEntity.getLevel());
        result.setState(timerEntity.getState());
        return result;
    }

    @Override
    public List<Timer> toModels(List<TimerEntity> timerEntities){
        return timerEntities.stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public void fillEntity(TimerEntity timerEntity, Timer timer) {
        timerEntity.setCronString(timer.getCronString());
        timerEntity.setSkipNext(timer.isSkipNext());
        timerEntity.setLevel(timer.getLevel());
        timerEntity.setState(timer.getState());
    }
}
