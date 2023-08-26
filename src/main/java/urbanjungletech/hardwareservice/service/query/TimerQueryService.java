package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.Timer;

import java.util.List;

public interface TimerQueryService {
    Timer getTimer(long id);
    List<Timer> getTimers();
}
