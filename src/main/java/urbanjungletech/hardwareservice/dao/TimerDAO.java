package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.TimerEntity;
import urbanjungletech.hardwareservice.model.Timer;

import java.util.List;

public interface TimerDAO {
    TimerEntity addTimer(Timer timer);
    TimerEntity getTimer(long timerId);
    TimerEntity updateTimer(Timer timer);
    void delete(long timerId);
    List<TimerEntity> getTimers();
}
