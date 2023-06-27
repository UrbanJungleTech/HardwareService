package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.entity.TimerEntity;
import frentz.daniel.hardwareservice.model.Timer;

import java.util.List;

public interface TimerConverter {
    Timer toModel(TimerEntity timerEntity);
    List<Timer> toModels(List<TimerEntity> timerEntities);
    void fillEntity(TimerEntity timerEntity, Timer timer);
}
