package frentz.daniel.hardwareservice.dao;

import frentz.daniel.hardwareservice.entity.TimerEntity;
import frentz.daniel.hardwareservice.client.model.Timer;

public interface TimerDAO {
    TimerEntity addTimer(Timer timer);
    TimerEntity getTimer(long timerId);
    TimerEntity updateTimer(Timer timer);
    void delete(long timerId);
}
