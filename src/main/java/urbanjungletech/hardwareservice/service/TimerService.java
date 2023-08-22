package urbanjungletech.hardwareservice.service;

import urbanjungletech.hardwareservice.model.Timer;

import java.util.List;

public interface TimerService {
    Timer getTimer(long id);
    List<Timer> getTimers();
}
