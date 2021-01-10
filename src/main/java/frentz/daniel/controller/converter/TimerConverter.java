package frentz.daniel.controller.converter;

import frentz.daniel.controller.entity.TimerEntity;
import frentz.daniel.controllerclient.model.Timer;

public interface TimerConverter {
    Timer toModel(TimerEntity timerEntity);
}
