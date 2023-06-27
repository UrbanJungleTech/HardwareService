package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.model.Timer;

import java.util.List;

public interface TimerService {
    Timer getTimer(long id);
    List<Timer> getTimers();
}
