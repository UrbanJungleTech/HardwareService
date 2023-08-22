package urbanjungletech.hardwareservice.converter;

import urbanjungletech.hardwareservice.entity.TimerEntity;
import urbanjungletech.hardwareservice.model.Timer;

import java.util.List;

public interface TimerConverter {
    Timer toModel(TimerEntity timerEntity);
    List<Timer> toModels(List<TimerEntity> timerEntities);
    void fillEntity(TimerEntity timerEntity, Timer timer);
}
